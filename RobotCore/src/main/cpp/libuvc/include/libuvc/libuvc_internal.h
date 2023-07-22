/** @file libuvc_internal.h
  * @brief Implementation-specific UVC constants and structures.
  * @cond include_hidden
  */
#ifndef LIBUVC_INTERNAL_H
#define LIBUVC_INTERNAL_H

#include <assert.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <pthread.h>
#include <signal.h>
#include <libusb.h>
#include <unistd.h>
#include "utlist.h"
#include "ftc.h"

//--------------------------------------------------------------------------------------------------
// Misc
//--------------------------------------------------------------------------------------------------

/** Converts an unaligned 8-byte little-endian integer into an int64 */
#define QW_TO_LONG(p) \
 ((p)[0] | ((p)[1] << 8) | ((p)[2] << 16) | ((p)[3] << 24) \
  | ((uint64_t)(p)[4] << 32) | ((uint64_t)(p)[5] << 40) \
  | ((uint64_t)(p)[6] << 48) | ((uint64_t)(p)[7] << 56))
/** Converts an unaligned four-byte little-endian integer into an int32 */
#define DW_TO_INT(p) ((p)[0] | ((p)[1] << 8) | ((p)[2] << 16) | ((p)[3] << 24))
/** Converts an unaligned two-byte little-endian integer into an int16 */
#define SW_TO_SHORT(p) ((p)[0] | ((p)[1] << 8))
/** Converts an int16 into an unaligned two-byte little-endian integer */
#define SHORT_TO_SW(s, p) \
  (p)[0] = (s); \
  (p)[1] = (s) >> 8;
/** Converts an int32 into an unaligned four-byte little-endian integer */
#define INT_TO_DW(i, p) \
  (p)[0] = (i); \
  (p)[1] = (i) >> 8; \
  (p)[2] = (i) >> 16; \
  (p)[3] = (i) >> 24;
/** Converts an int64 into an unaligned 8-byte little-endian integer */
#define LONG_TO_QW(i, p) \
  (p)[0] = (i); \
  (p)[1] = (i) >> 8; \
  (p)[2] = (i) >> 16; \
  (p)[3] = (i) >> 24; \
  (p)[4] = (i) >> 32; \
  (p)[5] = (i) >> 40; \
  (p)[6] = (i) >> 48; \
  (p)[7] = (i) >> 56;

/** Selects the nth item in a doubly linked list. n=-1 selects the last item. */
#define DL_NTH(head, out, n) \
  do { \
    int dl_nth_i = 0; \
    LDECLTYPE(head) dl_nth_p = (head); \
    if ((n) < 0) { \
      while (dl_nth_p && dl_nth_i > (n)) { \
        dl_nth_p = dl_nth_p->prev; \
        dl_nth_i--; \
      } \
    } else { \
      while (dl_nth_p && dl_nth_i < (n)) { \
        dl_nth_p = dl_nth_p->next; \
        dl_nth_i++; \
      } \
    } \
    (out) = dl_nth_p; \
  } while (0);

/* http://stackoverflow.com/questions/19452971/array-size-macro-that-rejects-pointers */
#undef ARRAYSIZE
#define IS_INDEXABLE(arg) (sizeof(arg[0]))
#define IS_ARRAY(arg) (IS_INDEXABLE(arg) && (((void *) &arg) == ((void *) arg)))
#define ARRAYSIZE(arr) (sizeof(arr) / (IS_ARRAY(arr) ? sizeof(arr[0]) : 0))

//--------------------------------------------------------------------------------------------------
// Enums & constants
//--------------------------------------------------------------------------------------------------

/** Video interface subclass code (A.2) */
enum uvc_int_subclass_code {
  UVC_SC_UNDEFINED = 0x00,
  UVC_SC_VIDEOCONTROL = 0x01,
  UVC_SC_VIDEOSTREAMING = 0x02,
  UVC_SC_VIDEO_INTERFACE_COLLECTION = 0x03
};

/** Video interface protocol code (A.3) */
enum uvc_int_proto_code {
  UVC_PC_PROTOCOL_UNDEFINED = 0x00
};

