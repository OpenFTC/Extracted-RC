/*
 * Copyright 2017 Google LLC
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
import com.qualcomm.robotcore.util.Range;

/**
 * A class that provides JavaScript access to {@link Range}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class RangeAccess extends Access {

  RangeAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "Range");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Range.class, methodName = "clip")
  public double clip(double number, double min, double max) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".clip");
      return Range.clip(number, min, max);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = Range.class, methodName = "scale")
  public double scale(double number, double x1, double x2, double y1, double y2) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".scale");
      return Range.scale(number, x1, x2, y1, y2);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}
