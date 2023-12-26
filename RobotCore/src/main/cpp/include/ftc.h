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
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
//
// ftc.h
//
// Common header we use in all the native code compile in these here parts of town.
//
// Notes:
//  http://mobilepearls.com/labs/native-android-api/
//  https://developer.android.com/ndk/guides/abis.html:
//      "Android follows the little-endian ARM GNU/Linux ABI."
//      "The type of wchar_t is unsigned int"
//      "Enumeration types have type int or unsigned int"

#ifndef __FTC_H__
#define __FTC_H__

#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include <libgen.h>
#include <pthread.h>
#include <android/log.h>
#include <android/bitmap.h>
#include <new>
#include <string>


//--------------------------------------------------------------------------------------------------
// Types and constants
//--------------------------------------------------------------------------------------------------

typedef int8_t byte_t;      // signed, to match java
typedef byte_t* pbyte_t;

typedef char * LPSTR;
typedef const char * LPCSTR;

#define FD_NONE (-1)
#define isValidFd(fd) ((fd)>=0)

typedef jlong JNI_NATIVE_POINTER;
#define JNI_NATIVE_POINTER_NULL ((JNI_NATIVE_POINTER)0)

#ifdef __cplusplus
    // Redefine so linkage works. We wish we didn't have to copy the value
    // down just so we can add 'extern "C"', but there seems to be no other way.
    #undef JNIEXPORT
    #define JNIEXPORT extern "C" __attribute__ ((visibility ("default")))
#endif

/** Global pointer to the VM. Initialized in JNI_OnLoad() */
extern JavaVM* g_pJavaVM;

#define HUNDRED_THOUSAND    100000
#define MILLION             1000000
#define TEN_MILLION         10000000
#define HUNDRED_MILLION     100000000
#define BILLION             1000000000

//--------------------------------------------------------------------------------------------------
// Logging and tracing
//--------------------------------------------------------------------------------------------------

// Note: the idea of having several 'levels' of tracing seemed appealing, but could use some polish
#define ENABLE_FTC_TRACING      1   // verbosity level (integer)

// Account for compiling on windows: remove path parts of file names (no effect on non-windows)
inline LPCSTR ftcBaseNameOfFile(LPCSTR file)
    {
    if (file != nullptr)
        {
        LPCSTR last = strrchr(file,'\\');
        if (last == NULL)
            {
            last = strrchr(file, '/');
            }
		return last==NULL ? file : last+1;
        }
    else
        return nullptr;
    }

#define _LOGV(fmt, args...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, fmt, ##args)
#define _LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,    TAG, fmt, ##args)
#define _LOGW(fmt, args...) __android_log_print(ANDROID_LOG_WARN,    TAG, fmt, ##args)
#define _LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG,   TAG, fmt, ##args)
#define _LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR,   TAG, fmt, ##args)

#define LOGV(fmt, args...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, "[%s:%d] " fmt, ftcBaseNameOfFile(__FILE__), __LINE__, ##args)
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,    TAG, "[%s:%d] " fmt, ftcBaseNameOfFile(__FILE__), __LINE__, ##args)
#define LOGW(fmt, args...) __android_log_print(ANDROID_LOG_WARN,    TAG, "[%s:%d] " fmt, ftcBaseNameOfFile(__FILE__), __LINE__, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG,   TAG, "[%s:%d] " fmt, ftcBaseNameOfFile(__FILE__), __LINE__, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR,   TAG, "[%s:%d] " fmt, ftcBaseNameOfFile(__FILE__), __LINE__, ##args)

#define TAG "Uvc" // #undef and change if you want for other files

