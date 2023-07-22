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
import com.qualcomm.robotcore.hardware.Gamepad.RumbleEffect;

/**
 * A class that provides JavaScript access to {@link RumbleEffect}.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class RumbleEffectAccess extends Access {

  RumbleEffectAccess(BlocksOpMode blocksOpMode, String identifier) {
    super(blocksOpMode, identifier, "RumbleEffect");
  }

  private RumbleEffect.Builder checkRumbleEffectBuilder(Object rumbleEffectBuilderArg) {
    return checkArg(rumbleEffectBuilderArg, RumbleEffect.Builder.class, "rumbleEffectBuilder");
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = RumbleEffect.Builder.class, constructor = true)
  public RumbleEffect.Builder createBuilder() {
    try {
      startBlockExecution(BlockType.CREATE, "");
      return new RumbleEffect.Builder();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = RumbleEffect.Builder.class, methodName = "addStep")
  public void addStep(Object rumbleEffectBuilderArg, double rumble1, double rumble2, int millis) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".addStep");
      RumbleEffect.Builder rumbleEffectBuilder = checkRumbleEffectBuilder(rumbleEffectBuilderArg);
      if (rumbleEffectBuilder != null) {
        rumbleEffectBuilder.addStep(rumble1, rumble2, millis);
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
  @Block(classes = RumbleEffect.Builder.class, methodName = "build")
  public RumbleEffect build(Object rumbleEffectBuilderArg) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".build");
      RumbleEffect.Builder rumbleEffectBuilder = checkRumbleEffectBuilder(rumbleEffectBuilderArg);
      if (rumbleEffectBuilder != null) {
        return rumbleEffectBuilder.build();
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
