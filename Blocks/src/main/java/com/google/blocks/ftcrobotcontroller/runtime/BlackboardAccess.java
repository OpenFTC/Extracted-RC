/*
 * Copyright 2025 Google LLC
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
import com.qualcomm.robotcore.util.RobotLog;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that provides JavaScript access to OpMode.blackboard.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
class BlackboardAccess extends Access {
  private final Map<String, Object> blackboard;

  BlackboardAccess(BlocksOpMode blocksOpMode, String identifier, Map<String, Object> blackboard) {
    super(blocksOpMode, identifier, "Blackboard");
    this.blackboard = blackboard;
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "clear")
  public void clear() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".clear");
      blackboard.clear();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "containsKey")
  public boolean containsKey(String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".containsKey");
      return blackboard.containsKey(key);
    } catch (Throwable e) {
      e.printStackTrace();
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(exclusiveToBlocks = true)
  public String getType(String label, String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, "." + label);
      Object value = blackboard.get(key);
      if (value == null) {
        return "null";
      }
      if (value instanceof Boolean) {
        return "boolean";
      }
      if (value instanceof Number) {
        return "number";
      }
      if (value instanceof String) {
        return "string";
      }
      return "object";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "get")
  public Object get(String label, String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, "." + label);
      return blackboard.get(key);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "get")
  public boolean getBoolean(String label, String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, "." + label);
      Object value = blackboard.get(key);
      if (value instanceof Boolean) {
        return ((Boolean) value).booleanValue();
      }
      RobotLog.ww("BlackboardAccess", "Expected blackboard.get(" + key + ") to return a Boolean");
      return false;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "get")
  public double getNumber(String label, String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, "." + label);
      Object value = blackboard.get(key);
      if (value instanceof Number) {
        return ((Number) value).doubleValue();
      }
      RobotLog.ww("BlackboardAccess", "Expected blackboard.get(" + key + ") to return a Number");
      return 0;
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "get")
  public String getString(String label, String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, "." + label);
      Object value = blackboard.get(key);
      if (value instanceof String) {
        return (String) value;
      }
      RobotLog.ww("BlackboardAccess", "Expected blackboard.get(" + key + ") to return a String");
      return "";
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "isEmpty")
  public boolean isEmpty() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".isEmpty");
      return blackboard.isEmpty();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "put")
  public void put(String key, Object value) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".put");
      blackboard.put(key, value);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "put")
  public void putBoolean(String key, boolean value) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".put");
      blackboard.put(key, value);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "put")
  public void putNumber(String key, double value) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".put");
      blackboard.put(key, value);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "put")
  public void putString(String key, String value) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".put");
      blackboard.put(key, value);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "remove")
  public Object remove(String key) {
    try {
      startBlockExecution(BlockType.FUNCTION, ".remove");
      return blackboard.remove(key);
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }

  @SuppressWarnings("unused")
  @JavascriptInterface
  @Block(classes = {HashMap.class, Map.class}, methodName = "size")
  public int size() {
    try {
      startBlockExecution(BlockType.FUNCTION, ".size");
      return blackboard.size();
    } catch (Throwable e) {
      blocksOpMode.handleFatalException(e);
      throw new AssertionError("impossible", e);
    } finally {
      endBlockExecution();
    }
  }
}