#if defined(__cplusplus)

    #if ENABLE_FTC_TRACING > 0
        #define FTC_TRACE_ENABLED(enabled, ...) FunctionTracer _functionTracer(__FILE__, __LINE__, __METHOD_NAME__, 1, (enabled) && ENABLE_FTC_TRACING, ##__VA_ARGS__)
        #define FTC_TRACE(...)                  FunctionTracer _functionTracer(__FILE__, __LINE__, __METHOD_NAME__, 1, ENABLE_FTC_TRACING, ##__VA_ARGS__)
        #define FTC_TRACE_VERBOSE(...)          FunctionTracer _functionTracer(__FILE__, __LINE__, __METHOD_NAME__, 2, ENABLE_FTC_TRACING, ##__VA_ARGS__)
        #define FTC_TRACE_VERY_VERBOSE(...)     FunctionTracer _functionTracer(__FILE__, __LINE__, __METHOD_NAME__, 3, ENABLE_FTC_TRACING, ##__VA_ARGS__)

        #define UVC_DEBUG(format, ...)          LOGD(format, ##__VA_ARGS__)
        #define UVC_DEBUG_VERBOSE(format,...)   (ENABLE_FTC_TRACING >= 2 ? UVC_DEBUG(format, ##__VA_ARGS__): 0)
        #define UVC_ENTER(...)                  FTC_TRACE(__VA_ARGS__)
        #define UVC_ENTER_VERBOSE(...)          FTC_TRACE_VERBOSE(__VA_ARGS__)
        #define UVC_EXIT(code)                  (_functionTracer.setReturnCode(code), 0)
        #define UVC_EXIT_VOID()                 (0)

        #define uvc_originate_err(rc)           (_uvc_originate_err((rc), __FILE__, __LINE__))
    #else
        #define FTC_TRACE_ENABLED(enabled, ...) (0)
        #define FTC_TRACE(...)                  (0)
        #define FTC_TRACE_VERBOSE(...)          (0)
        #define FTC_TRACE_VERY_VERBOSE(...)     (0)

        #define UVC_DEBUG(format, ...)
        #define UVC_DEBUG_VERBOSE(format, ...)
        #define UVC_ENTER(...)
        #define UVC_ENTER_VERBOSE(...)
        #define UVC_EXIT(code)
        #define UVC_EXIT_VOID()

        #define uvc_originate_err(rc)           (rc)
    #endif

#else
    #define FTC_TRACE_ENABLED(enabled, ...) (0)
    #define FTC_TRACE(...)                  (0)
    #define FTC_TRACE_VERBOSE(...)          (0)
    #define FTC_TRACE_VERY_VERBOSE(...)     (0)
#endif

#define UVC_ERROR(format, ...) LOGE(format, ##__VA_ARGS__)
#define	UVC_RETURN(code) { uvc_error __value = (code); UVC_EXIT(__value); return (__value); }

#if defined(__cplusplus)

    /** This takes the fully elaborated function name with types, etc, that is provided by
     * __PRETTY_FUNCTION__ and extracts the ClassName::func part (if a method) or just the func
     * part (if not). Note that this isn't perfect, but it's good enough to help make function
     * tracing a litle nicer. */
    inline std::string_view ftcMethodName(LPCSTR szPrettyFunction)
        {
        const std::string_view prettyFunction(szPrettyFunction);
        size_t colons = prettyFunction.find("::");
        int openParen = prettyFunction.rfind("(");
        size_t begin;
        if (colons == std::string::npos)
            {
            begin = prettyFunction.substr(0,openParen).rfind(" ") + 1;
            }
        else
            {
            begin = prettyFunction.substr(0,colons).rfind(" ") + 1;
            }
        // TODO: if we seem to start with a *, then increment. Or, perhaps we need to look for
        // * in addition to space?
        size_t end = openParen - begin;

        return prettyFunction.substr(begin,end);
        }

    #define __METHOD_NAME__       ftcMethodName(__PRETTY_FUNCTION__)
    #define __LPSTR_METHOD_NAME__ std::string(__METHOD_NAME__).c_str()

#else

    // We're not in c++, so things are easier
    #define __LPSTR_METHOD_NAME__ __FUNCTION__

#endif

#if defined(__cplusplus)

