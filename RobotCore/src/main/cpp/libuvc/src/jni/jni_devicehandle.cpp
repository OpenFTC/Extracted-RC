/*
Copyright (c) 2017 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
//
// UvcDeviceHandle methods
//
#include <jni.h>
#include <unistd.h>
#include <libuvc.h>
#include <ftc.h>
#include <asm/errno.h>
#include <libuvc/libuvc_internal.h>
#include "ftc.h"

#undef TAG
static LPCSTR TAG = "UvcDeviceHandle";

JNIEXPORT jstring JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetDiagnostics(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;
    jstring result = NULL;
    if (pDeviceHandle)
        {
        TempFile tempFile = TempFile(pDeviceHandle->getContext()->szTempFolder);
        if (tempFile.create())
            {
            NATIVE_API_ONE_CALLER();
            uvc_print_diag(pDeviceHandle, tempFile.pFile);
            result = tempFile.getJavaString(env);
            tempFile.close();
            }
        }
    else
        invalidArgs();
    return result;
    }

JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeAddRefDeviceHandle(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pDeviceHandle)
        {
        NATIVE_API_MANY_CALLERS_VERBOSE();
        pDeviceHandle->addRef();
        }
    else
        invalidArgs();
    }

JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeReleaseRefDeviceHandle(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pDeviceHandle)
        {
        NATIVE_API_ONE_CALLER();
        ::releaseRef(pDeviceHandle);
        }
    else
        invalidArgs();
    }

JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetAutoExposure(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jbyte mode)
    {
    FTC_TRACE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pDeviceHandle)
        {
        NATIVE_API_ONE_CALLER();
        uvc_set_ae_mode(pDeviceHandle,(uint8_t)mode);
        }
    else
        invalidArgs();
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetStreamControlFormatSize(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, JNI_NATIVE_POINTER pointerStreamControl, jint format, jint width, jint height, jint fps)
    {
    FTC_TRACE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;
    /*out*/uvc_stream_ctrl_t* pStreamControl = (uvc_stream_ctrl_t*) pointerStreamControl;
    jint result = 0;
    //
    if (pDeviceHandle && pStreamControl)
        {
        NATIVE_API_ONE_CALLER();
        result = uvc_get_stream_ctrl_format_size(pDeviceHandle, pStreamControl, (enum uvc_frame_format)format, width, height, fps);
        }
    else
        result = uvcInvalidArgs();
    //
    return result;
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeStopAllStreaming(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;
    jint result = UVC_SUCCESS;
    //
    if (pDeviceHandle)
        {
        NATIVE_API_ONE_CALLER();
        uvc_stop_streaming(pDeviceHandle);
        }
    else
        result = uvcInvalidArgs();
    //
    return result;
    }

//--------------------------------------------------------------------------------------------------
// PTZ controls
//--------------------------------------------------------------------------------------------------

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetZoomAbsolute(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jint zoom)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    bool success = false;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_set_zoom_abs(ptrUvcDeviceHandle, zoom);

        if (result == UVC_SUCCESS)
            {
            success = true;
            }
        else
            {
            LOGE("Failed to set zoom to %d : error %d", zoom, static_cast<int>(result));
            }

        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetZoomAbsolute(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint16_t ret = 0;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_zoom_abs(ptrUvcDeviceHandle, &ret, UVC_GET_CUR);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get zoom : error %d", static_cast<int>(result));
            }
        }

    return ret;
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetZoomAbsoluteMin(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint16_t ret = 0;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_zoom_abs(ptrUvcDeviceHandle, &ret, UVC_GET_MIN);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get min zoom : error %d", static_cast<int>(result));
            }
        }

    return ret;
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetZoomAbsoluteMax(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint16_t ret = 0;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_zoom_abs(ptrUvcDeviceHandle, &ret, UVC_GET_MAX);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get max zoom : error %d", static_cast<int>(result));
            }
        }

    return ret;
    }

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetPanTiltAbsolute(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jint pan, jint tilt)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    bool success = false;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_set_pantilt_abs(ptrUvcDeviceHandle, pan, tilt);

        if (result == UVC_SUCCESS)
            {
            success = true;
            }
        else
            {
            LOGE("Failed to set pan/tilt to %d/%d : error %d", pan, tilt, static_cast<int>(result));
            }

        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