/** VideoControl interface descriptor subtype (A.5) */
enum uvc_vc_desc_subtype {
  UVC_VC_DESCRIPTOR_UNDEFINED = 0x00,
  UVC_VC_HEADER = 0x01,
  UVC_VC_INPUT_TERMINAL = 0x02,
  UVC_VC_OUTPUT_TERMINAL = 0x03,
  UVC_VC_SELECTOR_UNIT = 0x04,
  UVC_VC_PROCESSING_UNIT = 0x05,
  UVC_VC_EXTENSION_UNIT = 0x06
};

/** UVC endpoint descriptor subtype (A.7) */
enum uvc_ep_desc_subtype {
  UVC_EP_UNDEFINED = 0x00,
  UVC_EP_GENERAL = 0x01,
  UVC_EP_ENDPOINT = 0x02,
  UVC_EP_INTERRUPT = 0x03
};

/** VideoControl interface control selector (A.9.1) */
enum uvc_vc_ctrl_selector {
  UVC_VC_CONTROL_UNDEFINED = 0x00,
  UVC_VC_VIDEO_POWER_MODE_CONTROL = 0x01,
  UVC_VC_REQUEST_ERROR_CODE_CONTROL = 0x02
};

/** Terminal control selector (A.9.2) */
enum uvc_term_ctrl_selector {
  UVC_TE_CONTROL_UNDEFINED = 0x00
};

/** Selector unit control selector (A.9.3) */
enum uvc_su_ctrl_selector {
  UVC_SU_CONTROL_UNDEFINED = 0x00,
  UVC_SU_INPUT_SELECT_CONTROL = 0x01
};

/** Extension unit control selector (A.9.6) */
enum uvc_xu_ctrl_selector {
  UVC_XU_CONTROL_UNDEFINED = 0x00
};

/** VideoStreaming interface control selector (A.9.7) */
enum uvc_vs_ctrl_selector {
  UVC_VS_CONTROL_UNDEFINED = 0x00,
  UVC_VS_PROBE_CONTROL = 0x01,
  UVC_VS_COMMIT_CONTROL = 0x02,
  UVC_VS_STILL_PROBE_CONTROL = 0x03,
  UVC_VS_STILL_COMMIT_CONTROL = 0x04,
  UVC_VS_STILL_IMAGE_TRIGGER_CONTROL = 0x05,
  UVC_VS_STREAM_ERROR_CODE_CONTROL = 0x06,
  UVC_VS_GENERATE_KEY_FRAME_CONTROL = 0x07,
  UVC_VS_UPDATE_FRAME_SEGMENT_CONTROL = 0x08,
  UVC_VS_SYNC_DELAY_CONTROL = 0x09
};

/** Status packet type (2.4.2.2) */
enum uvc_status_type {
  UVC_STATUS_TYPE_CONTROL = 1,
  UVC_STATUS_TYPE_STREAMING = 2
};

/** Payload header flags (2.4.3.3) */
#define UVC_STREAM_EOH (1 << 7)
#define UVC_STREAM_ERR (1 << 6)
#define UVC_STREAM_STI (1 << 5)
#define UVC_STREAM_RES (1 << 4)
#define UVC_STREAM_SCR (1 << 3)
#define UVC_STREAM_PTS (1 << 2)
#define UVC_STREAM_EOF (1 << 1)
#define UVC_STREAM_FID (1 << 0)

/** Control capabilities (4.1.2) */
#define UVC_CONTROL_CAP_GET (1 << 0)
#define UVC_CONTROL_CAP_SET (1 << 1)
#define UVC_CONTROL_CAP_DISABLED (1 << 2)
#define UVC_CONTROL_CAP_AUTOUPDATE (1 << 3)
#define UVC_CONTROL_CAP_ASYNCHRONOUS (1 << 4)

//--------------------------------------------------------------------------------------------------
// uvc_streaming_interface
//--------------------------------------------------------------------------------------------------

struct uvc_streaming_interface;
struct uvc_device_info;

