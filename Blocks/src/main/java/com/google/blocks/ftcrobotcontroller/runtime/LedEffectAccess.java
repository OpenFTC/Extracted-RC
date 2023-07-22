/*
 * Copyright 2021 Google LLC
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
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Gamepad.LedEffect;

/**
 * A class that provides JavaScript access to {@link LedEffect}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class LedEffectAccess extends Access {

  LedEffectAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "LedEffect");
  }

  private LedEffect.Builder checkLedEffectBuilder(Object ledEffectBuilderArg) {
    return checkArg(ledEffectBuilderArg, LedEffect.Builder.class, "ledEffectBuilder");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LedEffect.Builder.class, constructor = true)
  public LedEffect.Builder createBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new LedEffect.Builder();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LedEffect.Builder.class, methodName = "addStep")
  public void addStep(Object ledEffectBuilderArg, double red, double green, double blue, int millis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addStep");
      LedEffect.Builder ledEffectBuilder = checkLedEffectBuilder(ledEffectBuilderArg);
      if (ledEffectBuilder != null) {
        ledEffectBuilder.addStep(red, green, blue, millis);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LedEffect.Builder.class, methodName = "setRepeating")
  public void setRepeating(Object ledEffectBuilderArg, boolean repeating) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".setRepeating");
      LedEffect.Builder ledEffectBuilder = checkLedEffectBuilder(ledEffectBuilderArg);
      if (ledEffectBuilder != null) {
        ledEffectBuilder.setRepeating(repeating);
      }
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = LedEffect.Builder.class, methodName = "build")
  public LedEffect build(Object ledEffectBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".build");
      LedEffect.Builder ledEffectBuilder = checkLedEffectBuilder(ledEffectBuilderArg);
      if (ledEffectBuilder != null) {
        return ledEffectBuilder.build();
      }
      return null;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}