struct FunctionTracer
    {
    LPCSTR file;
    int line;
    LPCSTR szFunction;
    std::string strFunction;
    int minLevelRequired;
    int currentLevel;
    int returnCode;
    bool returnCodeSet;

    FunctionTracer(LPCSTR file, int line, LPCSTR szFunction, int minLevelRequired, int currentLevel, LPCSTR format="", ...)
        {
        va_list args;
        va_start(args, format);
        char message[100];
        vsnprintf(&message[0], 100, format, args);

        this->file = ftcBaseNameOfFile(file);
        this->line = line;
        this->szFunction = strlen(szFunction)==0 ? "<fn>" : szFunction;
        this->minLevelRequired = minLevelRequired;
        this->currentLevel = currentLevel;
        this->returnCodeSet = false;
        log("", message, "...");

        va_end(args);
        }

    FunctionTracer(LPCSTR file, int line, const std::string_view& strvwFunction, int minLevelRequired, int currentLevel, LPCSTR format="", ...)
            : strFunction(strvwFunction)
        {
        va_list args;
        va_start(args, format);
        char message[100];
        vsnprintf(&message[0], 100, format, args);

        this->file = ftcBaseNameOfFile(file);
        this->line = line;
        this->szFunction = strFunction.c_str();
        this->minLevelRequired = minLevelRequired;
        this->currentLevel = currentLevel;
        this->returnCodeSet = false;
        log("", message, "...");

        va_end(args);
        }

    void setReturnCode(int rc)
        {
        this->returnCode = rc;
        this->returnCodeSet = true;
        }

    ~FunctionTracer()
        {
        if (returnCodeSet)
            {
            char after[32];
            snprintf(after, 32, " rc=%d", returnCode);
            log("...", "", after);
            }
        else
            log("...", "", "");
        }

    void log(LPCSTR before, LPCSTR params, LPCSTR after)
        {
        if (currentLevel >= minLevelRequired)
            {
            _LOGD("[%s:%d] %s%s(%s)%s", file, line, before, szFunction, params, after);
            }
        }
    };

#endif

//--------------------------------------------------------------------------------------------------
// Code paths
//--------------------------------------------------------------------------------------------------

#if defined(__GNUC__)
// the macro for branch prediction optimization for gcc(-O2/-O3 required)
#define		CONDITION(cond)				((__builtin_expect((cond)!=0, 0)))
#define		LIKELY(x)					((__builtin_expect(!!(x), 1)))	// x is likely true
#define		UNLIKELY(x)					((__builtin_expect(!!(x), 0)))	// x is likely false
#else
#define		CONDITION(cond)				((cond))
#define		LIKELY(x)					((x))
#define		UNLIKELY(x)					((x))
#endif

//--------------------------------------------------------------------------------------------------
// Memory
//--------------------------------------------------------------------------------------------------

template<typename T>
static __always_inline void zero(T* pt)
    {
    memset(pt, 0, sizeof(*pt));
    }
template<typename T>
static __always_inline void zero(T* pt, size_t cb)
    {
    memset(pt, 0, cb);
    }

template<typename T>
static __always_inline T* typedMalloc(size_t cbAlloc)
    {
    return (T*)malloc(cbAlloc);
    }
template<typename T>
static __always_inline T* typedMalloc()
    {
    return typedMalloc<T>(sizeof(T));
    }


template<typename T>
static __always_inline T* typedMallocZero(size_t cbAlloc)
    {
    T* pResult = typedMalloc<T>(cbAlloc);
    if (pResult)
        {
        zero(pResult, cbAlloc);
        }
    return pResult;
    }
template<typename T>
static __always_inline T* typedMallocZero()
    {
    return typedMallocZero<T>(sizeof(T));
    }


template<typename T>
static __always_inline T* typedRealloc(T* pExisting, size_t cbAlloc)
    {
    return (T*)realloc(pExisting, cbAlloc);
    }

template<typename T> // this variant not guaranteed to preserve any memcontents across reallocation
static __always_inline T* typedReallocNoPreserve(T *pExisting, size_t cbAlloc)
    {
    return (T*)realloc(pExisting, cbAlloc);
    }

struct ZeroOnNew
    {
    void* operator new(size_t cbAlloc)
        {
        void* pvData = ::operator new(cbAlloc);
        zero(pvData, cbAlloc);
        return pvData;
        }
    };

#define fatalError(msg)  (LOGE("[%s:%d]: %s: exiting app", ftcBaseNameOfFile(__FILE__), __LINE__, msg), exit(-1), 0)
#define outOfMemory()    (fatalError("out of memory"), (uvc_error)(-ENOMEM))
#define invalidArgs()    ((void)(LOGE("[%s:%d] invalid arguments", ftcBaseNameOfFile(__FILE__), __LINE__)))
#define uvcInvalidArgs() (LOGE("[%s:%d] invalid arguments", ftcBaseNameOfFile(__FILE__), __LINE__), (UVC_ERROR_INVALID_ARGS))