JNIEXPORT jlong JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetPanTiltAbsolute(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint64_t ret = 0;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        int32_t pan;
        int32_t tilt;

        uvc_error_t result = uvc_get_pantilt_abs(ptrUvcDeviceHandle, &pan, &tilt, UVC_GET_CUR);

        if (result == UVC_SUCCESS)
            {
            ret |= (uint32_t)pan; //dunno why cast to unsigned is needed, but it is
            ret <<= 32;
            ret |= (uint32_t)tilt; //dunno why cast to unsigned is needed, but it is
            }
        else
            {
            LOGE("Failed to get pan/tilt : error %d", static_cast<int>(result));
            }
        }

    return ret;
    }

JNIEXPORT jlong JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetPanTiltAbsoluteMax(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint64_t ret = 0;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        int32_t pan;
        int32_t tilt;

        uvc_error_t result = uvc_get_pantilt_abs(ptrUvcDeviceHandle, &pan, &tilt, UVC_GET_MAX);

        if (result == UVC_SUCCESS)
            {
            ret |= (uint32_t)pan; //dunno why cast to unsigned is needed, but it is
            ret <<= 32;
            ret |= (uint32_t)tilt; //dunno why cast to unsigned is needed, but it is
            }
        else
            {
            LOGE("Failed to get max pan/tilt : error %d", static_cast<int>(result));
            }
        }

    return ret;
    }

JNIEXPORT jlong JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetPanTiltAbsoluteMin(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint64_t ret = 0;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        int32_t pan;
        int32_t tilt;

        uvc_error_t result = uvc_get_pantilt_abs(ptrUvcDeviceHandle, &pan, &tilt, UVC_GET_MIN);

        if (result == UVC_SUCCESS)
            {
            ret |= (uint32_t)pan; //dunno why cast to unsigned is needed, but it is
            ret <<= 32;
            ret |= (uint32_t)tilt; //dunno why cast to unsigned is needed, but it is
            }
        else
            {
            LOGE("Failed to get min pan/tilt : error %d", static_cast<int>(result));
            }
        }

    return ret;
    }

//--------------------------------------------------------------------------------------------------
// Exposure controls
//--------------------------------------------------------------------------------------------------

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetAePriority(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jboolean aePriority)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    bool success = false;
    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uint8_t value = (uint8_t) aePriority;

        uvc_error_t result = uvc_set_ae_priority(ptrUvcDeviceHandle, value);

        if (result == UVC_SUCCESS)
            {
            success = true;
            }
        else
            {
            LOGE("Failed to set ae priority to %d : error %d", value, static_cast<int>(result));
            }
        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetAePriority(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();

    bool val = false;

    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    if (ptrUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_ae_priority(ptrUvcDeviceHandle, reinterpret_cast<uint8_t*>(&val), UVC_GET_CUR);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get ae priority value : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
            }
        }

    return static_cast<jboolean>(val);
    }

enum class ExposureMode : int32_t // Matches ExposureControl.Mode in Java
{
    UNKNOWN,            ///< Unknown exposure mode.
    AUTO,               ///< Single trigger auto exposure.
    CONTINUOUS_AUTO,    ///< Continuous auto exposure.
    MANUAL,             ///< Manual exposure mode.
    SHUTTER_PRIORITY,   ///< Shutter priority mode.
    APERTURE_PRIORITY,  ///< Aperture priority mode.
};

enum class FocusMode : int32_t // Matches FocusControl.Mode in Java
{
    UNKNOWN,            ///< Unknown focus mode.
    AUTO,               ///< Single trigger auto focus.
    CONTINUOUS_AUTO,    ///< Continuous auto focus.
    MACRO,              ///< Macro mode.
    INFINITY_FOCUS,     ///< Focus to infinity.
    FIXED,              ///< Fixed focus that can't be adjusted.
};


JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeIsExposureModeSupported(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jint modeId)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    ExposureMode mode = ExposureMode(modeId);
    bool success = false;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        const uvc_input_terminal_t* inputTerminal = uvc_get_input_terminals(pUvcDeviceHandle);
        if (inputTerminal->isSupported(UvcCtCtrlSupported::AE_MODE))
            {
            uint8_t supportedModes = 0;
            uvc_error_t rc = uvc_get_ae_mode(pUvcDeviceHandle, &supportedModes, UVC_GET_RES);
            if (!rc)
                {
                switch (mode)
                    {
                    case ExposureMode::AUTO:
                        // Unless otherwise stated auto exposure is always continuous, not only for one exposure
                        success = false;
                        break;

                    case ExposureMode::CONTINUOUS_AUTO:
                        success = (supportedModes & (int)UvcAutoExposureMode::AUTO)!=0;
                        break;

                    case ExposureMode::APERTURE_PRIORITY:
                        success = (supportedModes & (int)UvcAutoExposureMode::APERTURE_PRIORITY) != 0;
                        break;

                    case ExposureMode::MANUAL:
                        success = (supportedModes & (int)UvcAutoExposureMode::MANUAL) != 0;
                        break;

                    case ExposureMode::SHUTTER_PRIORITY:
                        success = (supportedModes & (int)UvcAutoExposureMode::SHUTTER_PRIORITY) != 0;
                        break;

                    default:
                        LOGE("Unknown exposure mode: %d", mode);
                        break;
                    }
                }
            else
                LOGE("uvc_get_ae_mode() failed: %d", rc);
            }
        else
            LOGD("UvcCtCtrlSupported::AE_MODE not supported");
        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetExposureModeId(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    ExposureMode result = ExposureMode::UNKNOWN;

    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uint8_t mode = 0;
        uvc_error_t rc = uvc_get_ae_mode(pUvcDeviceHandle, &mode, UVC_GET_CUR);
        if (!rc)
            {
            switch ((UvcAutoExposureMode)mode)
                {
                case UvcAutoExposureMode::MANUAL:
                    result = ExposureMode::MANUAL;
                    break;

                case UvcAutoExposureMode::AUTO:
                    result = ExposureMode::CONTINUOUS_AUTO;
                    break;

                case UvcAutoExposureMode::APERTURE_PRIORITY:
                    result = ExposureMode::APERTURE_PRIORITY;
                    break;

                case UvcAutoExposureMode::SHUTTER_PRIORITY:
                    result = ExposureMode::SHUTTER_PRIORITY;
                    break;

                default:
                    LOGE("Unknown exposure mode : 0x%02x", mode);
                    break;
                }
            }
        else
            LOGE("uvc_get_ae_mode() failed: %d", rc);
        }

    return (jint)result;
    }

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetExposureMode(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jint modeId)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    ExposureMode mode = ExposureMode(modeId);

    bool success = true;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        switch (mode)
            {
            case ExposureMode::AUTO:
                // Unless otherwise stated auto exposure is always continuous, not only for one exposure
                LOGE("Auto exposure mode for only one exposure is not supported");
                success = false;
                break;

            case ExposureMode::CONTINUOUS_AUTO:
                {
                uvc_error_t rc = uvc_set_ae_mode(pUvcDeviceHandle, (int)UvcAutoExposureMode::AUTO);
                if (rc != UVC_SUCCESS)
                    {
                    LOGE("Failed to set exposure mode to AUTO : error %d.", static_cast<int>(rc));
                    success = false;
                    }
                }
                break;

            case ExposureMode::APERTURE_PRIORITY:
                {
                uvc_error_t rc = uvc_set_ae_mode(pUvcDeviceHandle, (int)UvcAutoExposureMode::APERTURE_PRIORITY);
                if (rc != UVC_SUCCESS)
                    {
                    LOGE("Failed to set exposure mode to APERTURE PRIORITY : error %d", static_cast<int>(rc));
                    success = false;
                    }
                }
                break;

            case ExposureMode::MANUAL:
                {
                uvc_error_t rc = uvc_set_ae_mode(pUvcDeviceHandle, (int)UvcAutoExposureMode::MANUAL);
                if (rc != UVC_SUCCESS)
                    {
                    LOGE("Failed to set exposure mode to MANUAL : error %d", static_cast<int>(rc));
                    success = false;
                    }
                }
                break;

            case ExposureMode::SHUTTER_PRIORITY:
                {
                uvc_error_t rc = uvc_set_ae_mode(pUvcDeviceHandle, (int)UvcAutoExposureMode::SHUTTER_PRIORITY);
                if (rc != UVC_SUCCESS)
                    {
                    LOGE("Failed to set exposure mode to SHUTTER PRIORITY : error %d", static_cast<int>(rc));
                    success = false;
                    }
                }
                break;

            default:
                LOGE("Unknown exposure mode : %d", mode);
                success = false;
                break;
            }
        }
    else
        success = false;

    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

