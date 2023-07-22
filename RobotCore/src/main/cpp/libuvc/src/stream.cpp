/*********************************************************************
* Software License Agreement (BSD License)
*
*  Copyright (C) 2010-2012 Ken Tossell
*  All rights reserved.
*
*  Redistribution and use in source and binary forms, with or without
*  modification, are permitted provided that the following conditions
*  are met:
*
*   * Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*   * Redistributions in binary form must reproduce the above
*     copyright notice, this list of conditions and the following
*     disclaimer in the documentation and/or other materials provided
*     with the distribution.
*   * Neither the name of the author nor other contributors may be
*     used to endorse or promote products derived from this software
*     without specific prior written permission.
*
*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
*  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
*  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
*  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
*  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
*  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
*  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
*  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
*  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
*  LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
*  ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
*  POSSIBILITY OF SUCH DAMAGE.
*********************************************************************/
/**
 * @defgroup streaming Streaming control functions
 * @brief Tools for creating, managing and consuming video streams
 */

#include "libuvc.h"
#include "libuvc/libuvc_internal.h"
#include "errno.h"
#include "JniEnv.h"

#ifdef _MSC_VER
    #define DELTA_EPOCH_IN_MICROSECS  116444736000000000Ui64
    // gettimeofday - get time of day for Windows;
    // A gettimeofday implementation for Microsoft Windows;
    // Public domain code, author "ponnada";
    int gettimeofday(struct timeval *tv, struct timezone *tz)
    {
        FILETIME ft;
        uint64_t tmpres = 0;
        static int tzflag = 0;
        if (NULL != tv)
        {
            GetSystemTimeAsFileTime(&ft);
            tmpres |= ft.dwHighDateTime;
            tmpres <<= 32;
            tmpres |= ft.dwLowDateTime;
            tmpres /= 10;
            tmpres -= DELTA_EPOCH_IN_MICROSECS;
            tv->tv_sec = (TIMEVAL_TV_SEC_T)(tmpres / 1000000U);
            tv->tv_usec = (TIMEVAL_TV_NSEC_T)(tmpres % 1000000U);
        }
        return 0;
    }
#endif // _MSC_VER

#undef TAG
#define TAG "UvcStream"

uvc_frame_desc_t *uvc_find_frame_desc_stream(uvc_stream_handle *strmh, uint16_t format_id, uint16_t frame_id);
uvc_frame_desc_t *uvc_find_frame_desc(uvc_device_handle_t *devh, uint16_t format_id, uint16_t frame_id);
void uvc_user_callback_main(uvc_stream_handle *arg);
uvc_error_t captureUserFrame(uvc_stream_handle *strmh, uvc_frame **ppFrame);

struct format_table_entry
    {
    enum uvc_frame_format format;
    uint8_t abstract_fmt;
    uint8_t guid[16];
    int children_count;
    enum uvc_frame_format *children;
    };