// An easy way to add (fatal) error checks where they were absent. Better would be to rework
// the control flow, but that can be more work.
#define failfastIfNull(value) ((value)==nullptr ? (LOGE("[%s:%d]: '%s' is null: failfast", ftcBaseNameOfFile(__FILE__), __LINE__, #value), exit(-2), 0) : 0)
#define failfastIfNotZero(value) ((value)!=0 ? (LOGE("[%s:%d]: '%s' is not zero: failfast", ftcBaseNameOfFile(__FILE__), __LINE__, #value), exit(-2), 0) : 0)

//--------------------------------------------------------------------------------------------------
// JNI
//--------------------------------------------------------------------------------------------------

// http://journals.ecs.soton.ac.uk/java/tutorial/native1.1/implementing/method.html
#define JNI_INTARRAY        "[I"
#define JNI_BOOLEAN         "Z"
#define JNI_INT             "I"
#define JNI_LONG            "J"
#define JNI_DOUBLE          "D"
#define JNI_FLOAT           "F"
#define JNI_POINTER         JNI_LONG
#define JNI_VOID            "V"
#define JNI_STRING          "Ljava/lang/String;"

jmethodID findMethod(JNIEnv *env, jobject object, LPCSTR methodName, LPCSTR args);

//--------------------------------------------------------------------------------------------------
// Time
//--------------------------------------------------------------------------------------------------

int ftc_gettime(clockid_t, timespec*);

static inline jlong System_nanoTime()
    {
    timespec now;
    ftc_gettime(CLOCK_MONOTONIC, &now);
    return now.tv_sec * (jlong)(BILLION) + now.tv_nsec;
    }

// timespec is in sysroot/usr/include/linux/time.h:
//  tv_sec is a __kernel_time_t, which is __kernel_long_t, which is a long, which is 64bits or 32bits depending on ABI
//  tv_nsec is a long
typedef __kernel_time_t TIMEVAL_TV_SEC_T;
typedef long TIMEVAL_TV_NSEC_T;

static inline void addns(timespec& ts, int64_t ns)
    {
    ts.tv_sec  += (TIMEVAL_TV_SEC_T)(ns / BILLION);
    ts.tv_nsec += (TIMEVAL_TV_NSEC_T)(ns % BILLION);

    ts.tv_sec += (TIMEVAL_TV_SEC_T)(ts.tv_nsec / BILLION);
    ts.tv_nsec = (TIMEVAL_TV_NSEC_T)(ts.tv_nsec % BILLION);
    }

static inline int64_t nsFromMs(int32_t ms)
    {
    return (int64_t)(ms) * MILLION;
    }

//--------------------------------------------------------------------------------------------------
// Assertions
//--------------------------------------------------------------------------------------------------

#include <assert.h>
#include <bits/timespec.h>

#ifdef NDEBUG
# define Assert(e) __assert_no_op
#else
# define Assert(e) ((e) ? __assert_no_op : logAssert(__FILE__, __LINE__, __PRETTY_FUNCTION__, #e))
#endif
static inline void logAssert(LPCSTR file, int line, LPCSTR function, LPCSTR assertion)
    {
    LOGE("[%s,%d:%s] assertion failed: %s", ftcBaseNameOfFile(file), line, function, assertion);
    }

//--------------------------------------------------------------------------------------------------
// Concurrency control
//--------------------------------------------------------------------------------------------------

