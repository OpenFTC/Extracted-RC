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
import com.google.blocks.ftcrobotcontroller.hardware.HardwareUtil;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.collections.SimpleGson;
import org.firstinspires.ftc.robotcore.internal.opmode.BlocksClassFilter;

/**
 * A class that provides JavaScript access to miscellaneous functionality.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class MiscAccess extends Access {
  private HardwareMap hardwareMap;

  MiscAccess(BlocksOpMode blocksOpMode, String identifier, HardwareMap hardwareMap) {
    super(blocksOpMode, identifier, ""); // misc blocks don't have a first name.
    this.hardwareMap = hardwareMap;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object getNull() {
    try {
      startBlockExecution(BlockType.SPECIAL, "null");
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
  public boolean isNull(Object value) {
    try {
      startBlockExecution(BlockType.FUNCTION, "isNull");
      return (value == null);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean isNotNull(Object value) {
    try {
      startBlockExecution(BlockType.FUNCTION, "isNotNull");
      return (value != null);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String formatNumber(double number, int precision) {
    try {
      startBlockExecution(BlockType.FUNCTION, "formatNumber");
      return JavaUtil.formatNumber(number, precision);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String formatNumber_withWidth(double number, int width, int precision) {
    try {
      startBlockExecution(BlockType.FUNCTION, "formatNumber");
      return JavaUtil.formatNumber(number, width, precision);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public double roundDecimal(double number, int precision) {
    try {
      startBlockExecution(BlockType.FUNCTION, "roundDecimal");
      return Double.parseDouble(JavaUtil.formatNumber(number, precision));
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  // Note(lizlooney): The javascript to java bridge doesn't support calling var args methods, so we
  // have to explicitly declare all of the parameters. I have chosen to support methods with [0, 21]
  // arguments. We can't handle methods with over 21 parameters. 21 seems beyond reasonable to me,
  // but if we want to change that, we need to change this method and BlocksClassFilter.filterClass.

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object callJava(String methodLookupString, String json,
      Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7,
      Object a8, Object a9, Object a10, Object a11, Object a12, Object a13, Object a14,
      Object a15, Object a16, Object a17, Object a18, Object a19, Object a20) {
    try {
      startBlockExecution(BlockType.FUNCTION,
          "Java method " + BlocksClassFilter.getUserVisibleName(methodLookupString));
      return callJavaVarArgs(methodLookupString, json,
          a0, a1, a2, a3, a4, a5, a6, a7,
          a8, a9, a10, a11, a12, a13, a14,
          a15, a16, a17, a18, a19, a20);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean callJava_boolean(String methodLookupString, String json,
      Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7,
      Object a8, Object a9, Object a10, Object a11, Object a12, Object a13, Object a14,
      Object a15, Object a16, Object a17, Object a18, Object a19, Object a20) {
    try {
      startBlockExecution(BlockType.FUNCTION,
          "Java method " + BlocksClassFilter.getUserVisibleName(methodLookupString));
      return (Boolean) callJavaVarArgs(methodLookupString, json,
          a0, a1, a2, a3, a4, a5, a6, a7,
          a8, a9, a10, a11, a12, a13, a14,
          a15, a16, a17, a18, a19, a20);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String callJava_String(String methodLookupString, String json,
      Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7,
      Object a8, Object a9, Object a10, Object a11, Object a12, Object a13, Object a14,
      Object a15, Object a16, Object a17, Object a18, Object a19, Object a20) {
    try {
      startBlockExecution(BlockType.FUNCTION,
          "Java method " + BlocksClassFilter.getUserVisibleName(methodLookupString));
      Object result = callJavaVarArgs(methodLookupString, json,
          a0, a1, a2, a3, a4, a5, a6, a7,
          a8, a9, a10, a11, a12, a13, a14,
          a15, a16, a17, a18, a19, a20);
      return (result == null) ? null : result.toString();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private Object callJavaVarArgs(String methodLookupString, String json, Object... objectArgs) throws Throwable {
    Method method = BlocksClassFilter.getInstance().findStaticMethod(methodLookupString);
    if (method == null) {
      throw new RuntimeException("Could not find method " + methodLookupString + ".");
    }
    Object[] jsonArgs = SimpleGson.getInstance().fromJson(json, Object[].class);
    Class[] parameterTypes = method.getParameterTypes();
    if (jsonArgs.length != parameterTypes.length || objectArgs.length < parameterTypes.length) {
      throw new RuntimeException("Number of arguments does not match required number of parameters.");
    }
    String[] parameterLabels = HardwareUtil.getParameterLabels(method);
    List<Gamepad> gamepads = new ArrayList<>();
    Object[] args = new Object[parameterTypes.length];
    for (int i = 0; i < args.length; i++) {
      args[i] = determineArgument(adjustParameterType(parameterTypes[i]), objectArgs[i], jsonArgs[i],
          parameterLabels[i], gamepads);
    }
    try {
      return method.invoke(null, args);
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      if (cause != null) {
        throw cause;
      }
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Unable to invoke method " + methodLookupString + ".", e);
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public Object callHardware(String deviceName, String methodLookupString, String json,
      Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7,
      Object a8, Object a9, Object a10, Object a11, Object a12, Object a13, Object a14,
      Object a15, Object a16, Object a17, Object a18, Object a19, Object a20) {
    try {
      startBlockExecution(BlockType.FUNCTION,
          "Hardware method " + BlocksClassFilter.getUserVisibleName(methodLookupString));
      return callHardwareVarArgs(deviceName, methodLookupString, json,
          a0, a1, a2, a3, a4, a5, a6, a7,
          a8, a9, a10, a11, a12, a13, a14,
          a15, a16, a17, a18, a19, a20);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean callHardware_boolean(String deviceName, String methodLookupString, String json,
      Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7,
      Object a8, Object a9, Object a10, Object a11, Object a12, Object a13, Object a14,
      Object a15, Object a16, Object a17, Object a18, Object a19, Object a20) {
    try {
      startBlockExecution(BlockType.FUNCTION,
          "Hardware method " + BlocksClassFilter.getUserVisibleName(methodLookupString));
      return (Boolean) callHardwareVarArgs(deviceName, methodLookupString, json,
          a0, a1, a2, a3, a4, a5, a6, a7,
          a8, a9, a10, a11, a12, a13, a14,
          a15, a16, a17, a18, a19, a20);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String callHardware_String(String deviceName, String methodLookupString, String json,
      Object a0, Object a1, Object a2, Object a3, Object a4, Object a5, Object a6, Object a7,
      Object a8, Object a9, Object a10, Object a11, Object a12, Object a13, Object a14,
      Object a15, Object a16, Object a17, Object a18, Object a19, Object a20) {
    try {
      startBlockExecution(BlockType.FUNCTION,
          "Hardware method " + BlocksClassFilter.getUserVisibleName(methodLookupString));
      Object result = callHardwareVarArgs(deviceName, methodLookupString, json,
          a0, a1, a2, a3, a4, a5, a6, a7,
          a8, a9, a10, a11, a12, a13, a14,
          a15, a16, a17, a18, a19, a20);
      return (result == null) ? null : result.toString();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  private Object callHardwareVarArgs(String deviceName, String methodLookupString, String json, Object... objectArgs) throws Throwable {
    Method method = BlocksClassFilter.getInstance().findHardwareMethod(methodLookupString);
    if (method == null) {
      throw new RuntimeException("Could not find method " + methodLookupString + ".");
    }
    Object hardwareDevice = hardwareMap.get(method.getDeclaringClass(), deviceName);

    Object[] jsonArgs = SimpleGson.getInstance().fromJson(json, Object[].class);
    Class[] parameterTypes = method.getParameterTypes();
    if (jsonArgs.length != parameterTypes.length || objectArgs.length < parameterTypes.length) {
      throw new RuntimeException("Number of arguments does not match required number of parameters.");
    }
    String[] parameterLabels = HardwareUtil.getParameterLabels(method);
    List<Gamepad> gamepads = new ArrayList<>();
    Object[] args = new Object[parameterTypes.length];
    for (int i = 0; i < args.length; i++) {
      args[i] = determineArgument(adjustParameterType(parameterTypes[i]), objectArgs[i], jsonArgs[i],
          parameterLabels[i], gamepads);
    }
    try {
      return method.invoke(hardwareDevice, args);
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      if (cause != null) {
        throw cause;
      }
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Unable to invoke method " + methodLookupString + ".", e);
    }
  }

  private Class adjustParameterType(Class parameterType) {
    if (parameterType.equals(boolean.class)) {
      return Boolean.class;
    } else if (parameterType.equals(char.class)) {
      return Character.class;
    } else if (parameterType.equals(byte.class)) {
      return Byte.class;
    } else if (parameterType.equals(short.class)) {
      return Short.class;
    } else if (parameterType.equals(int.class)) {
      return Integer.class;
    } else if (parameterType.equals(long.class)) {
      return Long.class;
    } else if (parameterType.equals(float.class)) {
      return Float.class;
    } else if (parameterType.equals(double.class)) {
      return Double.class;
    }
    return parameterType;
  }

  private Object determineArgument(Class parameterType, Object objectArg, Object jsonArg,
      String parameterLabel, List<Gamepad> gamepads) {
    if (parameterType.equals(LinearOpMode.class) ||
        parameterType.equals(OpMode.class)) {
      return blocksOpMode;
    }
    if (parameterType.equals(HardwareMap.class)) {
      return blocksOpMode.hardwareMap;
    }
    if (parameterType.equals(Telemetry.class)) {
      return blocksOpMode.telemetry;
    }
    if (parameterType.equals(Gamepad.class)) {
      // If the parameter label is gamepad1 or gamepad2, return that.
      if (parameterLabel.equals("gamepad1")) {
        return blocksOpMode.gamepad1;
      } else if (parameterLabel.equals("gamepad2")) {
        return blocksOpMode.gamepad2;
      }
      // Otherwise, return the first element in the gamepads list. This will be gamepad1 for the
      // first Gamepad parameter and gamepad2 for the second Gamepad parameter.
      if (gamepads.isEmpty()) {
        gamepads.add(blocksOpMode.gamepad1);
        gamepads.add(blocksOpMode.gamepad2);
      }
      return gamepads.remove(0);
    }

    if (objectArg == null) {
      // objectArg is null. Therefore, argument is either null, primitive, or string.
      if (jsonArg == null) {
        return null;
      }
      // jsonArg is not null. Therefore, argument is primitive or string.
      if (parameterType.equals(jsonArg.getClass())) {
        return jsonArg;
      }

      if (jsonArg instanceof String) {
        // Try to coerce the jsonArgs value to the parameter type.
        try {
          return coerceStringValue((String) jsonArg, parameterType);
        } catch (Exception e) {
        }
        // Try to cast the jsonArgs value to the parameter type.
        try {
          return parameterType.cast(jsonArg);
        } catch (Exception e) {
        }
      }
    } else {
      // objectArg is not null. Therefore, argument is an object.
      if (parameterType.equals(objectArg.getClass())) {
        return objectArg;
      }
      // Try to cast the objectArgs value to the parameter type.
      try {
        return parameterType.cast(objectArg);
      } catch (Exception e) {
      }
    }

    throw new RuntimeException("Unable to convert " + objectArg + " and/or " +
        jsonArg + " to " + parameterType + ".");
  }

  private Object coerceStringValue(String value, Class parameterType) {
    if (parameterType.equals(Character.class)) {
      if (value.length() >= 1) {
        return new Character(value.charAt(0));
      }
    } else if (parameterType.equals(Byte.class)) {
      return (Byte) (byte) round(value);
    } else if (parameterType.equals(Short.class)) {
      return (Short) (short) round(value);
    } else if (parameterType.equals(Integer.class)) {
      return (Integer) (int) round(value);
    } else if (parameterType.equals(Long.class)) {
      return (Long) round(value);
    } else if (parameterType.equals(Float.class)) {
      return Float.valueOf(value);
    } else if (parameterType.equals(Double.class)) {
      return Double.valueOf(value);
    } else if (parameterType.isEnum()) {
      return coerceToEnum(value, parameterType);
    }
    throw new RuntimeException("Unable to convert \"" + value + "\" to " + parameterType);
  }

  @SuppressWarnings("unchecked")
  private Object coerceToEnum(String value, Class parameterType) {
    return checkArg(value, (Class<Enum>) parameterType, parameterType.getSimpleName());
  }

  private static long round(String value) {
    return Math.round(Double.valueOf(value));
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public int listLength(Object o) {
    try {
      startBlockExecution(BlockType.SPECIAL, "length of");
      return JavaUtil.listLength(o);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public boolean listIsEmpty(Object o) {
    try {
      startBlockExecution(BlockType.SPECIAL, "is empty");
      return JavaUtil.listIsEmpty(o);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String huskyLensBlockToText(String json) {
    try {
      startBlockExecution(BlockType.FUNCTION, "HuskyLens.Block", "toText");
      return HuskyLensAccess.huskyLensBlockToText(json);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String huskyLensArrowToText(String json) {
    try {
      startBlockExecution(BlockType.FUNCTION, "HuskyLens.Arrow", "toText");
      return HuskyLensAccess.huskyLensArrowToText(json);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  public String objectToJson(Object o) {
    try {
      if (o instanceof LLResult) {
        return LLResultAccess.llResultToJson((LLResult) o);
      }
      return toJson(o);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    }
  }
}
