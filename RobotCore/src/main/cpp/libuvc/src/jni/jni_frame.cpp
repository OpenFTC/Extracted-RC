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
// UvcFrame methods
//
#include <jni.h>
#include <unistd.h>
#include <errno.h>
#include <libuvc.h>
#include <libuvc/libuvc_internal.h>
#include <ftc.h>

#undef TAG
static LPCSTR TAG = "UvcFrame";

JNIEXPORT jintArray JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrame_nativeGetFieldOffsets(JNIEnv *env, jclass type, jint cFieldExpected)
    {
    OFFSET_MAP_START(12, uvc_frame)

    #define addField(field)  (cFieldAdded++, (*pT++) = offsetof(uvc_frame, field))
    addField(pbData);
    addField(cbData);
    addField(cbAllocated);
    addField(width);
    addField(height);
    addField(frameFormat);
    addField(cbLineStride);
    addField(frameNumber);
    addField(pts);
    addField(captureTime);
    addField(sourceClockReference);
    addField(pContext);
    #undef addField
    OFFSET_MAP_END()
    }

JNIEXPORT jobject JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrame_nativeGetImageByteBuffer(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    jobject result = NULL;
    uvc_frame* pFrame = (uvc_frame*) pointer;
    if (pFrame)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        result = env->NewDirectByteBuffer(pFrame->pbData, pFrame->cbData);
        if (result)
            {
            // All is well
            }
        else
            outOfMemory();
        }
    else
        invalidArgs();
    return result;
    }

JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrame_nativeCopyImageData(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer, jbyteArray byteArray, jint cbByteArray)
    {
    FTC_TRACE_VERBOSE();
    uvc_frame* pFrame = (uvc_frame*) pointer;
    if (pFrame && byteArray)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        if (cbByteArray == pFrame->cbData)
            {
            jboolean isCopy;
            jbyte* rgb = env->GetByteArrayElements(byteArray, &isCopy);
            if (rgb)
                {
                int cbCopy = cbByteArray < pFrame->cbData ? cbByteArray : pFrame->cbData;
                memcpy(rgb, pFrame->pbData, cbCopy);
                env->ReleaseByteArrayElements(byteArray, rgb, 0);
                }
            else
                LOGE("failed to get image data");
            }
        else
            {
            invalidArgs();
            }
        }
    else
        invalidArgs();
    }

JNIEXPORT JNI_NATIVE_POINTER JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrame_nativeCopyFrame(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    JNI_NATIVE_POINTER result = JNI_NATIVE_POINTER_NULL;
    uvc_frame* pFrame = (uvc_frame*) pointer;
    if (pFrame)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_frame_t* pNewFrame = uvc_allocate_frame(pFrame->pContext, 0, 0);
        if (pNewFrame)
            {
            uvc_error_t rc = uvc_duplicate_frame(pFrame, pNewFrame);
            if (!rc)
                {
                result = (JNI_NATIVE_POINTER)pNewFrame;
                }
            else
                uvc_free_frame(pNewFrame);
            }
        else
            outOfMemory();
        }
    else
        invalidArgs();
    return result;
    }

void rgb2rgba(const uint8_t *rgb, uint32_t width, uint32_t height, uint8_t *rgba)
    {
    for(uint32_t irgb = 0, irgba = 0; irgb < width*height*3; irgb += 3, irgba += 4)
        {
        rgba[irgba]   = rgb[irgb];
        rgba[irgba+1] = rgb[irgb+1];
        rgba[irgba+2] = rgb[irgb+2];
        rgba[irgba+3] = 255;
        }
    }

JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrame_nativeCopyFrameToBmp(JNIEnv *env, jclass type, long jPtrYuvFrame, jobject bmp)
    {
    FTC_TRACE_VERBOSE();
    NATIVE_API_ONE_CALLER_VERBOSE();

    uvc_frame* yuyvFrame = (uvc_frame*) jPtrYuvFrame;

    AndroidBitmapInfo bmpInfo;
    AndroidBitmap_getInfo(env, bmp, &bmpInfo);

    if(bmpInfo.width != yuyvFrame->width || bmpInfo.height != yuyvFrame->height || bmpInfo.format != ANDROID_BITMAP_FORMAT_RGBA_8888)
        {
        return;
        }

    void* pixels = NULL;
    AndroidBitmap_lockPixels(env, bmp, &pixels);

    uvc_frame* rgbFrame = uvc_allocate_frame(yuyvFrame->pContext, 0,0);
    uvc_yuyv2rgb(yuyvFrame, rgbFrame);
    rgb2rgba(rgbFrame->pbData, yuyvFrame->width, yuyvFrame->height, (uint8_t*)pixels);
    uvc_free_frame(rgbFrame);

    AndroidBitmap_unlockPixels(env, bmp);
    }

JNIEXPORT void JNICALL
Java_org_firstinspires_ftc_robotcore_internal_camera_libuvc_nativeobject_UvcFrame_nativeFreeFrame(JNIEnv *env, jclass type, JNI_NATIVE_POINTER pointer)
    {
    FTC_TRACE_VERBOSE();
    uvc_frame* pFrame = (uvc_frame*) pointer;
    if (pFrame)
        {
        NATIVE_API_ONE_CALLER_VERBOSE();
        uvc_free_frame(pFrame);
        }
    else
        invalidArgs();
    }