#if defined(__cplusplus)

    #include <atomic> // we use STL now, so need it's version of atomics (had been stdatomic.h)

    class RefCounted
        {
        std::atomic_int refCount;
    public:
        RefCounted()
            {
            atomic_init(&refCount, 1);
            }
    protected:
        virtual ~RefCounted()
            {
            // subclass hook
            }
    public:
        void addRef()
            {
            int previous = atomic_fetch_add(&refCount, 1);
            }

        int releaseRef() // returns the count AFTER releasing
            {
            int previous = atomic_fetch_sub(&refCount, 1);
            if (previous == 1)
                {
                delete this;
                }
            return previous-1;
            }
        };

    template <typename T> // A simple helper that more robustly deals with nulls
    static inline int releaseRef(T*& pThem)
        {
        if (pThem)
            {
            int result = pThem->releaseRef();
            pThem = nullptr;
            return result;
            }
        else
            return -1; // will never be returned for a real object
        }

    struct Lock
        {
    protected:
        friend struct LockCond;
        friend struct ScopedLock;
        pthread_mutex_t mutex;
    public:
        Lock(bool recursive = false)
            {
            pthread_mutexattr_t attr;
            pthread_mutexattr_init(&attr);
            if (recursive)
                {
                pthread_mutexattr_settype(&attr, PTHREAD_MUTEX_RECURSIVE);
                }
            pthread_mutex_init(&mutex, &attr);
            }
        ~Lock()
            {
            pthread_mutex_destroy(&mutex);
            }

        void acquireLock()
            {
            pthread_mutex_lock(&mutex);
            }
        bool tryAcquireLock()
            {
            return pthread_mutex_trylock(&mutex) != 0;
            }
        void releaseLock()
            {
            pthread_mutex_unlock(&mutex);
            }
        };

    struct LockCond
        {
    protected:
        friend struct ScopedLock;
        pthread_cond_t  cond;
        Lock*           pLockController;
    public:
        LockCond(Lock& lockController)
            {
            pthread_cond_init(&cond, nullptr);
            this->pLockController = &lockController;
            }
        LockCond(Lock* pLockController)
            {
            pthread_cond_init(&cond, nullptr);
            this->pLockController = pLockController;
            }
        ~LockCond()
            {
            pthread_cond_destroy(&cond);
            }
        void broadcast()
            {
            pthread_cond_broadcast(&cond);
            }
        void wait(Lock& lock)
            {
            wait(&lock);
            }
        void wait(Lock* pLock)
            {
            Assert(pLock == pLockController);
            pthread_cond_wait(&cond, &pLock->mutex);
            }
        int wait(Lock *pLock, timespec *pts)
            {
            Assert(pLock == pLockController);
            return pthread_cond_timedwait(&cond, &pLock->mutex, pts);
            }
        int wait(Lock &lock, timespec &ts)
            {
            return wait(&lock, &ts);
            }
        };

    struct ScopedLock
        {
    protected:
        Lock* pLock;
        LPCSTR sz1;
        LPCSTR sz2;
        std::string str2;
        LPCSTR szFile;
        int line;

        // We log *before* the acquire
        void acquire()
            {
            if (sz1)
                {
                if (szFile)
                    {
                    _LOGV("[%s:%d] %s%s: enter...", szFile, line, sz1, sz2);
                    }
                else
                    {
                    _LOGV("%s%s: enter...", sz1, sz2);
                    }
                }
            if (pLock)
                {
                pLock->acquireLock();
                }
            }

        // We log *after* the release
        void release()
            {
            if (pLock)
                {
                pLock->releaseLock();
                }
            if (sz1)
                {
                if (szFile)
                    {
                    _LOGV("[%s:%d] %s%s: ...exit", szFile, line, sz1, sz2);
                    }
                else
                    {
                    _LOGV("%s%s: ...exit", sz1, sz2);
                    }
                }
            }

    public:

        ScopedLock(Lock* pLock, LPCSTR sz1=nullptr, LPCSTR sz2="", LPCSTR file=nullptr, int line=0)
            {
            this->pLock = pLock;
            this->sz1 = sz1;
            this->sz2 = sz2;
            this->szFile = ftcBaseNameOfFile(file);
            this->line = line;
            acquire();
            }
        ScopedLock(Lock* pLock, LPCSTR sz1, const std::string_view& s2View, LPCSTR file=nullptr, int line=0)
                : str2(s2View)
            {
            this->pLock = pLock;
            this->sz1 = sz1;
            this->sz2 = str2.c_str();
            this->szFile = ftcBaseNameOfFile(file);
            this->line = line;
            acquire();
            }
        ScopedLock(Lock& lock, LPCSTR sz1, const std::string_view& s2View, LPCSTR file=nullptr, int line=0)
                : ScopedLock(&lock, sz1, s2View, file, line)
            {
            }
        ScopedLock(Lock& lock, LPCSTR sz1=nullptr, LPCSTR sz2="", LPCSTR file=nullptr, int line=0)
                : ScopedLock(&lock, sz1, sz2, file, line)
            {
            }
        ~ScopedLock()
            {
            release();
            }

        void broadcast(LockCond& cond)
            {
            Assert(pLock);
            Assert(cond.pLockController == this->pLock);
            cond.broadcast();
            }
        void wait(LockCond& cond)
            {
            Assert(pLock);
            cond.wait(pLock);
            }
        int wait(LockCond& cond, timespec& ts)
            {
            Assert(pLock);
            return cond.wait(*pLock, ts);
            }
        int waitms(LockCond& cond, int32_t ms)
            {
            return waitns(cond, nsFromMs(ms));
            }
        int waitns(LockCond& cond, int64_t ns)
            {
            timespec ts;
            ftc_gettime(CLOCK_REALTIME, &ts);
            addns(ts, ns);
            return wait(cond, ts);
            }
        };

    struct LockAndCond
        {
    protected:
        Lock        lock;
        LockCond    cond;
    public:
        LockAndCond() : cond(lock)
            {
            }
        void acquireLock()
            {
            lock.acquireLock();
            }
        void releaseLock()
            {
            lock.releaseLock();
            }
        void broadcast()
            {
            cond.broadcast();
            }
        void wait()
            {
            cond.wait(lock);
            }
        int wait(timespec& ts)
            {
            return cond.wait(lock, ts);
            }
        int waitms(int32_t ms)
            {
            return waitns(nsFromMs(ms));
            }
        int waitns(int64_t ns)
            {
            timespec ts;
            ftc_gettime(CLOCK_REALTIME, &ts);
            addns(ts, ns);
            return wait(ts);
            }

        };

    /**
     * Returns a non-joinable (ie: detached) thread: joinable threads MUST be
     * joined or they leak resources (!).
     *
     * "A thread may either be joinable or detached.  If a thread is joinable, then another thread
     * can call pthread_join(3) to wait for the thread to terminate and fetch its exit status.
     * Only when a terminated joinable thread has been joined are the last of its resources released
     * back to the system. When a detached thread terminates, its resources are automatically
     * released back to the system: it is not possible to join with the thread in order to obtain
     * its exit status."
     */
    template<typename ARG> static inline int createDetachedThread(void (*pfn)(ARG *), ARG *arg)
        {
        int rc = 0;
        typedef void* (*PFN)(void*);
        pthread_attr_t attr;
        rc = pthread_attr_init(&attr);
        if (!rc)
            {
            pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_DETACHED);
            //
            pthread_t threadId;
            rc = pthread_create(&threadId, nullptr, (PFN)pfn, (void *) arg);
            //
            pthread_attr_destroy(&attr);
            }
        return rc;
        }

    /* A mechanism that allows us to synchronize with the termination of a thread
     * using a TIMED wait: pthread_join() isn't timed, and the variants thereof
     * that ARE timed are not implemented in Android. */
    class ThreadInterlock : public RefCounted
        {
        LockAndCond start;
        LockAndCond stop;
        bool started;
        bool stopped;

    public:
        ThreadInterlock()
            {
            started = false;
            stopped = false;
            }

        void waitForThreadStart()
            {
            start.acquireLock();
            while (!started)
                {
                start.wait();
                }
            start.releaseLock();
            }
        void signalThreadStart()
            {
            start.acquireLock();
            started = true;
            start.broadcast();
            start.releaseLock();
            }

        void waitForThreadCompletion(int32_t ms)
            {
            stop.acquireLock();
            if (!stopped)
                {
                stop.waitms(ms);
                }
            stop.releaseLock();
            }
        void signalThreadCompletion()
            {
            stop.acquireLock();
            stopped = true;
            stop.broadcast();
            stop.releaseLock();
            }
        };


    // A handy macro to help declare scopes for scopedLock
    #define LOCK_SCOPE if (true)

    /**
      * Threading in the UVC world is as follows:
      *
      *   (a) there's a user callback thread. See uvc_user_callback_main().
      *   (b) there's a streaming thread, on which LibUsb calls back regarding the status
      *       of transfers. See uvc_stream_callback_main().
      *   (c) there's whatever (at most) one thread we're calling into the Uvc API from Java
      *       or wherever at any given time.
      *
      * That last point is important: the API is not internally thread safe, so we have to serialize
      * our access. We use a global lock to do that.
      */
    #define NATIVE_API_ONE_CALLER_VERBOSE()   ScopedLock apiOneCaller(apiOneCallerLock)
    #define NATIVE_API_ONE_CALLER()         ScopedLock apiOneCaller(apiOneCallerLock, "API: ", __METHOD_NAME__)
    #define ASSERT_NOT_NATIVE_API()         // we'd check for that if we knew how!
    extern Lock apiOneCallerLock;

    // This alternative documents from-Java calls we think we can execute concurrently
    #define NATIVE_API_MANY_CALLERS_VERBOSE()  (0)