//--------------------------------------------------------------------------------------------------

int64_t nsFromUvcExposure(int64_t exposure)
    {
    // UVC exposure time unit is 100us, we want ns
    // 4.2.2.1.4 Exposure Time (Absolute) Control
    return exposure * HUNDRED_THOUSAND;
    }

int64_t uvcExposureFromNs(int64_t ns)
    {
    // UVC exposure time unit is 100us, while the SDK's expected unit is 1ns
    return ns / HUNDRED_THOUSAND;
    }

const uint32_t undefinedExposure = 0; // 4.2.2.1.4 Exposure Time (Absolute) Control, four byte value, units of 1/10,000 s

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeIsExposureSupported(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    bool success = false;
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        const uvc_input_terminal_t* inputTerminal = uvc_get_input_terminals(pUvcDeviceHandle);
        success = inputTerminal->isSupported(UvcCtCtrlSupported::EXPOSURE_TIME_ABSOLUTE);
        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

JNIEXPORT jlong JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMinExposure(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    uint32_t exposure = undefinedExposure;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_exposure_abs(pUvcDeviceHandle, &exposure, UVC_GET_MIN);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get exposure : error %d", static_cast<int>(result));
            }
        }
    return nsFromUvcExposure(exposure);
    }

JNIEXPORT jlong JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMaxExposure(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    uint32_t exposure = undefinedExposure;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_exposure_abs(pUvcDeviceHandle, &exposure, UVC_GET_MAX);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get exposure : error %d", static_cast<int>(result));
            }
        }
    return nsFromUvcExposure(exposure);
    }

JNIEXPORT jlong JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetExposure(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    uint32_t exposure = undefinedExposure;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_exposure_abs(pUvcDeviceHandle, &exposure, UVC_GET_CUR);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get exposure : error %d", static_cast<int>(result));
            }
        }
    jlong result = nsFromUvcExposure(exposure);
    // UVC_DEBUG("exposure=%lld", result);
    return result;
    }

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetExposure(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jlong nsExposure)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    bool success = false;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uint32_t value = static_cast<uint32_t>(uvcExposureFromNs(nsExposure));
        uvc_error_t result = uvc_set_exposure_abs(pUvcDeviceHandle, value);
        if (result == UVC_SUCCESS)
            {
            success = true;
            }
        else
            LOGE("Failed to set exposure time to %d : error %d", value, static_cast<int>(result));
        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

//--------------------------------------------------------------------------------------------------
// White balance controls
//--------------------------------------------------------------------------------------------------

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMinWhiteBalanceTemperature(JNIEnv* env, jclass type, jlong pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint16_t temperature = 0;

    if(ptrUvcDeviceHandle != NULL)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_white_balance_temperature(ptrUvcDeviceHandle, &temperature, UVC_GET_MIN);
        if (result != UVC_SUCCESS)
            {
                LOGE("Failed to get min white balance temperature : error %d", static_cast<int>(result));
            }
        }

    return temperature;
    }

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMaxWhiteBalanceTemperature(JNIEnv* env, jclass type, jlong pointer)
{
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint16_t temperature = 0;

    if(ptrUvcDeviceHandle != NULL)
    {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_white_balance_temperature(ptrUvcDeviceHandle, &temperature, UVC_GET_MAX);
        if (result != UVC_SUCCESS)
        {
            LOGE("Failed to get max white balance temperature : error %d", static_cast<int>(result));
        }
    }

    return temperature;
}

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetWhiteBalanceTemperature(JNIEnv* env, jclass type, jlong pointer, jint temperature)
{
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    if(ptrUvcDeviceHandle != NULL)
    {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_set_white_balance_temperature(ptrUvcDeviceHandle, temperature);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to set white balance temperature : error %d", static_cast<int>(result));
            return false;
            }

        return true;
    }

    return false;
}

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetWhiteBalanceTemperature(JNIEnv* env, jclass type, jlong pointer)
{
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint16_t temperature = 0;

    if(ptrUvcDeviceHandle != NULL)
    {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_white_balance_temperature(ptrUvcDeviceHandle, &temperature, UVC_GET_CUR);
        if (result != UVC_SUCCESS)
        {
            LOGE("Failed to get current white balance temperature : error %d", static_cast<int>(result));
        }
    }

    return temperature;
}