/** VideoStream interface */
typedef struct uvc_streaming_interface {
  struct uvc_streaming_interface *prev, *next;
  struct uvc_device_info *parent;
  /** Interface number */
  uint8_t bInterfaceNumber;
  /** Video formats that this interface provides */
  struct uvc_format_desc *format_descs;
  /** USB endpoint to use when communicating with this interface */
  uint8_t bEndpointAddress;
  uint8_t bTerminalLink;
  uint8_t bmInfo;	// XXX
  uint8_t bStillCaptureMethod;	// XXX
  uint8_t bTriggerSupport;	// XXX
  uint8_t bTriggerUsage;	// XXX
  uint64_t *bmaControls;	// XXX
} uvc_streaming_interface_t;

//--------------------------------------------------------------------------------------------------
// uvc_control_interface
//--------------------------------------------------------------------------------------------------

/** VideoControl interface */
typedef struct uvc_control_interface {
  struct uvc_device_info *parent;
  struct uvc_input_terminal *input_term_descs;
  struct uvc_output_terminal *output_term_descs;
  // struct uvc_output_terminal *output_term_descs;
  struct uvc_selector_unit *selector_unit_descs;
  struct uvc_processing_unit *processing_unit_descs;
  struct uvc_extension_unit *extension_unit_descs;
  uint16_t bcdUVC;
  uint32_t dwClockFrequency;
  uint8_t bEndpointAddress;
  /** Interface number */
  uint8_t bInterfaceNumber;
} uvc_control_interface_t;

//--------------------------------------------------------------------------------------------------
// uvc_context
//--------------------------------------------------------------------------------------------------

struct uvc_stream_ctrl;

/** Context within which we communicate with devices */
struct uvc_context : ZeroOnNew
    {
    uvc_context()
        {
        /* ZeroOnNew does what we need for members that don't have their own ctors.
         * So: nothing explicit to do */
        }
    uvc_error init(LPCSTR usbsfs, int buildVersionSDKInt, LPCSTR szTempFolder, bool forceJavaUsbEnumerationKitKat);
    void signalEventThread();
    ~uvc_context();
    Lock                    lock;

    /** Underlying context for USB communication */
    libusb_context *        pLibUsbContext;
    LPCSTR                  szUsbfs;
    LPCSTR                  szTempFolder;

    /** List of open devices in this context */
    uvc_device_handle_t*    openDevicesList;

    bool                    usbThreadStarted;
    int                     stopEventThread;
    ThreadInterlock*        pUsbThreadInterlock;
    };

//--------------------------------------------------------------------------------------------------
// CamCompat
//--------------------------------------------------------------------------------------------------

#include "usbconstants.h"

enum class CamCompatFeature
    {
    /* To quote saki:
     *      "libusb_release_interface *should* reset the alternate setting to the first available,
     *       but sometimes (e.g. on Darwin) it doesn't. Thus, we do it explicitly here.
     *       This is needed to de-initialize certain cameras.
     *
     *       XXX but resetting the alt setting here many times leads trouble
	 *       on GT-N7100(international Galaxy Note2 at lease with Android4.4.2)
	 *       so we add flag to avoid the issue
     */
    ResetAltSettingOnRelease,

    /** Some cameras just take too very long to have a USB reset thrown their way (like, seconds) */
    AvoidLibUsbResetDevice,
    };

#define makeVidPid(vid, pid) (((vid)<<16) | (pid))
#define vidOfVidPid(vidpid)  (((vidpid)>>16) & 0xFFFF)

struct CameraCompatManager
    {
    static bool hasFeature(int vid, int pid, CamCompatFeature feature)
        {
        return hasFeature(makeVidPid(vid,pid), feature);
        }

    static bool hasFeature(int vidpid, CamCompatFeature feature)
        {
        switch (feature)
            {
            case CamCompatFeature::ResetAltSettingOnRelease:
                {
                switch (vidpid)
                    {
                    // case makeVidPid(VENDOR_ID_LOGITECH, PRODUCT_ID_LOGITECH_C270): // unclear if this helps
                    //    return true;
                    }
                }
                break;
            case CamCompatFeature::AvoidLibUsbResetDevice:
                {
                if (vidOfVidPid(vidpid) == VENDOR_ID_MICROSOFT)
                    {
                    // All the Microsoft cameras tested so far (all listed below individually) have
                    // had problems with reset, so we make a default for all Microsoft cameras.
                    return true;
                    }
                switch (vidpid)
                    {
                    case makeVidPid(VENDOR_ID_ARDUCAM, PRODUCT_ID_ARDUCAM_OV9281):
                    case makeVidPid(VENDOR_ID_MICROSOFT, PRODUCT_ID_MICROSOFT_LIFECAM_HD_3000): // takes six seconds
                    case makeVidPid(VENDOR_ID_MICROSOFT, PRODUCT_ID_MICROSOFT_LIFECAM_HD_5000): // takes many seconds
                    case makeVidPid(VENDOR_ID_MICROSOFT, PRODUCT_ID_MICROSOFT_LIFECAM_STUDIO): // takes many seconds
                    case makeVidPid(VENDOR_ID_AUSDOM, PRODUCT_ID_AUSDOM_AW615): // takes many seconds
                        return true;
                    }
                }
                break;
            }
        return false;
        }
    };