#endif // C++

//--------------------------------------------------------------------------------------------------
// Math
//--------------------------------------------------------------------------------------------------

template<typename T>
static __always_inline T max(T t1, T t2)
    {
    return t1 > t2 ? t1 : t2;
    }
template<typename T>
static __always_inline T min(T t1, T t2)
    {
    return t1 < t2 ? t1 : t2;
    }

//--------------------------------------------------------------------------------------------------
// Temporary files
//--------------------------------------------------------------------------------------------------

#ifdef __cplusplus

struct TempFile
    {
    LPCSTR  szTempFolder;  // caller must keep alloc'd
    FILE*   pFile;
    char    szPath[64];

    TempFile(LPCSTR szTempFolder)
        {
        this->szTempFolder = szTempFolder;
        }

    bool create();
    void close();

    jstring getJavaString(JNIEnv *env);
    };

#endif

//--------------------------------------------------------------------------------------------------
// Linked Lists
//--------------------------------------------------------------------------------------------------

#ifdef __cplusplus

    template <typename Container, int dibOffset>
    struct ListEntry
        {
    private:
        ListEntry *pPrev, *pNext;

    public:
        ListEntry()
            {
            pPrev = pNext = this;
            }

        Container* prev()
            {
            return prevEntry()->getContainer();
            }
        Container* next()
            {
            return nextEntry()->getContainer();
            }
        ListEntry* prevEntry()
            {
            return pPrev;
            }
        ListEntry* nextEntry()
            {
            return pNext;
            }

        Container* getContainer()
            {
            return reinterpret_cast<Container*>(reinterpret_cast<pbyte_t>(this) - dibOffset);
            }
        static ListEntry* getListEntry(Container *pContainer)
            {
            return reinterpret_cast<ListEntry*>(reinterpret_cast<pbyte_t>(pContainer) + dibOffset);
            }

        void insertAfter(Container* pThem)
            {
            insertAfterEntry(getListEntry(pThem));
            }
        void insertBefore(Container* pThem)
            {
            insertBeforeEntry(getListEntry(pThem));
            }

        void remove()
            {
            ListEntry* pPrevPrev = this->pPrev;
            ListEntry* pPrevNext = this->pNext;

            pPrevPrev->pNext = pPrevNext;
            pPrevNext->pPrev = pPrevPrev;

            this->pPrev = this->pNext = this;
            }

        /* Note: the head of the list ISN'T an actual field in Container at the indicated
         * offset. Rather, it (usually) lives in some other structure.*/
        struct Head
            {
        private:
            ListEntry entry;

        public:
            Head()
                {
                }

            void append(Container* pNew)
                {
                getListEntry(pNew)->insertAfterEntry(entry.pPrev);
                }

            void prepend(Container *pNew)
                {
                getListEntry(pNew)->insertBeforeEntry(entry.pNext);
                }

            bool isEmpty()
                {
                return entry.pNext == &entry;
                }

            void clear()
                {
                entry.pPrev = entry.pNext = &entry;
                }

            /* You MUST NOT use this pointer if it's equal to stop(). */
            Container* first()
                {
                return entry.pNext->getContainer();
                }

            /* You MUST NOT use this pointer if it's equal to stop(). */
            Container* last()
                {
                return entry.pPrev->getContainer();
                }

            /* NOT A VALID Container*. Just a sentinel. */
            Container* stop()
                {
                return entry.getContainer();
                }
            };

    private:

        void insertAfterEntry(ListEntry *pBefore)
            {
            this->pNext = pBefore->pNext;
            this->pPrev = pBefore;
            pBefore->pNext = this;
            this->pNext->pPrev = this;
            }

        void insertBeforeEntry(ListEntry *pAfter)
            {
            this->pNext = pAfter;
            this->pPrev = pAfter->pPrev;
            pAfter->pPrev = this;
            this->pPrev->pNext = this;
            }
        };


    template <typename Container /*extends DoublyLinkable*/>
    struct DoublyLinkable : ListEntry<Container, 0>
        {
        };