namespace Java_WhiteBalanceControl_Mode /* If you change this order, remember to update WhiteBalanceControl.java! */
{
    enum
    {
        UNKNOWN, // 0
        AUTO,    // 1
        MANUAL   // 2
    };
}

namespace UVC_WhiteBalanceMode
{
    enum
    {
        MANUAL, // 0
        AUTO    // 1
    };
}

JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetWhiteBalanceMode(JNIEnv* env, jclass type, jlong pointer)
{
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    uint8_t mode = Java_WhiteBalanceControl_Mode::UNKNOWN;

    if(ptrUvcDeviceHandle != NULL)
    {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_white_balance_temperature_auto(ptrUvcDeviceHandle, &mode, UVC_GET_CUR);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get current white balance mode : error %d", static_cast<int>(result));
            return Java_WhiteBalanceControl_Mode::UNKNOWN;
            }

        return mode == UVC_WhiteBalanceMode::AUTO ? Java_WhiteBalanceControl_Mode::AUTO : Java_WhiteBalanceControl_Mode::MANUAL;
    }

    return Java_WhiteBalanceControl_Mode::UNKNOWN;
}

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetWhiteBalanceMode(JNIEnv* env, jclass type, jlong pointer, jint mode)
{
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;

    if(ptrUvcDeviceHandle != NULL)
    {
        NATIVE_API_ONE_CALLER_VERBOSE();

        if(mode == Java_WhiteBalanceControl_Mode::AUTO || mode == Java_WhiteBalanceControl_Mode::MANUAL)
        {
            uint8_t uvcMode = (mode == Java_WhiteBalanceControl_Mode::AUTO ? UVC_WhiteBalanceMode::AUTO : UVC_WhiteBalanceMode::MANUAL);
            uvc_error_t result = uvc_set_white_balance_temperature_auto(ptrUvcDeviceHandle, uvcMode);
            if (result != UVC_SUCCESS)
            {
                LOGE("Failed to set white balance mode : error %d", static_cast<int>(result));
            }
            else
            {
                return true;
            }
        }
    }

    return false;
}

//--------------------------------------------------------------------------------------------------
// Gain Controls
//--------------------------------------------------------------------------------------------------

/*
 * Get min gain
 */
JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMinGain(JNIEnv *env, jclass type, jlong pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    uint16_t gain = 0;
    if (ptrUvcDeviceHandle != NULL)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_gain(ptrUvcDeviceHandle, &gain, UVC_GET_MIN);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get min gain : error %d", static_cast<int>(result));
            }
        }
    return gain;
    }

/*
 * Get max gain
 */
JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMaxGain(JNIEnv *env, jclass type, jlong pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    uint16_t gain = 0;
    if (ptrUvcDeviceHandle != NULL)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_gain(ptrUvcDeviceHandle, &gain, UVC_GET_MAX);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get max gain : error %d", static_cast<int>(result));
            }
        }
    return gain;
    }

/*
 * Get current gain
 */
JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetGain(JNIEnv *env, jclass type, jlong pointer)
    {
    FTC_TRACE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    uint16_t gain = 0;
    if (ptrUvcDeviceHandle != NULL)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        uvc_error_t result = uvc_get_gain(ptrUvcDeviceHandle, &gain, UVC_GET_CUR);

        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get current gain : error %d", static_cast<int>(result));
            }
        }
    return gain;
    }