//--------------------------------------------------------------------------------------------------
// uvc_device
//--------------------------------------------------------------------------------------------------

struct uvc_device : RefCounted
    {
    struct uvc_context *ctx;    // no ref
    libusb_device *usb_dev;

    // Cache a notion of what this camera is so we can do product-dependent bug fix workarounds
    int vid = 0;
    int pid = 0;
    bool vidpidAcquired = false;

    // An open handle to this device, provided to us from Java, usually obtained from UsbDeviceConnection.
    // Note that we have our own dup of the handle; we need to close it when we're done if not FD_NONE.
    // Note that whenever the uvc_device is opened (in a call to uvc_open), the handle
    // is again dup()d to obtain an independent lifetime.
    int fdJava = FD_NONE;

    // The path in the USB file system of that open handle
    LPCSTR szUsbPath = nullptr;

    uvc_device(uvc_context_t* ctx, libusb_device* pUsbDevice)
        {
        UVC_ENTER();
        this->ctx = ctx;
        this->usb_dev = pUsbDevice;
        libusb_ref_device2(pUsbDevice, "uvc_device::uvc_device");
        acquireVidPid();
        UVC_EXIT_VOID();
        }

    uvc_error open(struct libusb_device_handle **pusb_devh)
        {
        UVC_ENTER();
        uvc_error ret = uvc_error(libusb_open(usb_dev, pusb_devh, fdJava, szUsbPath));
        if (!ret)
            {
            acquireVidPid();
            }
        UVC_RETURN(ret);
        }

    void acquireVidPid()
        {
        if (!vidpidAcquired) // racing is ok
            {
            libusb_device_descriptor descriptor;
            int rc = libusb_get_device_descriptor(usb_dev, &descriptor);
            if (!rc)
                {
                vid = descriptor.idVendor;
                pid = descriptor.idProduct;
                UVC_DEBUG("acquired vid=%d & pid=%d", vid, pid);
                vidpidAcquired = true;
                }
            }
        }

    ~uvc_device() override
        {
        libusb_unref_device2(usb_dev, "uvc_device::~uvc_device");
        if (isValidFd(fdJava))
            {
            close(fdJava);
            }
        free(const_cast<LPSTR>(szUsbPath));
        }

    bool hasFeature(CamCompatFeature feature)
        {
        return CameraCompatManager::hasFeature(vid, pid, feature);
        }

    uvc_context *getContext()
        {
        return ctx;
        }
    };

//--------------------------------------------------------------------------------------------------
// uvc_device_info
//--------------------------------------------------------------------------------------------------

typedef struct uvc_device_info
    {
    /** Configuration descriptor for USB device */
    libusb_config_descriptor* config;
    /** VideoControl interface provided by device */
    uvc_control_interface ctrl_if;
    /** VideoStreaming interfaces on the device */
    uvc_streaming_interface* stream_ifs;    // Note: this is a LIST of streaming interfaces, not just one
    } uvc_device_info_t;


//--------------------------------------------------------------------------------------------------
// uvc_stream_handle
//--------------------------------------------------------------------------------------------------

/*
  set a high number of transfer buffers. This uses a lot of ram, but
  avoids problems with scheduling delays on slow boards causing missed
  transfers. A better approach may be to make the transfer thread FIFO
  scheduled (if we have root).
  We could/should change this to allow reduce it to, say, 5 by default
  and then allow the user to change the number of buffers as required.

  NOTE: previously was 100, changed to 10 to prevent kernel panic or
  failure to start streaming if run multiple times in a row.
  It is believed the issue is a bug in the kernel XHCI driver.

 */
