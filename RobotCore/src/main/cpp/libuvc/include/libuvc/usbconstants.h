/*
Copyright (c) 2018 Robert Atkinson

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
SERVICES; LOSS OF USE, DATA; OR PROFITS, OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

#ifndef FTC_APP_USBCONSTANTS_H
#define FTC_APP_USBCONSTANTS_H

/** Modelled after the Java class of the same name */
enum UsbConstants
    {
    /**
     * Bitmask used for extracting the {@link UsbEndpoint} direction from its address field.
     * @see UsbEndpoint#getAddress
     * @see UsbEndpoint#getDirection
     * @see #USB_DIR_OUT
     * @see #USB_DIR_IN
     *
     */
    USB_ENDPOINT_DIR_MASK = 0x80,
    /**
     * Used to signify direction of data for a {@link UsbEndpoint} is OUT (host to device)
     * @see UsbEndpoint#getDirection
     */
    USB_DIR_OUT = 0,
    /**
     * Used to signify direction of data for a {@link UsbEndpoint} is IN (device to host)
     * @see UsbEndpoint#getDirection
     */
    USB_DIR_IN = 0x80,

    /**
     * Bitmask used for extracting the {@link UsbEndpoint} number its address field.
     * @see UsbEndpoint#getAddress
     * @see UsbEndpoint#getEndpointNumber
     */
    USB_ENDPOINT_NUMBER_MASK = 0x0f,

    /**
     * Bitmask used for extracting the {@link UsbEndpoint} type from its address field.
     * @see UsbEndpoint#getAddress
     * @see UsbEndpoint#getType
     * @see #USB_ENDPOINT_XFER_CONTROL
     * @see #USB_ENDPOINT_XFER_ISOC
     * @see #USB_ENDPOINT_XFER_BULK
     * @see #USB_ENDPOINT_XFER_INT
     */
    USB_ENDPOINT_XFERTYPE_MASK = 0x03,
    /**
     * Control endpoint type (endpoint zero)
     * @see UsbEndpoint#getType
     */
    USB_ENDPOINT_XFER_CONTROL = 0,
    /**
     * Isochronous endpoint type (currently not supported)
     * @see UsbEndpoint#getType
     */
    USB_ENDPOINT_XFER_ISOC = 1,
    /**
     * Bulk endpoint type
     * @see UsbEndpoint#getType
     */
    USB_ENDPOINT_XFER_BULK = 2,
    /**
     * Interrupt endpoint type
     * @see UsbEndpoint#getType
     */
    USB_ENDPOINT_XFER_INT = 3,


    /**
     * Bitmask used for encoding the request type for a control request on endpoint zero.
     */
    USB_TYPE_MASK = (0x03 << 5),
    /**
     * Used to specify that an endpoint zero control request is a standard request.
     */
    USB_TYPE_STANDARD = (0x00 << 5),
    /**
     * Used to specify that an endpoint zero control request is a class specific request.
     */
    USB_TYPE_CLASS = (0x01 << 5),
    /**
     * Used to specify that an endpoint zero control request is a vendor specific request.
     */
    USB_TYPE_VENDOR = (0x02 << 5),
    /**
     * Reserved endpoint zero control request type (currently unused).
     */
    USB_TYPE_RESERVED = (0x03 << 5),


    /**
     * USB class indicating that the class is determined on a per-interface basis.
     */
    USB_CLASS_PER_INTERFACE = 0,
    /**
     * USB class for audio devices.
     */
    USB_CLASS_AUDIO = 1,
    /**
     * USB class for communication devices.
     */
    USB_CLASS_COMM = 2,
    /**
     * USB class for human interface devices (for example, mice and keyboards).
     */
    USB_CLASS_HID = 3,
    /**
     * USB class for physical devices.
     */
    USB_CLASS_PHYSICA = 5,
    /**
     * USB class for still image devices (digital cameras).
     */
    USB_CLASS_STILL_IMAGE = 6,
    /**
     * USB class for printers.
     */
    USB_CLASS_PRINTER = 7,
    /**
     * USB class for mass storage devices.
     */
    USB_CLASS_MASS_STORAGE = 8,
    /**
     * USB class for USB hubs.
     */
    USB_CLASS_HUB = 9,
    /**
     * USB class for CDC devices (communications device class).
     */
    USB_CLASS_CDC_DATA = 0x0a,
    /**
     * USB class for content smart card devices.
     */
    USB_CLASS_CSCID = 0x0b,
    /**
     * USB class for content security devices.
     */
    USB_CLASS_CONTENT_SEC = 0x0d,
    /**
     * USB class for video devices.
     * Constants as specified in "USB Device Class Definition for Video Devices" standard.
     */
    USB_CLASS_VIDEO = 0x0e,

    USB_VIDEO_INTERFACE_SUBCLASS_UNDEFINED = 0,
    USB_VIDEO_INTERFACE_SUBCLASS_CONTROL = 1,
    USB_VIDEO_INTERFACE_SUBCLASS_STREAMING = 2,
    USB_VIDEO_INTERFACE_SUBCLASS_INTERFACE_COLLECTION = 3,

    USB_VIDEO_INTERFACE_PROTOCOL_UNDEFINED = 0,
    USB_VIDEO_INTERFACE_PROTOCOL_15 = 1,

    USB_VIDEO_CLASS_DESCRIPTOR_UNDEFINED = 0x20,
    USB_VIDEO_CLASS_DESCRIPTOR_DEVICE = 0x21,
    USB_VIDEO_CLASS_DESCRIPTOR_CONFIGURATION = 0x22,
    USB_VIDEO_CLASS_DESCRIPTOR_STRING = 0x23,
    USB_VIDEO_CLASS_DESCRIPTOR_INTERFACE = 0x24,
    USB_VIDEO_CLASS_DESCRIPTOR_ENDPOINT = 0x25,

    /**
     * USB class for wireless controller devices.
     */
    USB_CLASS_WIRELESS_CONTROLLER = 0xe0,
    /**
     * USB class for wireless miscellaneous devices.
     */
    USB_CLASS_MISC = 0xef,
    /**
     * Application specific USB class.
     */
    USB_CLASS_APP_SPEC = 0xfe,
    /**
     * Vendor specific USB class.
     */
    USB_CLASS_VENDOR_SPEC = 0xff,

    /**
     * Boot subclass for HID devices.
     */
    USB_INTERFACE_SUBCLASS_BOOT = 1,
    /**
     * Vendor specific USB subclass.
     */
    USB_SUBCLASS_VENDOR_SPEC = 0xff,

    //----------------------------------------------------------------------------------------------

    VENDOR_ID_ARDUCAM = 0xC45,
    VENDOR_ID_AUSDOM = 3034,
    VENDOR_ID_MICROSOFT = 0x045E,
    VENDOR_ID_LOGITECH = 0x046D,
    VENDOR_ID_FTDI = 0x0403,

    // https://android.googlesource.com/platform/system/core/+/android-4.4_r1/adb/usb_vendors.c
    // http://www.linux-usb.org/usb.ids
    VENDOR_ID_GOOGLE = 0x18d1,
    VENDOR_ID_INTEL = 0x8087,
    VENDOR_ID_HTC = 0x0bb4,
    VENDOR_ID_SAMSUNG = 0x04e8,
    VENDOR_ID_MOTOROLA = 0x22b8,
    VENDOR_ID_LGE = 0x1004,
    VENDOR_ID_HUAWEI = 0x12D1,
    VENDOR_ID_ACER = 0x0502,
    VENDOR_ID_SONY_ERICSSON = 0x0FCE,
    VENDOR_ID_FOXCONN = 0x0489,
    VENDOR_ID_DELL = 0x413c,
    VENDOR_ID_NVIDIA = 0x0955,
    VENDOR_ID_GARMIN_ASUS = 0x091E,
    VENDOR_ID_SHARP = 0x04dd,
    VENDOR_ID_ZTE = 0x19D2,
    VENDOR_ID_KYOCERA = 0x0482,
    VENDOR_ID_PANTECH = 0x10A9,
    VENDOR_ID_QUALCOMM = 0x05c6,
    VENDOR_ID_OTGV = 0x2257,
    VENDOR_ID_NEC = 0x0409,
    VENDOR_ID_PMC = 0x04DA,
    VENDOR_ID_TOSHIBA = 0x0930,
    VENDOR_ID_SK_TELESYS = 0x1F53,
    VENDOR_ID_KT_TECH = 0x2116,
    VENDOR_ID_ASUS = 0x0b05,
    VENDOR_ID_PHILIPS = 0x0471,
    VENDOR_ID_TI = 0x0451,
    VENDOR_ID_FUNAI = 0x0F1C,
    VENDOR_ID_GIGABYTE = 0x0414,
    VENDOR_ID_IRIVER = 0x2420,
    VENDOR_ID_COMPAL = 0x1219,
    VENDOR_ID_T_AND_A = 0x1BBB,
    VENDOR_ID_LENOVOMOBILE = 0x2006,
    VENDOR_ID_LENOVO = 0x17EF,
    VENDOR_ID_VIZIO = 0xE040,
    VENDOR_ID_K_TOUCH = 0x24E3,
    VENDOR_ID_PEGATRON = 0x1D4D,
    VENDOR_ID_ARCHOS = 0x0E79,
    VENDOR_ID_POSITIVO = 0x1662,
    VENDOR_ID_FUJITSU = 0x04C5,
    VENDOR_ID_LUMIGON = 0x25E3,
    VENDOR_ID_QUANTA = 0x0408,
    VENDOR_ID_INQ_MOBILE = 0x2314,
    VENDOR_ID_SONY = 0x054C,
    VENDOR_ID_LAB126 = 0x1949,
    VENDOR_ID_YULONG_COOLPAD = 0x1EBF,
    VENDOR_ID_KOBO = 0x2237,
    VENDOR_ID_TELEEPOCH = 0x2340,
    VENDOR_ID_ANYDATA = 0x16D5,
    VENDOR_ID_HARRIS = 0x19A5,
    VENDOR_ID_OPPO = 0x22D9,
    VENDOR_ID_XIAOMI = 0x2717,
    VENDOR_ID_BYD = 0x19D1,
    VENDOR_ID_OUYA = 0x2836,
    VENDOR_ID_HAIER = 0x201E,
    VENDOR_ID_HISENSE = 0x109b,
    VENDOR_ID_MTK = 0x0e8d,
    VENDOR_ID_NOOK = 0x2080,
    VENDOR_ID_QISDA = 0x1D45,
    VENDOR_ID_ECS = 0x03fc,
    VENDOR_ID_APPOTECH = 0x1908,

    // Collated from our own observations
    PRODUCT_ID_LOGITECH_C920 = 0x082D,
    PRODUCT_ID_LOGITECH_C310 = 0x081B,
    PRODUCT_ID_LOGITECH_C270 = 0x0825,

    PRODUCT_ID_MICROSOFT_LIFECAM_HD_3000 = 2064,
    PRODUCT_ID_MICROSOFT_LIFECAM_HD_5000 = 1901,
    PRODUCT_ID_MICROSOFT_LIFECAM_STUDIO = 1906,

    PRODUCT_ID_AUSDOM_AW615 = 22704,

    PRODUCT_ID_ARDUCAM_OV9281 = 0x6366,
};

#endif //FTC_APP_USBCONSTANTS_H