/*
 * Set gain
 */
JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetGain(JNIEnv *env, jclass type, jlong pointer, jint gain)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* ptrUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    bool success = false;
    if (ptrUvcDeviceHandle != NULL)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_set_gain(ptrUvcDeviceHandle, static_cast<uint16_t>(gain));

        if (result == UVC_SUCCESS)
            {
            success = true;
            }
        else
            {
            LOGE("Failed to set gain : error %d", static_cast<int>(result));
            }
        }
    return static_cast<jboolean>(success);
    }


//--------------------------------------------------------------------------------------------------
// Focus controls
// 4.2.2.1.6 Focus (Absolute) Control
// units in mm
//--------------------------------------------------------------------------------------------------

const short undefinedFocusLength = -1;

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeIsFocusLengthSupported(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    bool success = false;
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        const uvc_input_terminal_t* inputTerminal = uvc_get_input_terminals(pUvcDeviceHandle);
        success = inputTerminal->isSupported(UvcCtCtrlSupported::FOCUS_ABSOLUTE);
        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }


JNIEXPORT jdouble JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMinFocusLength(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    short minFocusVal = undefinedFocusLength;
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_focus_abs(pUvcDeviceHandle, (uint16_t*)&minFocusVal, UVC_GET_MIN);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get min focus value : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
            }
        }
    return minFocusVal;
    }

JNIEXPORT jdouble JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetMaxFocusLength(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    short maxFocusVal = undefinedFocusLength;
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_focus_abs(pUvcDeviceHandle, (uint16_t*)&maxFocusVal, UVC_GET_MAX);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get max focus value : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
            }
        }
    return maxFocusVal;
    }

JNIEXPORT jdouble JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetFocusLength(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    short focusVal = undefinedFocusLength;
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result = uvc_get_focus_abs(pUvcDeviceHandle, (uint16_t*)&focusVal, UVC_GET_CUR);
        if (result != UVC_SUCCESS)
            {
            LOGE("Failed to get focus value : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
            }
        }
    UVC_DEBUG("focusLength=%d", focusVal);
    return focusVal;
    }

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetFocusLength(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jdouble value)
    {
    FTC_TRACE_VERBOSE();
    bool success = false;
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uint16_t focusVal = static_cast<uint16_t>(value);
        uvc_error_t result = uvc_set_focus_abs(pUvcDeviceHandle, focusVal);
        if (result == UVC_SUCCESS)
            {
            success = true;
            }
        else
            LOGE("Failed to set focus value to %d : error %d(%s)", focusVal, static_cast<int>(result), uvcErrorName(result));
        }
    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

//--------------------------------------------------------------------------------------------------

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeIsFocusModeSupported(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jint modeId)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    FocusMode mode = FocusMode(modeId);

    bool success = false;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        const uvc_input_terminal_t* inputTerminal = uvc_get_input_terminals(pUvcDeviceHandle);
        switch (mode)
            {
            case FocusMode::AUTO:
                // Unless otherwise stated auto focus is always continuous, not only for one exposure
                success = false;
                break;

            case FocusMode::CONTINUOUS_AUTO:
                success = inputTerminal->isSupported(UvcCtCtrlSupported::FOCUS_AUTO);
                break;

            case FocusMode::MACRO:
            case FocusMode::INFINITY_FOCUS:
                success = inputTerminal->isSupported(UvcCtCtrlSupported::FOCUS_SIMPLE);
                break;

            case FocusMode::FIXED:
                // Fixed focus mode is always supported. But whether we support
                // getting / setting the absolute focus value is another matter.
                success = true;
                break;

            default:
                LOGE("Unknown focus mode: %d", mode);
            }
        }

    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }


