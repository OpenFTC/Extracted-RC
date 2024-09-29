/*
 * Copyright 2024 Google LLC
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
import com.qualcomm.hardware.limelightvision.LLStatus;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;

/**
 * A class that provides JavaScript access to LLStatus.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class LLStatusAccess extends Access {

  LLStatusAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "LLStatus");
  }

  private LLStatus checkLLStatus(Object llStatusArg) {
    return checkArg(llStatusArg, LLStatus.class, "llStatus");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LLStatus.class, methodName = "getCameraQuat")
  public Quaternion getCameraQuat(Object llStatusArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".getCameraQuat");
      LLStatus llStatus = checkLLStatus(llStatusArg);
      if (llStatusArg != null) {
        return llStatus.getCameraQuat();
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LLStatus.class, methodName = "toString")
  public String toText(Object llStatusArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".toText");
      LLStatus llStatus = checkLLStatus(llStatusArg);
      if (llStatusArg != null) {
        return llStatus.toString();
      }
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}