#define LIBUVC_NUM_TRANSFER_BUFS 10

/*
 **** libusb_clear_halt: You should cancel all pending transfers before attempting to clear the halt condition. ****
 */
inline int clear_halt(libusb_transfer* pUsbTransfer)
    {
    UVC_ENTER("status=%d", pUsbTransfer->status);
    uvc_error rc = (uvc_error)libusb_clear_halt(pUsbTransfer->dev_handle, pUsbTransfer->endpoint);
    UVC_RETURN(rc);
    }

#define LIBUVC_DEFAULT_FRAME_SIZE   ( 16 * 1024 * 1024 )

struct uvc_stream_handle : ZeroOnNew
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    struct StreamTransfer;

    uvc_stream_handle *prev, *next;
    uvc_device_handle *devh;
    uvc_streaming_interface *stream_if;
    const libusb_interface_descriptor *pIsochronousAltSetting;
    bool             interfaceClaimed;
    bool             isRunning;         // if true, stream is running (streaming video to host)
    bool             deviceDisconnected;// true if we've noticed the device has vamoosed
    struct uvc_stream_ctrl cur_ctrl;    // current control block
    bool             fid;
    bool             frameTransitionSeen;
    // If you're going to take both the streaming and the user callback lock you MUST
    // take the streaming lock first.
    Lock             userCallbackLock;
    LockCond         userCallbackFrameAvailable;
    Lock             streamingLock;
    LockCond         streamingTransferProcessed;
    framenumber_t    frameNumberPolledMax;

    bool             userCallbackThreadStarted;
    PfnUserCallback  pfnUserCallback;
    void *           pvUserCallback;
    int              msUserCallbackThreadExitWait;
    ThreadInterlock* pUserCallbackThreadInterlock;

    int              cIsoPacketMax;
    unsigned         msTransfersTimeout;
    int              cTransfers;
    StreamTransfer*  rgTransfers;
    DoublyLinkable<StreamTransfer>::Head pendingTransfers;
    int              submissionIndexProcessedMax;
    Lock             submissionIndexLock;
    int              submissionIndexLast;   // index of last submitted transfer

    uvc_frame_format frameFormat;
    int              frameWidth;
    int              frameHeight;
    size_t           cbFrameExpected;
    uvc_frame *      pFrame;            // the frame into which data is being accumulated as it is received from the device
    uvc_frame *      pFrameUser;        // frame ready for user processing
    uvc_frame *      pFrameUserReturn;  // frame returned from user processing

    struct StreamTransfer : DoublyLinkable<StreamTransfer>
        {
        struct uvc_stream_handle* strmh;
        libusb_transfer*    pUsbTransfer;
        uint8_t*            pbData;
        size_t              cbData;
        int                 index;
        int                 submissionIndex;    // expect to be completed in submission order
        bool                currentlyActive;

        StreamTransfer()
            {
            strmh = NULL;
            pUsbTransfer = NULL;
            pbData = NULL;
            cbData = 0;
            index = 0;
            submissionIndex = 0;
            currentlyActive = false;
            }

        void initialize(uvc_stream_handle* strmh, int index)
            {
            this->strmh = strmh;
            this->index = index;
            }

        ~StreamTransfer()
            {
            free("dtor");
            }

        void alloc(int cIsoPacket, size_t cbTotal)
            {
            pUsbTransfer = libusb_alloc_transfer(cIsoPacket); failfastIfNull(pUsbTransfer);
            cbData = cbTotal;
            pbData = typedMalloc<uint8_t>(cbData);
            submissionIndex = 0;
            currentlyActive = false;
            if (NULL==pbData)
                {
                outOfMemory();
                }
            }

        void fillUsbTransfer(unsigned char type, uvc_format_desc *pFormatDesc, int cIsoPackets = 0);

        uvc_error submit()
            {
            uvc_error rc = UVC_SUCCESS;
            Assert(strmh->isRunning);

            // Careful to avoid holes on submission errors
            LOCK_SCOPE
                {
                ScopedLock scopedLock(strmh->submissionIndexLock);
                const int indexAttempted = submissionIndex = strmh->submissionIndexLast + 1;
                activate(); // must do first, as libusb_submit_transfer() might in theory accept and then wholly complete before we can look at things
                rc = (uvc_error)libusb_submit_transfer(pUsbTransfer);
                if (!rc)
                    {
                    strmh->submissionIndexLast = indexAttempted;
                    }
                else
                    {
                    deactivate();
                    }
                }
            return rc;
            }

        void activate() // is (the pUsbTransfer of) this StreamTransfer currently submitted to libusb_submit_transfer()?
            {
            Assert(!currentlyActive);
            currentlyActive = true;
            }

        void deactivate()
            {
            currentlyActive = false;
            }

        bool isActive()
            {
            return currentlyActive;
            }

        uvc_error cancel()
            {
            uvc_error rc = UVC_SUCCESS;
            if (pUsbTransfer)
                {
                /* The return code from libusb_cancel_transfer() usually DOESN'T MATTER. If we've
                 * successfully submitted() (and so are 'active'), we'll get a callback, plain
                 * and simple ('cancelled' or otherwise); otherwise, we won't. */
                rc = (uvc_error)libusb_cancel_transfer(pUsbTransfer);
                if (rc == UVC_SUCCESS || rc == UVC_ERROR_NOT_FOUND)
                    {
                    rc = UVC_SUCCESS;
                    }
                else
                    {
                    UVC_ERROR("libusb_cancel_transfer() failed: %d %s: active=%d", rc, libusb_error_name(rc), isActive());
                    if (rc==UVC_ERROR_NO_DEVICE) // maybe on other errors too??
                        {
                        // Assume that libusb is done with this. Deactivate so that free() will tidy up
                        deactivate();
                        }
                    }
                }
            return rc;
            }

        void free(LPCSTR szCaller)
            {
            if (isActive() && (pUsbTransfer || pbData))
                {
                UVC_ERROR("internal error: leaking StreamTransfer index=%d: dont risk corruption: pUsbTransfer=%p pbData=%p", index, pUsbTransfer, pbData);
                }
            else
                {
                if (pUsbTransfer)
                    {
                    // LOGV("freeing StreamTransfer %d %s", index, szCaller);
                    libusb_free_transfer(pUsbTransfer);
                    pUsbTransfer = NULL;
                    }
                if (pbData)
                    {
                    ::free(pbData);
                    pbData = NULL;
                    }
                }
            }
        };

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    uvc_stream_handle(uvc_device_handle* devh, uvc_streaming_interface* stream_if);

    bool ctorOK()
        {
        return pFrame != NULL && pFrame->ctorOK() && rgTransfers != NULL && pUserCallbackThreadInterlock != NULL;
        }

    ~uvc_stream_handle();

    void allocTransfers(int cTransfers)
        {
        this->cTransfers = cTransfers;
        submissionIndexProcessedMax = 0;
        submissionIndexLast = 0;
        rgTransfers = new StreamTransfer[cTransfers];
        if (rgTransfers)
            {
            for (int i = 0; i < cTransfers; i++)
                {
                rgTransfers[i].initialize(this, i);
                }
            }
        }

    void freeTransfers()
        {
        if (rgTransfers)
            {
            delete [] rgTransfers;
            rgTransfers = NULL;

            /* tidy */
            pendingTransfers.clear();
            submissionIndexProcessedMax = 0;
			submissionIndexLast = 0;
            }
        }


    //----------------------------------------------------------------------------------------------
    // Operations
    //----------------------------------------------------------------------------------------------

    uvc_context* getContext();
    uvc_error startIsochronousStreaming();
    uvc_error stopIsochronousStreaming();
    uvc_error startUserCallbackThread(PfnUserCallback pfnUserCallback, void* pvUserCallback);
    uvc_error submitTransfers();

    void setCbFrame()
        {
        this->cbFrameExpected = this->frameWidth * this->frameHeight * cbPerPixel(this->frameFormat);
        }

    uvc_frame* allocateFrame();
    uvc_error claimInterface();
    void releaseInterface();
    };