#endif // C++

//--------------------------------------------------------------------------------------------------
// Structure offsets
//--------------------------------------------------------------------------------------------------

#ifdef __cplusplus

    #define offsetmax(type, member) (offsetof(type, member) + sizeof(type::member))

    template<typename T>
    static inline T* readPointerField(void* pStruct, int ib)
        {
        return * (T**) ((byte_t*)pStruct + ib);
        }

    template<typename T>
    static inline T* readPointerField(jlong pStruct, int ib)
        {
        return * (T**) ((byte_t*)pStruct + ib);
        }

    template<typename T>
    static inline void writePointerField(void* pStruct, int ib, T* pT)
        {
        T** ppt = reinterpret_cast<T**>((byte_t*)pStruct + ib);
        *ppt = pT;
        }

    template<typename T>
    static inline void writePointerField(jlong pStruct, int ib, T* pT)
        {
        T** ppt = reinterpret_cast<T**>((byte_t*)pStruct + ib);
        *ppt = pT;
        }

#endif

#define OFFSET_MAP_START(cAdd, type_t)                                                      \
                                                                                            \
    jintArray result = nullptr;                                                             \
                                                                                            \
    const int cFieldAllocated = 1 + (cAdd);                                                 \
    if (cFieldAllocated == cFieldExpected)                                                  \
        {                                                                                   \
        result = env->NewIntArray(cFieldAllocated);                                         \
        if (result)                                                                         \
            {                                                                               \
            jboolean isCopy; jint *pArray = env->GetIntArrayElements(result, &isCopy);      \
            jint *pT = pArray;                                                              \
                                                                                            \
            (*pT++)=sizeof(type_t);                                                         \
            int cFieldAdded = 1;                                                            \

#define OFFSET_MAP_END()                                                                    \
            env->ReleaseIntArrayElements(result, pArray, 0);                                \
            if (cFieldAllocated != cFieldAdded) LOGE("internal error: allocated=%d added=%d", cFieldAllocated, cFieldAdded);    \
            }                                                                               \
        else                                                                                \
            outOfMemory();                                                                  \
        }                                                                                   \
    else                                                                                    \
        invalidArgs();                                                                      \
                                                                                            \
    return result;

