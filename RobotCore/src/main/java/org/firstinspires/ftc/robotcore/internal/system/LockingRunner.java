/*
Copyright (c) 2019 Noah Andrews

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Noah Andrews nor the names of his contributors may be used to
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
package org.firstinspires.ftc.robotcore.internal.system;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.ThrowingCallable;
import org.firstinspires.ftc.robotcore.internal.files.FileBasedLock;

import java.lang.ref.WeakReference;
import java.util.concurrent.Semaphore;

/**
 * {@link LockingRunner} provides non-recursive exclusive-lock semantics across threads, with an
 * API that matches {@link FileBasedLock} as closely as possible. The "non-recursive" (aka non-reentrant)
 * part is important to note, as it is a significant difference from how JVM locking usually works.
 * This class uses non-reentrant locking so that if {@link FileBasedLock} is ever fixed, it can be
 * used in place of this class with reduced worry of breakage.
 *
 * It's also important to note that while it's safe to create a new instance of {@link FileBasedLock}
 * every time you need one, the same is not true for {@link LockingRunner}.
 */
public final class LockingRunner {
    // package-private
    static final String ATTEMPTED_REENTRANT_USAGE_ERROR_MESSAGE = "The thread currently holding the lock tried to obtain the lock. This is invalid behavior, as LockingRunner does not (currently) support re-entrant locking, to preserve full compatibility with file-based locking.";
    private static final int MAX_CONCURRENT_EXECUTIONS = 1;
    private static class NeverThrown extends Exception {}

    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_EXECUTIONS, true);
    private WeakReference<Thread> lockingThreadReference = null;

    public void lockWhile(final Runnable runnable) throws InterruptedException {
        lockWhile(new Supplier<Void>() {
            @Override public Void get() {
                runnable.run();
                return null;
            }
        });
    }

    public <T> T lockWhile(final Supplier<T> supplier) throws InterruptedException {
        try {
            return lockWhile(new ThrowingCallable<T, NeverThrown>() {
                @Override public T call() {
                    return supplier.get();
                }
            });
        }
        catch (NeverThrown throwable) {
            throw AppUtil.getInstance().unreachable(throwable);
        }
    }

    public <T,E extends Throwable> T lockWhile(ThrowingCallable<T,E> throwingCallable) throws InterruptedException, E {
        T result = null;
        lock();
        try {
            result = throwingCallable.call();
        } finally {
            unlock();
        }

        return result;
    }

    private void lock() throws InterruptedException {
        if (lockingThreadReference != null && lockingThreadReference.get().equals(Thread.currentThread())) {
            throw new RuntimeException(ATTEMPTED_REENTRANT_USAGE_ERROR_MESSAGE);
        }
        semaphore.acquire();
        lockingThreadReference = new WeakReference<>(Thread.currentThread());
    }

    private void unlock() {
        lockingThreadReference = null;
        semaphore.release();
    }
}
