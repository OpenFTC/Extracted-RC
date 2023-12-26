# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
        RobotCore

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/system/SerialPort.cpp
        src/main/cpp/system/time.cpp
        src/main/cpp/libuvc/src/ctrl.cpp
        src/main/cpp/libuvc/src/ctrl-gen.cpp
        src/main/cpp/libuvc/src/device.cpp
        src/main/cpp/libuvc/src/devicehandle.cpp
        src/main/cpp/libuvc/src/diag.cpp
        src/main/cpp/libuvc/src/frame.cpp
        src/main/cpp/libuvc/src/init.cpp
        src/main/cpp/libuvc/src/stream.cpp
        src/main/cpp/libuvc/src/streamhandle.cpp
        src/main/cpp/libuvc/src/misc.cpp
        src/main/cpp/libuvc/src/jni/jni.cpp
        src/main/cpp/libuvc/src/jni/jni_bmpwriter.cpp
        src/main/cpp/libuvc/src/jni/jni_libusb_device.cpp
        src/main/cpp/libuvc/src/jni/jni_nativeobject.cpp
        src/main/cpp/libuvc/src/jni/jni_context.cpp
        src/main/cpp/libuvc/src/jni/jni_device.cpp
        src/main/cpp/libuvc/src/jni/jni_devicehandle.cpp
        src/main/cpp/libuvc/src/jni/jni_devicedescriptor.cpp
        src/main/cpp/libuvc/src/jni/jni_deviceinfo.cpp
        src/main/cpp/libuvc/src/jni/jni_formatdesc.cpp
        src/main/cpp/libuvc/src/jni/jni_framedesc.cpp
        src/main/cpp/libuvc/src/jni/jni_streamcontrol.cpp
        src/main/cpp/libuvc/src/jni/jni_streamhandle.cpp
        src/main/cpp/libuvc/src/jni/jni_streaminginterface.cpp
        src/main/cpp/libuvc/src/jni/jni_frame.cpp
        src/main/cpp/libusb/core.c
        src/main/cpp/libusb/descriptor.c
        src/main/cpp/libusb/hotplug.c
        src/main/cpp/libusb/io.c
        src/main/cpp/libusb/sync.c
        src/main/cpp/libusb/strerror.c
        src/main/cpp/libusb/os/linux_usbfs.c
        src/main/cpp/libusb/os/poll_posix.c
        src/main/cpp/libusb/os/threads_posix.c
        src/main/cpp/libusb/os/linux_netlink.c)

# Specifies a path to native header files.
include_directories(
        src/main/cpp/libusb
        src/main/cpp/libusb/android
        src/main/cpp/libuvc/include
        src/main/cpp/include)

# linking libs
target_link_libraries(
        RobotCore
        log
        jnigraphics)