struct format_table_entry *_get_format_entry(enum uvc_frame_format format)
    {
#define ABS_FMT(_fmt, _num, ...) \
    case _fmt: { \
    static enum uvc_frame_format _fmt##_children[] = __VA_ARGS__; \
    static struct format_table_entry _fmt##_entry = { \
      _fmt, 0, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, _num, _fmt##_children }; \
    return &_fmt##_entry; }

#define FMT(_fmt, ...) \
    case _fmt: { \
    static struct format_table_entry _fmt##_entry = { \
      _fmt, 0, __VA_ARGS__, 0, NULL }; \
    return &_fmt##_entry; }

    switch (format)
        {
        /* Define new formats here */
        ABS_FMT(UVC_FRAME_FORMAT_ANY, 2, { UVC_FRAME_FORMAT_UNCOMPRESSED, UVC_FRAME_FORMAT_COMPRESSED })
        ABS_FMT(UVC_FRAME_FORMAT_UNCOMPRESSED, 3, { UVC_FRAME_FORMAT_YUY2, UVC_FRAME_FORMAT_UYVY, UVC_FRAME_FORMAT_GRAY8 })
        FMT(UVC_FRAME_FORMAT_YUY2, { 'Y', 'U', 'Y', '2', 0x00, 0x00, 0x10, 0x00, 0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71 })
        FMT(UVC_FRAME_FORMAT_UYVY, { 'U', 'Y', 'V', 'Y', 0x00, 0x00, 0x10, 0x00, 0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71 })
        FMT(UVC_FRAME_FORMAT_GRAY8, { 'Y', '8', '0', '0', 0x00, 0x00, 0x10, 0x00, 0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71 })
        FMT(UVC_FRAME_FORMAT_BY8, { 'B', 'Y', '8', ' ', 0x00, 0x00, 0x10, 0x00, 0x80, 0x00, 0x00, 0xaa, 0x00, 0x38, 0x9b, 0x71 })
        ABS_FMT(UVC_FRAME_FORMAT_COMPRESSED, 1, { UVC_FRAME_FORMAT_MJPEG })
        FMT(UVC_FRAME_FORMAT_MJPEG, { 'M', 'J', 'P', 'G' })
        default:return NULL;
        }

#undef ABS_FMT
#undef FMT
    }

static uint8_t _uvc_frame_format_matches_guid(enum uvc_frame_format fmt, uint8_t guid[16])
    {
    struct format_table_entry *format;
    int child_idx;

    format = _get_format_entry(fmt);
    if (!format)
        return 0;

    if (!format->abstract_fmt && !memcmp(guid, format->guid, 16))
        return 1;

    for (child_idx = 0; child_idx < format->children_count; child_idx++)
        {
        if (_uvc_frame_format_matches_guid(format->children[child_idx], guid))
            return 1;
        }

    return 0;
    }

static enum uvc_frame_format uvc_frame_format_for_guid(uint8_t guid[16])
    {
    for (int fmt = UVC_FRAME_FORMAT_UNKNOWN; fmt < UVC_FRAME_FORMAT_COUNT; ++fmt)
        {
        format_table_entry *pFormatTableEntry = _get_format_entry(uvc_frame_format(fmt));
        if (!pFormatTableEntry || pFormatTableEntry->abstract_fmt)
            continue;
        if (!memcmp(pFormatTableEntry->guid, guid, 16))
            return pFormatTableEntry->format;
        }
    return UVC_FRAME_FORMAT_UNKNOWN;
    }

/** @internal
 * Run a streaming control query
 * @param[in] devh UVC device
 * @param[in,out] ctrl Control block
 * @param[in] probe Whether this is a probe query or a commit query
 * @param[in] req Query type
 */
uvc_error uvc_query_stream_ctrl(uvc_device_handle_t *devh, uvc_stream_ctrl_t *ctrl, uvc_query_stream_ctrl_flavor flavor, enum uvc_req_code req)
    {
    UVC_ENTER();
    uvc_error rc = UVC_SUCCESS;

	const uint16_t cb15 = 48;
    const uint16_t cb11 = 34;
	const uint16_t cbOld = 26;

    uint8_t buf[cb15];
    zero(buf);

    uint16_t cb;
    const uint16_t bcdUVC = devh->info->ctrl_if.bcdUVC;
    if (bcdUVC >= 0x0150)
		cb = cb15;
	else if (bcdUVC >= 0x0110)
        cb = cb11;
    else
        cb = cbOld;

    /* prepare for a SET transfer */
    if (isSet(req))
        {
        SHORT_TO_SW(ctrl->bmHint, buf);
        buf[2] = ctrl->bFormatIndex;
        buf[3] = ctrl->bFrameIndex;
        INT_TO_DW(ctrl->dwFrameInterval, buf + 4);
        SHORT_TO_SW(ctrl->wKeyFrameRate, buf + 8);
        SHORT_TO_SW(ctrl->wPFrameRate, buf + 10);
        SHORT_TO_SW(ctrl->wCompQuality, buf + 12);
        SHORT_TO_SW(ctrl->wCompWindowSize, buf + 14);
        SHORT_TO_SW(ctrl->wDelay, buf + 16);
        INT_TO_DW(ctrl->dwMaxVideoFrameSize, buf + 18);
        INT_TO_DW(ctrl->dwMaxPayloadTransferSize, buf + 22);

        if (cb >= cb11)
            {
            INT_TO_DW (ctrl->dwClockFrequency, buf + 26);
            buf[30] = ctrl->bmFramingInfo;
            buf[31] = ctrl->bPreferredVersion;
            buf[32] = ctrl->bMinVersion;
            buf[33] = ctrl->bMaxVersion;
            if (cb >= cb15)
                {
				buf[34] = ctrl->bUsage;
				buf[35] = ctrl->bBitDepthLuma;
				buf[36] = ctrl->bmSettings;
				buf[37] = ctrl->bMaxNumberOfRefFramesPlus1;
				SHORT_TO_SW(ctrl->bmRateControlModes, buf + 38);
				LONG_TO_QW(ctrl->bmLayoutPerStream, buf + 40);
                }
            }
        }

    /* do the transfer */
    int cbTransferedOrErr = libusb_control_transfer(
            devh->usb_devh,
            (uint8_t)(isSet(req) ? REQ_TYPE_SET : REQ_TYPE_GET),
            req,
            (flavor==FLAVOR_PROBE) ? (UVC_VS_PROBE_CONTROL << 8) : (UVC_VS_COMMIT_CONTROL << 8),
            ctrl->bInterfaceNumber,
            buf,
            cb,
            0);

    if (cbTransferedOrErr <= 0)
        {
        rc = uvc_originate_err(rc==0 ? UVC_ERROR_OTHER : (uvc_error)cbTransferedOrErr);
        UVC_DEBUG("cbTransferedOrErr=%d(%s) rc=%d", cbTransferedOrErr, libusb_error_name(cbTransferedOrErr), rc);
        }
    else
        {
        /* now decode following a GET transfer */
        if (isGet(req))
            {
            ctrl->bmHint = SW_TO_SHORT(buf);
            ctrl->bFormatIndex = buf[2];
            ctrl->bFrameIndex = buf[3];
            ctrl->dwFrameInterval = DW_TO_INT(buf + 4);
            ctrl->wKeyFrameRate = SW_TO_SHORT(buf + 8);
            ctrl->wPFrameRate = SW_TO_SHORT(buf + 10);
            ctrl->wCompQuality = SW_TO_SHORT(buf + 12);
            ctrl->wCompWindowSize = SW_TO_SHORT(buf + 14);
            ctrl->wDelay = SW_TO_SHORT(buf + 16);
            ctrl->dwMaxVideoFrameSize = DW_TO_INT(buf + 18);
            ctrl->dwMaxPayloadTransferSize = DW_TO_INT(buf + 22);

            if (cb >= cb11)
                {
                ctrl->dwClockFrequency = DW_TO_INT (buf + 26);
                ctrl->bmFramingInfo = buf[30];
                ctrl->bPreferredVersion = buf[31];
                ctrl->bMinVersion = buf[32];
                ctrl->bMaxVersion = buf[33];
                if (cb >= cb15)
                    {
                    ctrl->bUsage = buf[34];
                    ctrl->bBitDepthLuma = buf[35];
                    ctrl->bmSettings = buf[36];
                    ctrl->bMaxNumberOfRefFramesPlus1 = buf[37];
                    ctrl->bmRateControlModes = SW_TO_SHORT(buf + 38);
                    ctrl->bmLayoutPerStream = QW_TO_LONG(buf + 40);
                    }
                }
			else
				{
				ctrl->dwClockFrequency = devh->info->ctrl_if.dwClockFrequency;
				}

            /* fix up block for cameras that fail to set dwMax* */
            if (ctrl->dwMaxVideoFrameSize == 0)
                {
                uvc_frame_desc_t *frame = uvc_find_frame_desc(devh, ctrl->bFormatIndex, ctrl->bFrameIndex);

                if (frame)
                    {
                    ctrl->dwMaxVideoFrameSize = frame->dwMaxVideoFrameBufferSize;
                    }
                }
            }
        }

    UVC_EXIT(rc);
    return rc;
    }

/** @brief Reconfigure stream with a new stream format.
 * @ingroup streaming
 *
 * This may be executed whether or not the stream is running.
 *
 * @param[in] strmh Stream handle
 * @param[in] ctrl Control block, processed using {uvc_probe_stream_ctrl} or
 *             {uvc_get_stream_ctrl_format_size}
 */
uvc_error_t uvc_commit_stream_ctrl(uvc_stream_handle *strmh, uvc_stream_ctrl_t *ctrl)
    {
    uvc_error_t ret;

    if (strmh->stream_if->bInterfaceNumber != ctrl->bInterfaceNumber)
        return uvc_originate_err(UVC_ERROR_INVALID_PARAM);

    /* @todo Allow the stream to be modified without restarting the stream */
    if (strmh->isRunning)
        return uvc_originate_err(UVC_ERROR_BUSY);

    ret = uvc_query_stream_ctrl(strmh->devh, ctrl, FLAVOR_COMMIT, UVC_SET_CUR);
    if (ret != UVC_SUCCESS)
        return ret;

    strmh->cur_ctrl = *ctrl;
    return UVC_SUCCESS;
    }

/** @internal
 * @brief Find the descriptor for a specific frame configuration
 * @param stream_if Stream interface
 * @param format_id Index of format class descriptor
 * @param frame_id Index of frame descriptor
 */
static uvc_frame_desc_t *
_uvc_find_frame_desc_stream_if(uvc_streaming_interface_t *stream_if, uint16_t format_id, uint16_t frame_id)
    {
    uvc_format_desc *format = NULL;
    uvc_frame_desc *frame = NULL;

    DL_FOREACH(stream_if->format_descs, format)
        {
        if (format->bFormatIndex == format_id)
            {
            DL_FOREACH(format->frame_descs, frame)
                {
                if (frame->bFrameIndex == frame_id)
                    return frame;
                }
            }
        }

    return NULL;
    }

uvc_frame_desc_t *
uvc_find_frame_desc_stream(uvc_stream_handle *strmh, uint16_t format_id, uint16_t frame_id)
    {
    return _uvc_find_frame_desc_stream_if(strmh->stream_if, format_id, frame_id);
    }

/** @internal
 * @brief Find the descriptor for a specific frame configuration
 * @param devh UVC device
 * @param format_id Index of format class descriptor
 * @param frame_id Index of frame descriptor
 */
uvc_frame_desc_t *
uvc_find_frame_desc(uvc_device_handle_t *devh, uint16_t format_id, uint16_t frame_id)
    {
    uvc_streaming_interface_t *pStreamIntf;
    uvc_frame_desc_t *pFrameDesc;

    DL_FOREACH(devh->info->stream_ifs, pStreamIntf)
        {
        pFrameDesc = _uvc_find_frame_desc_stream_if(pStreamIntf, format_id, frame_id);
        if (pFrameDesc)
            return pFrameDesc;
        }

    return NULL;
    }

uvc_error
uvc_get_stream_ctrl_format_size(uvc_device_handle* devh, /*out*/ uvc_stream_ctrl* pCtrl, enum uvc_frame_format cf, int width, int height, int fps)
    {
    UVC_ENTER();
    uvc_error rc = UVC_SUCCESS;

    zero(pCtrl);
    bool done = false;

    /* find a matching frame descriptor and interval */
    uvc_streaming_interface* pStream;
    DL_FOREACH(devh->info->stream_ifs, pStream)
        {
        if (done) break;
        rc = devh->claimInterface(pStream->bInterfaceNumber); // this and the matching release recommended by saki. indeed seems necessary if we invoke uvc_query_stream_ctrl()
        if (!rc)
            {
            uvc_format_desc_t* pFormat;
            DL_FOREACH(pStream->format_descs, pFormat)
                {
                if (done) break;
                if (!_uvc_frame_format_matches_guid(cf, pFormat->guidFormat))
                    {
                    continue;
                    }

                uvc_frame_desc_t* pFrame;
                DL_FOREACH(pFormat->frame_descs, pFrame)
                    {
                    if (done) break;
                    if (pFrame->wWidth != width || pFrame->wHeight != height)
                        {
                        continue;
                        }

                    if (pFrame->rgIntervals)
                        {
                        for (uint32_t* pInterval = pFrame->rgIntervals; !done && *pInterval; ++pInterval)
                            {
                            // allow a fps rate of zero to mean "accept first rate available"
                            if (TEN_MILLION / *pInterval == (unsigned int) fps || fps == 0)
                                {
                                UVC_DEBUG("%dx%d %dfps", width, height, TEN_MILLION / *pInterval);
                                /* get the max values -- we need the interface number to be able to do this */
                                pCtrl->bInterfaceNumber = pStream->bInterfaceNumber;
                                uvc_query_stream_ctrl(devh, pCtrl, FLAVOR_PROBE, UVC_GET_MAX);

                                pCtrl->bmHint = (1 << 0); /* don't negotiate frame interval */
                                pCtrl->bFormatIndex = pFormat->bFormatIndex;
                                pCtrl->bFrameIndex = pFrame->bFrameIndex;
                                pCtrl->dwFrameInterval = *pInterval;

                                done = true;
                                }
                            }

                        // If after going though all the available frame rates, we find none that match,
                        // we choose the closest supported frame rate instead of failing hard. This has
                        // proven to be necessary due to some cameras reporting, for instance, an interval
                        // for 15FPS of 666667*100ns, but after rounding to 15FPS and conversion back to
                        // nanoseconds and a cast to integer, ends up as 666666*100ns, which would cause
                        // a fault, even though it's only 100ns different.
                        if (!done)
                            {
                            int32_t fpsConvertedTo100NsInterval = (1.0f/fps)*1e7;
                            int32_t distance = 0;
                            uint32_t chosen = UINT32_MAX;

                            for (int i = 0; pFrame->rgIntervals[i]; i++)
                                {
                                int32_t nextDistance = abs((int32_t)pFrame->rgIntervals[i] - fpsConvertedTo100NsInterval);
                                if (chosen == UINT32_MAX || nextDistance < distance)
                                    {
                                    chosen = pFrame->rgIntervals[i];
                                    distance = nextDistance;
                                    }
                                }

                            // Paranoia
                            if (chosen != UINT32_MAX)
                                {
                                LOGE("Camera does not support requested frame rate of %dFPS (interval %d*100ns) at resolution [%dx%d] format %d; choosing closest supported interval (%d*100ns)",
                                        fps, fpsConvertedTo100NsInterval, width, height, (int)cf, chosen);

                                /* get the max values -- we need the interface number to be able to do this */
                                pCtrl->bInterfaceNumber = pStream->bInterfaceNumber;
                                uvc_query_stream_ctrl(devh, pCtrl, FLAVOR_PROBE, UVC_GET_MAX);

                                pCtrl->bmHint = (1 << 0); /* don't negotiate frame interval */
                                pCtrl->bFormatIndex = pFormat->bFormatIndex;
                                pCtrl->bFrameIndex = pFrame->bFrameIndex;
                                pCtrl->dwFrameInterval = chosen;

                                done = true;
                                }
                            }
                        }
                    else
                        {
                        uint32_t interval_100ns = fps==0 ? 0 : TEN_MILLION / fps;
                        uint32_t interval_offset = interval_100ns - pFrame->dwMinFrameInterval;

                        bool intervalOK = interval_100ns >= pFrame->dwMinFrameInterval &&
                            interval_100ns <= pFrame->dwMaxFrameInterval &&
                            !(interval_offset && (interval_offset % pFrame->dwFrameIntervalStep));

                        if (fps==0 || intervalOK)
                            {
                            UVC_DEBUG("%dx%d %dfps", width, height, fps);
                            /* get the max values -- we need the interface number to be able to do this */
                            pCtrl->bInterfaceNumber = pStream->bInterfaceNumber;
                            uvc_query_stream_ctrl(devh, pCtrl, FLAVOR_PROBE, UVC_GET_MAX);

                            pCtrl->bmHint = (1 << 0); /* don't negotiate frame interval */
                            pCtrl->bFormatIndex = pFormat->bFormatIndex;
                            pCtrl->bFrameIndex = pFrame->bFrameIndex;
                            pCtrl->dwFrameInterval = interval_100ns;

                            done = true;
                            }
                        }
                    }
                }

            devh->releaseInterface(pStream->bInterfaceNumber);
            }
        else
            {
            LOGE("uvc_claim_if() failed: rc=%d; ignoring streaming interface interface", rc);
            rc = UVC_SUCCESS;
            }
        }

    if (done)
        {
        rc = uvc_probe_stream_ctrl(devh, pCtrl);
        UVC_RETURN(rc);
        }
    else
        {
        UVC_EXIT(UVC_ERROR_INVALID_MODE);
        return uvc_originate_err(UVC_ERROR_INVALID_MODE);
        }
    }

/** @internal
 * Negotiate streaming parameters with the device
 *
 * @param[in] devh UVC device
 * @param[in,out] ctrl Control block
 */
uvc_error uvc_probe_stream_ctrl(uvc_device_handle *devh, /*in,out*/uvc_stream_ctrl_t *ctrl)
    {
    UVC_ENTER();
    uvc_error rc = UVC_SUCCESS;
    //
    rc = devh->claimInterface(ctrl->bInterfaceNumber); // idempotent
    if (!rc)
        {
        rc = uvc_query_stream_ctrl(devh, ctrl, FLAVOR_PROBE, UVC_SET_CUR);
        if (!rc)
            {
            rc = uvc_query_stream_ctrl(devh, ctrl, FLAVOR_PROBE, UVC_GET_CUR);
            }
        }
    //
    UVC_RETURN(rc);
    }

/** @internal
 * @brief Swap the working buffer with the presented buffer and notify consumers
 * Called with the streaming lock held.
 */
void _uvc_swap_buffers(uvc_stream_handle *strmh)
    {
    LOCK_SCOPE
        {
        ScopedLock scopedLock(strmh->userCallbackLock);

        // Hang on to this for a moment
        uvc_frame* pFrameOld = strmh->pFrame;

        // Find us a new frame
        if (strmh->pFrameUserReturn)
            {
            strmh->pFrame = strmh->pFrameUserReturn;
            strmh->pFrameUserReturn = NULL;
            }
        else if (strmh->pFrameUser)
            {
            // They didn't consume what we gave them before. Take it back: we're
            // going to give them a new one, so why not?
            strmh->pFrame = strmh->pFrameUser;
            strmh->pFrameUser = NULL;
            }
        else
            {
            strmh->pFrame = strmh->allocateFrame();
            if (NULL == strmh->pFrame)
                {
                outOfMemory();
                }
            }

        // Give them the old one
        delete strmh->pFrameUser;   // free any that *might* be there
        strmh->pFrameUser = pFrameOld;

        // Carry over data / initialize new data
        strmh->pFrame->frameNumber          = pFrameOld->frameNumber + 1;
        strmh->pFrame->sourceClockReference = 0;
        strmh->pFrame->pts                  = 0;
        strmh->pFrame->resetAppend();

        scopedLock.broadcast(strmh->userCallbackFrameAvailable);
        }
    }

// "USB Device Class Definition for Video Devices", Table 2-5
enum HeaderInfo {
    headerInfoFrameId = 1 << 0,
    headerInfoEndOfFrame = 1 << 1,
    headerInfoPresentationTime = 1 << 2,
    headerInfoSourceClockRef = 1 << 3,
    headerInfoPayloadSpecific = 1 << 4,
    headerInfoStillImage = 1 << 5,
    headerInfoError = 1 << 6,
    headerInfoEndOfHeader = 1 << 7,
    };

/** @internal
 * @brief Process a payload transfer
 * 
 * Processes stream, places frames into buffer, signals listeners
 * (such as user callback thread and any polling thread) on new frame
 *
 * @param pbPayload Contents of the payload transfer, either a packet (isochronous) or a full
 * transfer (bulk mode)
 * @param cbPayload Length of the payload transfer
 */
void uvc_process_payload(uvc_stream_handle *strmh, uint8_t* pbPayload, int cbPayload)
    {
    /* ignore empty payload transfers */
    if (cbPayload == 0)
        return;

    int cbHeader = pbPayload[0];
    int cbData   = cbPayload - cbHeader;
    if (cbHeader > cbPayload)
        {
        UVC_DEBUG("bogus packet: actual_len=%d, cbHeader=%d\n", cbPayload, cbHeader);
        return;
        }

    /*
     * From USB Device Class Definition for Video Devices - FAQ, Section 2.25:
     *
     * The bHeaderLength value of the Payload Header can specify any length as long as it is
     * sufficient to allow the Payload Header to hold the information indicated by the bmHeaderInfo
     * bitmap field(s). If the bHeaderLength value is larger than necessary, the extra bytes
     * are simply ignored.
     *
     * As long as the previous class driver versions use the “End of Header” (D7) bit of the
     * bmHeaderInfo byte(s) to determine when the remaining Payload Header values (if any) start,
     * the bmHeaderInfo portion of the Payload Header can be any number of bytes up to the maximum
     * allowed by the bHeaderLength value and any additional Payload Header data.
     *
     * The “End of Header” (D7) bit must be present in each extended bmHeaderInfo byte. The value
     * of the D7 bit will be 0 for all but the last bmHeaderInfo byte.
     */
    uint8_t bHeaderInfo;
    if (cbHeader < 2)
        {
        bHeaderInfo = 0;
        }
    else
        {
        uvc_vc_error_code_control vc_error_code = UVC_ERROR_CODECTRL_NO_ERROR;
        uvc_vs_error_code_control vs_error_code = UVC_VS_ERROR_CODECTRL_NO_ERROR;

        bHeaderInfo = pbPayload[1];

        if (bHeaderInfo & headerInfoError)
            {
            UVC_DEBUG("bad packet: error bit set");
            libusb_clear_halt(strmh->devh->usb_devh, strmh->stream_if->bEndpointAddress);
            uvc_vs_get_error_code(strmh->devh, &vs_error_code, UVC_GET_CUR);
            return;
            }

        bool newFid = !!(bHeaderInfo & headerInfoFrameId);
        if (strmh->fid != newFid && strmh->pFrame->cbData > 0)
            {
            /* The frame ID bit was flipped, but we have image data sitting
               around from prior transfers. This means the camera didn't send
               an EOF for the last transfer of the previous frame. */
            LOGD("frame=%d: previous frame failed to send EOF", strmh->pFrame->frameNumber);
            _uvc_swap_buffers(strmh);
            assert(strmh->pFrame->cbData == 0);
            }

        strmh->fid = newFid;

        // Skip over all the bmHeaderInfo bytes
        int ibHeaderExtra = 1;
        while ((ibHeaderExtra < cbHeader) && (pbPayload[ibHeaderExtra] & headerInfoEndOfHeader)==0)
            {
            ibHeaderExtra++;
            }

        if ((ibHeaderExtra + 4 <= cbHeader) && (bHeaderInfo & headerInfoPresentationTime))
            {
            strmh->pFrame->pts = DW_TO_INT(pbPayload + ibHeaderExtra);
            ibHeaderExtra += 4;
            }

        if ((ibHeaderExtra + 6 <= cbHeader) && (bHeaderInfo & headerInfoSourceClockRef))
            {
            /** @todo read the SOF token counter */
            strmh->pFrame->sourceClockReference = DW_TO_INT(pbPayload + ibHeaderExtra);
            ibHeaderExtra += 6;
            }
        }

    strmh->pFrame->append(pbPayload + cbHeader, cbData);

    /*
     * " D1: End of Frame – This bit is set if the following payload data marks the end of the
     * current video or still image frame (for frame-based formats), or to indicate the end of a
     * codec-specific segment (for stream-based formats). This behavior is optional for all
     * payload formats. For stream-based formats, support for this bit must be indicated via
     * the bmFramingInfo field of the Video Probe and Commit Controls (see section 4.3.1.1,
     * “Video Probe and Commit Controls”).
     */
    if (bHeaderInfo & headerInfoEndOfFrame) // was: under "if (cbData > 0) ..." but spec doesn't indicate that's a condition
        {
        if (cbData == 0)
            {
            // Virtually ALL the the EOF markers we see (from our main test camera) are on payloads
            // with zero-sized data.
            // LOGD("frame=%d: EOF received on zero-sized payload", strmh->pFrame->frameNumber);
            }
        _uvc_swap_buffers(strmh);
        }
    }

void processTransfer(ScopedLock& scopedLock, uvc_stream_handle::StreamTransfer* pStreamTransfer)
    {
    libusb_transfer* pUsbTransfer = pStreamTransfer->pUsbTransfer;
    uvc_stream_handle* strmh = pStreamTransfer->strmh;

    uvc_error rc = UVC_SUCCESS;

    if (strmh->submissionIndexProcessedMax+1 != pStreamTransfer->submissionIndex)
        {
        UVC_ERROR("out-of-order transfer: last=%d cur=%d status=%s", strmh->submissionIndexProcessedMax, pStreamTransfer->submissionIndex, libusb_error_name(pUsbTransfer->status));
        }
    if (0 == strmh->submissionIndexProcessedMax)
        {
        UVC_DEBUG("this is the UVC processTransfer() stream processing thread"); // Just so we know which one it is
        }
    strmh->submissionIndexProcessedMax = max(strmh->submissionIndexProcessedMax, pStreamTransfer->submissionIndex);

    bool freeTransfer = false;
    bool resubmit = strmh->isRunning;
    switch (pUsbTransfer->status)
        {
        case LIBUSB_TRANSFER_COMPLETED:
            if (pUsbTransfer->num_iso_packets == 0)
                {
                /* This is a bulk mode transfer, so it just has one payload transfer */
                uvc_process_payload(strmh, pUsbTransfer->buffer, pUsbTransfer->actual_length);
                }
            else
                {
                /* This is an isochronous mode transfer, so each packet has a payload transfer */
                for (unsigned iPacket = 0; iPacket < pUsbTransfer->num_iso_packets; ++iPacket)
                    {
                    libusb_iso_packet_descriptor *pkt = pUsbTransfer->iso_packet_desc + iPacket;
                    if (pkt->status != 0)
                        {
                        UVC_DEBUG_VERBOSE("bad iso packet: running=%d active=%d index=%d status=%d(%s)", strmh->isRunning, pStreamTransfer->isActive(), pStreamTransfer->index, pkt->status, libusb_error_name(pkt->status));
                        continue;
                        }
                    uint8_t *pktbuf = libusb_get_iso_packet_buffer_simple(pUsbTransfer, iPacket);
                    uvc_process_payload(strmh, pktbuf, pkt->actual_length);
                    }
                }
            /* FALL THROUGH */
        case LIBUSB_TRANSFER_TIMED_OUT:
        case LIBUSB_TRANSFER_STALL:
        case LIBUSB_TRANSFER_OVERFLOW:
            /* if (pUsbTransfer->status == LIBUSB_TRANSFER_STALL)
                {
                clear_halt(pUsbTransfer); // seemed like a good idea, but cancels needed first, so, nada -rga
                } */
            if (!strmh->isRunning)
                {
                freeTransfer = true;
                rc = uvcErrorFromLibUsbTransferStatus(pUsbTransfer->status);
                }
            break;

        case LIBUSB_TRANSFER_NO_DEVICE:
            strmh->deviceDisconnected = true;   // we probably won't see all the transfers complete
            /* fall through */
        case LIBUSB_TRANSFER_CANCELLED:
        case LIBUSB_TRANSFER_ERROR:
        default:
            {
            freeTransfer = true;
            rc = uvcErrorFromLibUsbTransferStatus(pUsbTransfer->status);
            break;
            }
        }

    if (!freeTransfer && resubmit)
        {
        UVC_DEBUG_VERBOSE("status=%d(%s): resubmitting", pUsbTransfer->status, libusb_error_name(pUsbTransfer->status));
        rc = pStreamTransfer->submit();
        if (UVC_SUCCESS != rc)
            {
            freeTransfer = true;
            }
        }

    if (freeTransfer)
        {
        UVC_DEBUG_VERBOSE("freeing transfer: rc=%d(%s) running=%s", rc, uvcErrorName(rc), strmh->isRunning ? "true" : "false");
        pStreamTransfer->free("processTransfer");
        }

    scopedLock.broadcast(strmh->streamingTransferProcessed);
    }

// TODO: flush on error
void LIBUSB_CALL uvcUsbTransferCallback(libusb_transfer *pUsbTransfer)
    {
    uvc_stream_handle::StreamTransfer* pStreamTransfer = (uvc_stream_handle::StreamTransfer*) pUsbTransfer->user_data;
    const bool doLogging = false;
    const bool doValidation = doLogging || true;
    const bool doVerboseLogging = doLogging && false;

    /**
     * We have observed that the streaming gets going, transfers can be completed out of order, but
     * once the streaming is in the run of things, transfers seem to complete in order. We think this
     * has to do with the initial submission of a bunch of fresh transfers racing with completion
     * with an initial prefix of tha bunch, but are not entirely sure.
     *
     * For correctness, we need to process the transfers in-order. Otherwise, the image bytes get
     * really wonky. So we have to fix this.
     */
    LOCK_SCOPE
        {
        typedef uvc_stream_handle::StreamTransfer StreamTransfer;
        uvc_stream_handle* strmh = pStreamTransfer->strmh;
        ScopedLock scopedLock(strmh->streamingLock);
        pStreamTransfer->deactivate();	// here? or later? race? no: we have the lock, so anywhere in here is fine.

        if (pStreamTransfer->submissionIndex < strmh->submissionIndexProcessedMax)
            {
            // Old guy came in after we'd already *processed* guys after him. Don't wait
            // for better sorting since it just won't happen: process him (out of order) now.
            LOGE("retrograde transfer: max=%d cur=%d status=%s", strmh->submissionIndexProcessedMax, pStreamTransfer->submissionIndex, libusb_error_name(pUsbTransfer->status));
            processTransfer(scopedLock, pStreamTransfer);
            }
        else
            {
            // Insert the newly arrived transfer into the list of pending ones in sorted order
            if (strmh->pendingTransfers.isEmpty())
                {
                if (doVerboseLogging) LOGV("append empty: %d", pStreamTransfer->submissionIndex);
                strmh->pendingTransfers.append(pStreamTransfer);
                }
            else if (pStreamTransfer->submissionIndex < strmh->pendingTransfers.first()->submissionIndex)
                {
                if (doLogging) LOGV("prepend: %d", pStreamTransfer->submissionIndex);
                strmh->pendingTransfers.prepend(pStreamTransfer);
                }
            else
                {
                bool found = false;
                for (StreamTransfer* pCur = strmh->pendingTransfers.last(); pCur != strmh->pendingTransfers.stop(); pCur=pCur->prev())
                    {
                    if (pCur->submissionIndex < pStreamTransfer->submissionIndex)
                        {
                        if (doLogging) LOGV("insertafter: prev=%d %d", pCur->submissionIndex, pStreamTransfer->submissionIndex);
                        pStreamTransfer->insertAfter(pCur);
                        found = true;
                        break;
                        }
                    }

                if (!found)
                    {
                    if (doVerboseLogging) LOGV("append last=%d %d", strmh->pendingTransfers.last()->submissionIndex, pStreamTransfer->submissionIndex);
                    strmh->pendingTransfers.append(pStreamTransfer);
                    }
                }

            // Check that all is well
            if (doValidation)
                {
                StreamTransfer* pPrev = NULL;
                for(StreamTransfer* pCur = strmh->pendingTransfers.first(); pCur != strmh->pendingTransfers.stop(); pCur = pCur->next())
                    {
                    if (pPrev != NULL)
                        {
                        Assert(pPrev->submissionIndex < pCur->submissionIndex);
                        }
                    pPrev = pCur;
                    }
                }

            // Process all the transfers that now are in order
            for (StreamTransfer* pCur = strmh->pendingTransfers.first(); pCur != strmh->pendingTransfers.stop(); /*empty*/)
                {
                if (strmh->submissionIndexProcessedMax + 1 == pCur->submissionIndex)
                    {
                    StreamTransfer* pProcess = pCur;
                    pCur = pCur->next();
                    pProcess->remove();
                    if (doVerboseLogging) LOGV("processing: %d", pProcess->submissionIndex);
                    processTransfer(scopedLock, pProcess);
                    }
                else
                    {
                    break;
                    }
                }
            }
        }
    }

/** Begin streaming video from the camera into the callback function.
 * @ingroup streaming
 *
 * @param devh UVC device
 * @param ctrl Control block, processed using {uvc_probe_stream_ctrl} or
 *             {uvc_get_stream_ctrl_format_size}
 * @param pfnUserCallback   User callback function. See {uvc_frame_callback_t} for restrictions.
 * @param flags Stream setup flags, currently undefined. Set this to zero. The lower bit
 * is reserved for backward compatibility.
 */
uvc_error_t
uvc_start_streaming(uvc_device_handle_t *devh, uvc_stream_ctrl_t *ctrl, PfnUserCallback pfnUserCallback, void *pvUserCallback, uint8_t flags)
    {
    uvc_error_t ret;
    uvc_stream_handle *strmh;

    ret = uvc_stream_open_ctrl(devh, &strmh, ctrl);
    if (ret != UVC_SUCCESS)
        return ret;

    ret = uvc_stream_start(strmh, pfnUserCallback, pvUserCallback, flags);
    if (ret != UVC_SUCCESS)
        {
        uvc_stream_close(strmh);
        return ret;
        }

    return UVC_SUCCESS;
    }

/** Begin streaming video from the camera into the callback function.
 * @ingroup streaming
 *
 * @deprecated The stream type (bulk vs. isochronous) will be determined by the
 * type of interface associated with the uvc_stream_ctrl_t parameter, regardless
 * of whether the caller requests isochronous streaming. Please switch to
 * uvc_start_streaming().
 *
 * @param devh UVC device
 * @param ctrl Control block, processed using {uvc_probe_stream_ctrl} or
 *             {uvc_get_stream_ctrl_format_size}
 * @param pfnUserCallback   User callback function. See {uvc_frame_callback_t} for restrictions.
 */
uvc_error_t
uvc_start_iso_streaming(uvc_device_handle_t *devh, uvc_stream_ctrl_t *ctrl, PfnUserCallback pfnUserCallback, void *pvUserCallback)
    {
    return uvc_start_streaming(devh, ctrl, pfnUserCallback, pvUserCallback, 0);
    }

uvc_error_t uvc_stream_start(uvc_stream_handle_t *strmh, PfnUserCallback pfnUserCallback, void *pvUserCallback, uint8_t flags)
    {
	return uvc_stream_start_bandwidth(strmh, pfnUserCallback, pvUserCallback, 0.0f, flags);
    }

/** Begin streaming video from the stream into the callback function.
 * @ingroup streaming
 *
 * @param strmh UVC stream
 * @param pfnUserCallback   User callback function. See {uvc_frame_callback_t} for restrictions.
 * @param bandwidth_factor [0.0f, 1.0f]
 * @param flags Stream setup flags, currently undefined. Set this to zero. The lower bit
 * is reserved for backward compatibility.
 */
uvc_error
uvc_stream_start_bandwidth(uvc_stream_handle* strmh, PfnUserCallback pfnUserCallback, void* pvUserCallback, float bandwidth_factor, uint8_t flags)
    {
    UVC_ENTER();

    if (strmh->isRunning)
        {
        UVC_EXIT(UVC_ERROR_BUSY);
        return uvc_originate_err(UVC_ERROR_BUSY);
        }

    uvc_error ret = UVC_SUCCESS;

    strmh->isRunning = true;
    strmh->fid = false;
    strmh->frameTransitionSeen = false;
    strmh->pFrame->frameNumber = 0;	// first frame will (today) be ignored (see frameTransitionSeen), so first one users see will be 1
    strmh->pFrame->pts = 0;
    strmh->pFrame->sourceClockReference = 0;

    LOCK_SCOPE
        {
        // Not sure of why we need uvc_find_frame_desc and not uvc_find_frame_desc_stream here
        uvc_frame_desc* pFrameDesc = uvc_find_frame_desc(strmh->devh, strmh->cur_ctrl.bFormatIndex, strmh->cur_ctrl.bFrameIndex);
        strmh->frameWidth  = pFrameDesc->wWidth;
        strmh->frameHeight = pFrameDesc->wHeight;
        }

    uvc_stream_ctrl_t *ctrl = &strmh->cur_ctrl;
    uvc_frame_desc_t *pFrameDesc = uvc_find_frame_desc_stream(strmh, ctrl->bFormatIndex, ctrl->bFrameIndex);
    if (pFrameDesc)
        {
        uvc_format_desc *pFormatDesc = pFrameDesc->parent;

        strmh->frameFormat = uvc_frame_format_for_guid(pFormatDesc->guidFormat);
        strmh->setCbFrame();

        // strmh->pFrame was originally set in the strmh ctor, when we didn't know
        // the proper payload size. Now that we do (i.e. we called strmh->setCbFrame())
        // go back and retroactively set that frame's expected payload. Otherwise,
        // we'll drop half the frames we're supposed to deliver to the user because
        // the expected payload size (which would be 0) wouldn't be equal to the actual
        // payload size we got from the USB layer.
        strmh->pFrame->cbExpected = strmh->cbFrameExpected;

        if (strmh->frameFormat != UVC_FRAME_FORMAT_UNKNOWN)
            {
            // Get the interface that provides the chosen format and frame configuration
            const int iUsbInterface = strmh->stream_if->bInterfaceNumber;

            /* USB interface we'll be using */
            const libusb_interface *pUsbInterface = &strmh->devh->info->config->interface[iUsbInterface];

            /* A VS interface uses isochronous transfers iff it has multiple altsettings.
             * (UVC 1.5: 2.4.3. VideoStreaming Interface) */
            bool isIsochronous = pUsbInterface->num_altsetting > 1;
            if (isIsochronous)
                {
                /* Number of packets per transfer */
                int cIsoPackets = 0;

                /* Size of packet transferable from the chosen endpoint */
                size_t cbPerIsoPacket = 0;

                /* The greatest number of bytes that the device might provide, per packet, in this configuration */
                size_t cbConfigPerPacket = strmh->cur_ctrl.dwMaxPayloadTransferSize;
                if ((bandwidth_factor > 0) && (bandwidth_factor < 1.0f))
                    {
                    cbConfigPerPacket = (size_t)(strmh->cur_ctrl.dwMaxPayloadTransferSize * bandwidth_factor);
                    if (0 == cbConfigPerPacket)
                        {
                        cbConfigPerPacket = strmh->cur_ctrl.dwMaxPayloadTransferSize;
                        }
                    }

                if (cbConfigPerPacket > 0)
                    {
                    /* Total amount of data per transfer */
                    size_t cbIsoTotal = 0;

                    /* Go through the altsettings and find one whose packets are at least
                     * as big as our format's maximum per-packet usage. Assume that the
                     * packet sizes are increasing. */
                    strmh->pIsochronousAltSetting = NULL;
                    bool foundAltSetting = false;
                    for (int iAltSetting = 0; iAltSetting < pUsbInterface->num_altsetting; iAltSetting++)
                        {
                        /* For isochronous streaming, we choose an appropriate altsetting for the endpoint
                         * and set up several transfers */
                        strmh->pIsochronousAltSetting = pUsbInterface->altsetting + iAltSetting;

                        /* Find the endpoint with the number specified in the VS header */
                        cbPerIsoPacket = 0;
                        for (int iEndpoint = 0; iEndpoint < strmh->pIsochronousAltSetting->bNumEndpoints; iEndpoint++)
                            {
                            const libusb_endpoint_descriptor *pEndpoint = strmh->pIsochronousAltSetting->endpoint + iEndpoint;
                            if (pEndpoint->bEndpointAddress == pFormatDesc->pStreamingInterface->bEndpointAddress)
                                {
                                cbPerIsoPacket = pEndpoint->wMaxPacketSize;
                                // wMaxPacketSize: [unused:2 (multiplier-1):3 size:11]
                                cbPerIsoPacket = (cbPerIsoPacket & 0x07ff) * (((cbPerIsoPacket >> 11) & 3) + 1);
                                break;
                                }
                            }

                        if (cbPerIsoPacket >= cbConfigPerPacket)
                            {
                            /* Transfers will be at most one frame long: Divide the maximum frame size
                             * by the size of the endpoint and round up */
                            cIsoPackets = (ctrl->dwMaxVideoFrameSize + cbPerIsoPacket - 1) / cbPerIsoPacket;
                            /* But keep a reasonable limit: Otherwise we start dropping data */
                            cIsoPackets = min(cIsoPackets, strmh->cIsoPacketMax);
                            cbIsoTotal = cIsoPackets * cbPerIsoPacket;
                            foundAltSetting = true;
                            break;
                            }
                        }

                    if (foundAltSetting)
                        {
                        // You'd think we'd submit the buffers *before* we started streaming. But that
                        // doesn't seem to work: libusb returns failures on submission. So we live with
                        // what we have here.
                        if (!ret)
                            {
                            ret = strmh->startIsochronousStreaming();
                            }
                        if (!ret)
                            {
                            /* Set up the transfers */
                            for (int iTransfer = 0; iTransfer < strmh->cTransfers; ++iTransfer)
                                {
                                uvc_stream_handle::StreamTransfer* pTransfer = &strmh->rgTransfers[iTransfer];
                                pTransfer->alloc(cIsoPackets, cbIsoTotal);
                                pTransfer->fillUsbTransfer(LIBUSB_TRANSFER_TYPE_ISOCHRONOUS, pFormatDesc, cIsoPackets);
                                libusb_set_iso_packet_lengths(pTransfer->pUsbTransfer, cbPerIsoPacket);
                                }

                            if (!ret) ret = strmh->startUserCallbackThread(pfnUserCallback, pvUserCallback);
                            if (!ret) ret = strmh->submitTransfers();
                            }
                        }
                    else
                        {
                        /* We searched through all the altsettings and found nothing usable */
                        ret = uvc_originate_err(UVC_ERROR_INVALID_MODE);
                        }

                    if (ret < 0)
                        {
                        strmh->pIsochronousAltSetting = NULL;
                        }
                    }
                else
                    {
                    LOGE("cbConfigPerPacket is zero");
                    ret = uvc_originate_err(UVC_ERROR_IO);
                    }
                }
            else
                {
                /* Bulk, not isochronous. This needs more testing */
                for (int iTransfer = 0; iTransfer < strmh->cTransfers; ++iTransfer)
                    {
                    uvc_stream_handle::StreamTransfer* pTransfer = &strmh->rgTransfers[iTransfer];
                    pTransfer->alloc(0, strmh->cur_ctrl.dwMaxPayloadTransferSize);
                    pTransfer->fillUsbTransfer(LIBUSB_TRANSFER_TYPE_BULK, pFormatDesc);
                    }
                if (!ret) ret = strmh->startUserCallbackThread(pfnUserCallback, pvUserCallback);
                if (!ret) ret = strmh->submitTransfers();
                }
            }
        else
            {
            ret = uvc_originate_err(UVC_ERROR_NOT_SUPPORTED);
            }
        }
    else
        {
        ret = uvc_originate_err(UVC_ERROR_INVALID_PARAM);
        }

    if (ret != UVC_SUCCESS)
        {
        strmh->isRunning = false;
        }

    UVC_EXIT(ret);
    return ret;
    }

void uvc_stream_handle::StreamTransfer::fillUsbTransfer(unsigned char type, uvc_format_desc *pFormatDesc, int cIsoPackets)
    {
	pUsbTransfer->dev_handle    = strmh->devh->usb_devh;
	pUsbTransfer->endpoint      = pFormatDesc->pStreamingInterface->bEndpointAddress;
	pUsbTransfer->type          = type;
	pUsbTransfer->buffer        = pbData;
	pUsbTransfer->length        = cbData;
	pUsbTransfer->num_iso_packets = cIsoPackets;
	pUsbTransfer->callback      = uvcUsbTransferCallback;
	pUsbTransfer->user_data     = this;
	pUsbTransfer->timeout       = strmh->msTransfersTimeout;
    }

uvc_error uvc_stream_handle::startIsochronousStreaming()
    {
    UVC_ENTER();
    uvc_error rc = UVC_SUCCESS;
    if (this->pIsochronousAltSetting)
        {
        /* Select the altsetting. I *think* this starts the streaming going. See
         * USB Device Class Definition for Video Devices, Figure 4-1:
         * SET_INTERFACE(>0) starts streaming; SET_INTERFACE(0) stops. */
        rc = this->devh->setInterfaceAltSetting(this->pIsochronousAltSetting->bInterfaceNumber, this->pIsochronousAltSetting->bAlternateSetting);
        }
    else
        rc = uvc_originate_err(UVC_ERROR_INVALID_MODE);
    UVC_RETURN(rc);
    }

uvc_error uvc_stream_handle::stopIsochronousStreaming()
    {
    UVC_ENTER();
    uvc_error rc = UVC_SUCCESS;
    if (this->pIsochronousAltSetting)
        {
        rc = this->devh->setInterfaceAltSetting(this->pIsochronousAltSetting->bInterfaceNumber, 0);
        this->pIsochronousAltSetting = nullptr;
        }
    UVC_RETURN(rc);
    }

uvc_error uvc_stream_handle::startUserCallbackThread(PfnUserCallback pfnUserCallback, void* pvUserCallback)
    {
    UVC_ENTER();
    uvc_error ret = UVC_SUCCESS;
    this->pfnUserCallback = pfnUserCallback;
    this->pvUserCallback = pvUserCallback;

    userCallbackThreadStarted = false;
    if (pfnUserCallback)
        {
        ret = uvcErrorFromErrno(createDetachedThread(uvc_user_callback_main, this));
        if (!ret)
            {
            userCallbackThreadStarted = true;
            pUserCallbackThreadInterlock->waitForThreadStart();
            }
        }
    UVC_RETURN(ret);
    }

uvc_error uvc_stream_handle::submitTransfers()
    {
    UVC_ENTER();
    uvc_error ret = UVC_SUCCESS;

    // Try to submit *all* of our transfers before we process *any* in an attempt to
    // preserve ordering as we startup.
    ScopedLock scopedLock(this->streamingLock);

    // Submit all the transfers
    for (int iTransfer = 0; iTransfer < this->cTransfers; iTransfer++)
        {
        ret = this->rgTransfers[iTransfer].submit();
        if (ret != UVC_SUCCESS)
            {
            break;
            }
        }

    UVC_RETURN(ret);
    }

/** Begin streaming video from the stream into the callback function.
 * @ingroup streaming
 *
 * @deprecated The stream type (bulk vs. isochronous) will be determined by the
 * type of interface associated with the uvc_stream_ctrl_t parameter, regardless
 * of whether the caller requests isochronous streaming. Please switch to
 * uvc_stream_start().
 *
 * @param strmh UVC stream
 * @param pfnUserCallback   User callback function. See {uvc_frame_callback_t} for restrictions.
 */
uvc_error
uvc_stream_start_iso(uvc_stream_handle *strmh, PfnUserCallback pfnUserCallback, void *pvUserCallback)
    {
    return uvc_stream_start(strmh, pfnUserCallback, pvUserCallback, 0);
    }

/** @internal
 * @brief User callback runner thread
 * @note There should be at most one of these per currently streaming device
 * @param arg Device handle
 */
void uvc_user_callback_main(uvc_stream_handle *strmh)
    {
    LOGD("User Callback thread started");
    ThreadInterlock* pInterlock = strmh->pUserCallbackThreadInterlock;
    pInterlock->addRef();    // xyzzy
    pInterlock->signalThreadStart();

    ScopedJniEnv env;

    // loop until the stream isn't running any more
    framenumber_t frameNumberCalledMax = frameNumberInvalid;
    uvc_frame* pFrameCall = NULL;
    for (;;)
        {
        LOCK_SCOPE
            {
            ScopedLock scopedLock(strmh->userCallbackLock);

            // Put back any previous frame
            if (pFrameCall != NULL)
                {
                if (strmh->pFrameUserReturn == NULL)
                    {
                    strmh->pFrameUserReturn = pFrameCall;
                    }
                else
                    {
                    delete pFrameCall;
                    }
                pFrameCall = NULL;
                }

            // Wait until we are fed a new frame that we should dispatch
            while (strmh->isRunning && (NULL==strmh->pFrameUser || strmh->pFrameUser->frameNumber <= frameNumberCalledMax))
                {
                scopedLock.wait(strmh->userCallbackFrameAvailable);
                }

            if (!strmh->isRunning)
                {
                break;
                }

            frameNumberCalledMax = strmh->pFrameUser->frameNumber;
            captureUserFrame(strmh, &pFrameCall);
            }

        /* Don't deliver ill-sized frames to users. As we seem to have fixed transfer order delivery
         * problems, this hardly ever occurs, if ever, but it's a small thing to do and it keeps the
         * user's world so much more clean. We do not, however, check the expected payload length
         * for MJPEG frames because those do not have a constant payload size :monkey: */
        if (pFrameCall && (pFrameCall->cbData==pFrameCall->cbExpected || pFrameCall->frameFormat == UVC_FRAME_FORMAT_MJPEG))
            {
            /* We've observed that the very first frame we get from the camera has bad data.
             * We don't *exactly* know why, but we work around it just the same. */
            if (strmh->frameTransitionSeen)
                {
                // Call the user with provided frame. Don't hold the lock while we do: that will allow
                // more incoming frames to be provided if the user happens to be slow in their processing.
                strmh->pfnUserCallback(pFrameCall, strmh->pvUserCallback);
                }
            strmh->frameTransitionSeen = true;
            }
        }

    pInterlock->signalThreadCompletion();
    releaseRef(pInterlock); // xyzzy
    LOGD("user callback thread stopped");
    }

// User-callback lock must be held.
uvc_error_t captureUserFrame(uvc_stream_handle *strmh, uvc_frame **ppFrameCall)
    {
    uvc_error_t rc = UVC_SUCCESS;
    *ppFrameCall = NULL;

    uvc_frame* pFrameUser = strmh->pFrameUser;
    if (pFrameUser)
        {
        pFrameUser->captureTime = System_nanoTime();
        pFrameUser->frameFormat = strmh->frameFormat;
        pFrameUser->height      = strmh->frameHeight;
        pFrameUser->width       = strmh->frameWidth;

        switch (pFrameUser->frameFormat)
            {
            case UVC_FRAME_FORMAT_YUYV:
                pFrameUser->cbLineStride = pFrameUser->width * 2;
                break;
            case UVC_FRAME_FORMAT_RGB:
            case UVC_FRAME_FORMAT_BGR:
                pFrameUser->cbLineStride = pFrameUser->width * 3;
                break;
            case UVC_FRAME_FORMAT_MJPEG:
            default:
                pFrameUser->cbLineStride = 0; // unknown
                break;
            }

        strmh->pFrameUser = NULL;   // indicate we've taken the frame
        *ppFrameCall = pFrameUser;
        }
    else
        rc = uvc_originate_err(UVC_ERROR_OTHER);

    return rc;
    }


/*
 * Poll for a frame
 * @param timeout_us >0: Wait at most N microseconds; 0: Wait indefinitely; -1: return immediately
 */
uvc_error_t uvc_stream_get_frame(uvc_stream_handle* strmh, uvc_frame** ppFrameResult, int32_t timeout_us)
    {
    UVC_ENTER();
    uvc_error_t rc = UVC_SUCCESS;
    *ppFrameResult = NULL;

    if (!strmh->isRunning) return uvc_originate_err(UVC_ERROR_INVALID_PARAM);
    if (strmh->pfnUserCallback) return uvc_originate_err(UVC_ERROR_CALLBACK_EXISTS);

    LOCK_SCOPE
        {
        ScopedLock scopedLock(strmh->userCallbackLock);

        bool waited = false;
        bool loop = true;
        while (loop)
            {
            // Poll once, and get out if we found a frame
            if (strmh->pFrameUser && strmh->frameNumberPolledMax < strmh->pFrameUser->frameNumber)
                {
                uvc_frame* pFrame = NULL;
                rc = captureUserFrame(strmh, &pFrame);
                if (pFrame)
                    {
                    *ppFrameResult = pFrame;
                    strmh->frameNumberPolledMax = pFrame->frameNumber;
                    }
                break;
                }

            if (waited)
                {
                // Internal error: we waited, they said they had something, but they didn't
                rc = uvc_originate_err(UVC_ERROR_TIMEOUT);
                break;
                }
            else if (timeout_us == -1)
                {
                // return immediately
                rc = uvc_originate_err(UVC_ERROR_OTHER);
                break;
                }
            else if (timeout_us == 0)
                {
                // Wait indefinitely
                scopedLock.wait(strmh->userCallbackFrameAvailable);
                waited = true;
                }
            else
                {
                // Wait a while, but not indefinitely
                int err = scopedLock.waitns(strmh->userCallbackFrameAvailable, timeout_us * 1000L);
                switch (err)
                    {
                    case 0:
                        break;
                    case ETIMEDOUT:
                        rc = uvc_originate_err(UVC_ERROR_TIMEOUT);
                        loop = false;
                        break;
                    default:
                    case EINVAL:
                        rc = uvc_originate_err(UVC_ERROR_OTHER);
                        loop = false;
                        break;
                    }
                waited = true;
                }
            }
        }

    UVC_EXIT(rc);
    return rc;
    }

/** @brief Stop streaming video
 * @ingroup streaming
 *
 * Closes all streams, ends threads and cancels pollers
 *
 * @param devh UVC device
 */
void uvc_stop_streaming(uvc_device_handle_t *devh)
    {
    uvc_stream_handle *strmh, *strmh_tmp;

    DL_FOREACH_SAFE(devh->openStreamsList, strmh, strmh_tmp)
        {
        uvc_stream_close(strmh);
        }
    }

/** waits for all transfers to report transferIsReady() as true */
template<typename Predicate>
void waitForAllTransfers(LPCSTR tracing, uvc_stream_handle *strmh, ScopedLock& scopedLock, Predicate transferIsReady)
    {
    UVC_ENTER("waitForAllTransfers(%s)", tracing);
    for (;;)
        {
        bool shouldWait = false;
        for (int i = 0; i < strmh->cTransfers; i++)
            {
            if (!transferIsReady(&strmh->rgTransfers[i]))
                {
                shouldWait = true;
                break;
                }
            }
        if (!shouldWait)
            {
            LOGD("no extant active transfers: continuing");
            break;
            }

        // No, wait for a change in the status of the transfers, but not forever
        int err = scopedLock.waitms(strmh->streamingTransferProcessed, strmh->msTransfersTimeout*3/2);
        if (ETIMEDOUT==err)
            {
            LOGE("timed out waiting for active transfers to drain; abandoning");
            break;
            }
        else if (err != 0)
            {
            LOGE("unexepected error waiting for active transfers to drain; abandoning");
            break;
            }
        }
    UVC_EXIT_VOID();
    }

/** @brief Stop stream.
 * @ingroup streaming
 *
 * Stops stream, ends threads and cancels pollers
 *
 * @param devh UVC device
 */
uvc_error uvc_stream_stop(uvc_stream_handle *strmh)
    {
    UVC_ENTER();
    uvc_error rc = UVC_SUCCESS;

    LOCK_SCOPE
        {
        ScopedLock scopedLock(strmh->streamingLock);

        // If we're not currently running, then Get Out Of Dodge quickly. And
        // successfully! After we done did our duty if there's nothing more to do!
        if (!strmh->isRunning)
            {
            UVC_EXIT(UVC_SUCCESS);
            return UVC_SUCCESS;
            }

        // OK: we're transitioning from running to non-running (AKA stopped)
        // Set the flag so that transfers will notice as they complete. See
        // processTransfer().
        strmh->isRunning = false;

        // All the transfers should eventually notice and then deactivate. Unless all hell broke loose
        // because the device disappeared. This seems particularly important to the Logitech C270, but it
        // should be just fine for pretty much anyone. Unsure what the exact issue is. But it does, it
        // would seem, give a bit of a pause before we ask for the alt setting switch which happens in
        // stopIschoronousStreaming(); that seems, empirically, important.

        // Cancel any extant transfers so they won't be waiting for data.
        // This takes a long time: why?
        UVC_DEBUG("cancelling extant transfers");
        for (int i = 0; i < strmh->cTransfers; i++)
            {
            strmh->rgTransfers[i].cancel();
            }

        waitForAllTransfers("waiting for cancelled transfers", strmh, scopedLock, [](uvc_stream_handle::StreamTransfer* pTransfer) -> bool
            {
            return !pTransfer->isActive();
            });

        // Ask the camera to stop streaming
        strmh->stopIsochronousStreaming();
        }

    // If there's a user thread, then we want to kick that awake and wait until it completes
    UVC_DEBUG("awakening user thread");
    LOCK_SCOPE
        {
        ScopedLock scopedLock(strmh->userCallbackLock);
        scopedLock.broadcast(strmh->userCallbackFrameAvailable);
        };

    if (strmh->userCallbackThreadStarted)
        {
        UVC_DEBUG("waiting for user callback thread to finish");
        strmh->pUserCallbackThreadInterlock->waitForThreadCompletion(strmh->msUserCallbackThreadExitWait);
        }

    UVC_EXIT(rc);
    return rc;
    }

/** @brief Close stream.
 * @ingroup streaming
 *
 * Closes stream, frees handle and all streaming resources.
 *
 * @param strmh UVC stream handle
 */
void uvc_stream_close(uvc_stream_handle *strmh)
    {
    UVC_ENTER();
    if (strmh->isRunning)
        {
        uvc_stream_stop(strmh);
        }

    delete (strmh);
    UVC_EXIT_VOID();
    }