//--------------------------------------------------------------------------------------------------
// Graphics
//--------------------------------------------------------------------------------------------------

// Android is little endian
typedef uint32_t ColorInt;

#ifdef __cplusplus

    // https://developer.android.com/ndk/reference/group___bitmap.html
    struct JavaBitmapAccess
        {
        JNIEnv*             env;
        jobject             bitmap;
        byte_t*             pbData;
        AndroidBitmapInfo   info;

        JavaBitmapAccess(JNIEnv* env, jobject bitmap)
            {
            this->env = env;
            this->bitmap = bitmap;
            int rc = AndroidBitmap_lockPixels(env, bitmap, (void**)&pbData);
            if (!rc)
                {
                rc = AndroidBitmap_getInfo(env, bitmap, &info);
                }
            failfastIfNotZero(rc);
            }
        virtual ~JavaBitmapAccess()
            {
            AndroidBitmap_unlockPixels(env, bitmap);
            }

        AndroidBitmapFormat getFormat()
            {
            return (AndroidBitmapFormat)info.format;
            }

        uint32_t getWidth()
            {
            return info.width;
            }
        uint32_t getHeight()
            {
            return info.height;
            }
        };

    struct Color
        {
        //----------------------------------------------------------------------------------------------
        // Mirrors of the Java API
        //----------------------------------------------------------------------------------------------

        __always_inline static uint8_t red(uint32_t color)
            {
            return uint8_t(color & 0xFF);
            }
        __always_inline static uint8_t green(uint32_t color)
            {
            return uint8_t((color >> 8) & 0xFF);
            }
        __always_inline static uint8_t blue(uint32_t color)
            {
            return uint8_t((color >> 16) & 0xFF);
            }
        __always_inline static uint8_t alpha(uint32_t color)
            {
            return uint8_t((color >> 24) & 0xFF);
            }

        //----------------------------------------------------------------------------------------------
        // Byte-order-specific conversions
        //----------------------------------------------------------------------------------------------

        /* Returns a value that when stored in memory has RGBA in increasing memory order */
        __always_inline static ColorInt toRGBA(ColorInt color)
            {
            return color;
            }

        // We could define other ordering conversions but as yet have had no need

        };

#endif // C++

#endif // __FTC_H__