//--------------------------------------------------------------------------------------------------
// uvc_device_handle
//--------------------------------------------------------------------------------------------------

uvc_error uvc_get_device_info(uvc_device_t *dev, uvc_device_info_t **info);
void uvc_free_device_info(uvc_device_info_t *info);

/** Handle on an open UVC device
 */
struct uvc_device_handle : ZeroOnNew, RefCounted
    {
    struct uvc_device_handle *prev, *next;
    struct uvc_device *dev;             // owns a ref
    /** Underlying USB device handle */
    libusb_device_handle *usb_devh;     // we own; close on dtor
    struct uvc_device_info *info;
    struct libusb_transfer *status_xfer;
    Lock callbackLock;
    uint8_t status_buf[32];
    /** Function to call when we receive status updates from the camera */
    uvc_status_callback_t *status_cb;
    void *status_user_ptr;
    /** Function to call when we receive button events from the camera */
    uvc_button_callback_t *button_cb;
    void *button_user_ptr;
    uvc_stream_handle *openStreamsList;
    bool interfaceClaimed;
    int interfaceIndexClaimed;
    bool onOpenDevicesList;
    UsbInterfaceManager* pInterfaceManager;
    bool useAutoDetach;
    bool autoDetachSet;
    bool manuallyDetached;

    uvc_device_handle(uvc_device* dev, /*optional*/ UsbInterfaceManager* pInterfaceManager, libusb_device_handle* usb_devh)
        {
        UVC_ENTER();
        this->dev = dev;
        this->usb_devh = usb_devh;
        this->dev->addRef();
        if (pInterfaceManager)
            {
            this->pInterfaceManager = pInterfaceManager;
            this->pInterfaceManager->addRef();
            }
        // This seems to work on Motorola Marshmallows, but doesn't on a Control Hub. Go figure.
        // For now, we live with that and move on. TODO: get to the bottom of the problem.
        this->useAutoDetach = false; // libusb_has_capability(LIBUSB_CAP_SUPPORTS_DETACH_KERNEL_DRIVER);
        this->autoDetachSet = false;
        this->manuallyDetached = false;
        UVC_EXIT_VOID();
        }

    void stop()
        {
        UVC_ENTER();
        if (openStreamsList)
            {
            uvc_stop_streaming(this);
            if (openStreamsList)
                {
                UVC_ERROR("openStreamsList unexpectedly still non-null");
                }
            }
        UVC_EXIT_VOID();
        }

    bool hasFeature(CamCompatFeature feature)
        {
        return dev->hasFeature(feature);
        }

    uvc_error claimInterface(int idx)
        {
        UVC_ENTER("idx=%d",idx);

        if (useAutoDetach && !autoDetachSet)
            {
            libusb_set_auto_detach_kernel_driver(usb_devh, true);
            autoDetachSet = true;
            }

        uvc_error rc = pInterfaceManager
            ? pInterfaceManager->claimInterface(idx)
            : uvc_claim_if(this, idx); // idempotent?
        if (!rc)
            {
            UVC_DEBUG("claimInterface(%d) succeeded", idx);
            interfaceClaimed = true;
            interfaceIndexClaimed = idx;
            libusb_note_claimed_interface(usb_devh, idx); // make sure libusb knows what's claimed and what's not
            }
        UVC_RETURN(rc);
        }

    uvc_error releaseInterface(int idx)
        {
        UVC_ENTER();
        uvc_error rc = UVC_SUCCESS;
        if (interfaceClaimed)
            {
            if (interfaceIndexClaimed != idx)
                {
                LOGE("internal error: interface mismatch claimed=%d idx=%d: mismatch ignored", interfaceIndexClaimed, idx);
                }

            /* libusb_release_interface *should* reset the alternate setting to the first available,
               but sometimes (e.g. on Darwin ???) it doesn't. Thus, we do it explicitly here.
               This is needed to de-initialize certain cameras. */
            if (hasFeature(CamCompatFeature::ResetAltSettingOnRelease))
                {
                UVC_DEBUG("CamCompatFeature::ResetAltSettingOnRelease() activated");
                setInterfaceAltSetting(idx, 0);
                }

            rc = pInterfaceManager
                ? pInterfaceManager->releaseInterface(interfaceClaimed)
                : uvc_release_if(this, interfaceIndexClaimed);

            interfaceClaimed = false;
            libusb_note_released_interface(usb_devh, idx); // make sure libusb knows what's claimed and what's not
            }
        else
            LOGE("attempt to release unclaimed interface; ignored");
        UVC_RETURN(rc);
        }

    uvc_error setInterfaceAltSetting(uint8_t bInterfaceNumber, uint8_t bAlternateSetting)
        {
        UVC_ENTER("setInterfaceAltSetting(intf=%d alt=%d)", bInterfaceNumber, bAlternateSetting);
        uvc_error rc = pInterfaceManager->setInterfaceAltSetting(bInterfaceNumber, bAlternateSetting);
        if (rc < 0)
            {
            UVC_ERROR("libusb_set_interface_alt_setting() failed: rc=%d(%s)", rc, uvcErrorName(rc));
            }
        UVC_RETURN(rc);
        }

private:
    uvc_error uvc_claim_if(uvc_device_handle *devh, int idx);
    uvc_error uvc_release_if(uvc_device_handle *devh, int idx);

    void deconstruct() // undo the work done in uvc_open
        {
        UVC_ENTER();
        stop();

        if (interfaceClaimed)
            {
            this->releaseInterface(interfaceIndexClaimed);
            }

        if (autoDetachSet)
            {
            libusb_set_auto_detach_kernel_driver(usb_devh, false);
            autoDetachSet = false;
            }

        close();

        if (onOpenDevicesList)
            {
            DL_REMOVE(dev->ctx->openDevicesList, this);
            }
        UVC_EXIT_VOID();
        }

    void close()
        {
        UVC_ENTER();
        libusb_close(usb_devh);
        UVC_EXIT_VOID();
        }

    ~uvc_device_handle() override
        {
        UVC_ENTER();
        deconstruct();

        // by here, "streaming must be stopped"
        ::releaseRef(dev);
        if (info)
            {
            uvc_free_device_info(info);
            info = nullptr;
            }
        if (status_xfer)
            {
            libusb_free_transfer(status_xfer);
            status_xfer = nullptr;
            }

        ::releaseRef(pInterfaceManager);
        UVC_EXIT_VOID();
        }

public:
    uvc_stream_handle *getStreamByInterface(int index);

    uvc_streaming_interface *getStreamInterface(int index);

    uvc_error openStreamControl(uvc_stream_ctrl_t *ctrl, uvc_stream_handle **ppStreamHandleResult);

    uvc_context* getContext()
        {
        return dev->getContext();
        }
    };

