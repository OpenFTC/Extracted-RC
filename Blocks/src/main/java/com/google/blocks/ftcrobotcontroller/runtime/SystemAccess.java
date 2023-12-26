/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.blocks.ftcrobotcontroller.runtime;

import android.webkit.JavascriptInterface;

/**
 * A class that provides JavaScript access to {@link System}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class SystemAccess extends Access {

  SystemAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "System");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public long nanoTime() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".nanoTime");
      return System.nanoTime();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public long currentTimeMillis() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".currentTimeMillis");
      return System.currentTimeMillis();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}