JNIEXPORT jint JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeGetFocusModeId(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pDeviceHandle = (uvc_device_handle_t*) pointer;

    // If all else fails, then we are essentially on fixed focus mode
    FocusMode result = FocusMode::FIXED;

    if (pDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();

        // Check whether auto focus is on
        uint8_t state = 0;
        uvc_error rc = uvc_get_focus_auto(pDeviceHandle, &state, UVC_GET_CUR);
        if (rc == UVC_SUCCESS && state == 1)
            {
            result = FocusMode::CONTINUOUS_AUTO;
            }
        else
            {
            // Check whether simple focus mode is set to macro or scene
            rc = uvc_get_focus_simple_range(pDeviceHandle, &state, UVC_GET_CUR);
            if (rc == UVC_SUCCESS)
                {
                switch ((UvcSimpleFocusMode)state)
                    {
                    case UvcSimpleFocusMode::MACRO:
                        result = FocusMode::MACRO;
                        break;
                    case UvcSimpleFocusMode::SCENE:
                        result = FocusMode::INFINITY_FOCUS;
                        break;
                    default: break;
                    }
                }
            }
        }

    return (jint)result;
    }

JNIEXPORT jboolean JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcDeviceHandle_nativeSetFocusMode(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jint modeId)
    {
    FTC_TRACE_VERBOSE();
    uvc_device_handle_t* pUvcDeviceHandle = (uvc_device_handle_t*) pointer;
    FocusMode mode = FocusMode(modeId);

    bool success = true;
    if (pUvcDeviceHandle)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_error_t result;

        switch (mode)
            {
            case FocusMode::AUTO:
                // Unless otherwise stated auto focus is always continuous, not only for one exposure
                LOGE("Auto focus mode for only one exposure is not supported");
                success = false;
                break;

            case FocusMode::CONTINUOUS_AUTO:
                {
                result = uvc_set_focus_auto(pUvcDeviceHandle, (int)UvcAutoFocusMode::AUTO);
                if (result == UVC_SUCCESS)
                    {
                    // all is well
                    }
                else
                    {
                    LOGE("Failed to set auto focus mode to AUTO : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
                    success = false;
                    }
                }
                break;

            case FocusMode::MACRO:
                {
                result = uvc_set_focus_simple_range(pUvcDeviceHandle, (int)UvcSimpleFocusMode::MACRO);
                if (result == UVC_SUCCESS)
                    {
                    // all is wsell
                    }
                else
                    {
                    LOGE("Failed to set simple focus mode to MACRO : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
                    success = false;
                    }
                }
                break;

            case FocusMode::INFINITY_FOCUS:
                {
                result = uvc_set_focus_simple_range(pUvcDeviceHandle, (int)UvcSimpleFocusMode::SCENE);
                if (result == UVC_SUCCESS)
                    {
                    }
                else
                    {
                    LOGE("Failed to set simple focus mode to SCENE : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
                    success = false;
                    }
                }
                break;

            case FocusMode::FIXED:
                {
                // If we support auto focus, set the auto focus mode to FIXED
                const uvc_input_terminal_t* inputTerminal = uvc_get_input_terminals(pUvcDeviceHandle);
                if (inputTerminal->isSupported(UvcCtCtrlSupported::FOCUS_AUTO))
                    {
                    result = uvc_set_focus_auto(pUvcDeviceHandle, (int)UvcAutoFocusMode::FIXED);
                    if (result != UVC_SUCCESS)
                        {
                        LOGE("Failed to set auto focus mode to FIXED : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
                        success = false;
                        }
                    }

                // If we support simple focus, set it to FULL_RANGE mode
                if (success)
                    {
                    if (inputTerminal->isSupported(UvcCtCtrlSupported::FOCUS_SIMPLE))
                        {
                        result = uvc_set_focus_simple_range(pUvcDeviceHandle, (int)UvcSimpleFocusMode::FULL_RANGE);
                        if (result != UVC_SUCCESS)
                            {
                            LOGE("Failed to set simple focus mode to FULL_RANGE : error %d(%s)", static_cast<int>(result), uvcErrorName(result));
                            success = false;
                            }
                        }
                    }
                }
                break;

            default:
                LOGE("Unknown focus mode: %d", mode);
                success = false;
                break;
            }
        }
    else
        success = false;

    return jboolean(success ? JNI_TRUE : JNI_FALSE);
    }