//--------------------------------------------------------------------------------------------------
// uvc_query_stream_ctrl and status
//--------------------------------------------------------------------------------------------------

enum uvc_query_stream_ctrl_flavor
    {
    FLAVOR_PROBE,
    FLAVOR_COMMIT,
    };

static const int REQ_TYPE_SET = 0x21;
static const int REQ_TYPE_GET = 0xa1;

uvc_error_t uvc_query_stream_ctrl(
    uvc_device_handle_t *devh,
    uvc_stream_ctrl_t *ctrl,
    uvc_query_stream_ctrl_flavor flavor,
    enum uvc_req_code req);


// USB Device Class Definition for Video Devices, Table 2-2 Status Packet Format (VideoControl Interface as the Originator)

struct uvc_status_packet
    {
    uint8_t bStatusType; // 0
    uint8_t bOriginator; // 1

    int statusType()
        {
        return bStatusType & 0x0F;
        }
    };

struct uvc_control_status_packet : uvc_status_packet
    {
    uint8_t bEvent;     // 2
    uint8_t bSelector;  // 3
    uint8_t bAttribute; // 4
    uint8_t bValue;     // 5
    };

struct uvc_streaming_status_packet : uvc_status_packet
    {
    uint8_t bEvent;                         // 2
    union {
        uint8_t bValue;
        uint8_t rgbValue[ZERO_SIZED_ARRAY];     // 3 // actually an array sized according to bEvent
        };

    static const uint8_t bEventButtonPress = 0x00;
    };


#endif // !def(LIBUVC_INTERNAL_H)
/** @endcond */

