/**
 * @license
 * Copyright 2020 Google LLC
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

/**
 * @fileoverview Methods included when we run a BlocksOpMode.
 * @author lizlooney@google.com (Liz Looney)
 */

// The following are defined via WebView.addJavascriptInterface in BlocksOpMode.java.
// blocksOpMode
// telemetry
// blackboard

// IMPORTANT!!! All identifiers in this file must be added as reserved words for the JavaScript runtime.
// This is done in the function addReservedWordsForJavaScriptRuntime in FtcBlocks_common.js.

// Note: This javascript code is executed while a blocks OpMode is running. If you want to put in
// debugging prints, you can use dbgLogAccess.msg("...").

var currentBlockLabel = '';

function callRunOpMode() {
  blocksOpMode.scriptStarting();
  try {
    // Call the runOpMode method in the generated javascript.
    runOpMode();
  } catch (e) {
    blocksOpMode.caughtException(String(e), currentBlockLabel);
  }
  blocksOpMode.scriptFinished();
}

function startBlockExecution(blockLabel) {
  currentBlockLabel = blockLabel;
  return true;
}

function endBlockExecution(value) {
  currentBlockLabel = '';
  return value;
}

function telemetryAddTextData(key, data) {
  switch (typeof data) {
    case 'string':
      telemetry.addTextData(key, data);
      break;
    case 'object':
      if (data instanceof Array) {
        telemetry.addTextData(key, String(data));
      } else if (data == null) {
        telemetry.addTextData(key, '');
      } else if (Object.keys(data).length == 0) {
        // This is a Java object.
        telemetry.addObjectData(key, data);
      } else {
        // This is a JavaScript object.
        telemetry.addTextData(key, JSON.stringify(data));
      }
      break;
    default:
      telemetry.addTextData(key, String(data));
      break;
  }
}

function telemetrySpeak(data, languageCode, countryCode) {
  switch (typeof data) {
    case 'string':
      telemetry.speakTextData(data, languageCode, countryCode);
      break;
    case 'object':
      if (data instanceof Array) {
        telemetry.speakTextData(String(data), languageCode, countryCode);
      } else if (data == null) {
         telemetry.speakTextData('', languageCode, countryCode);
      } else {
        telemetry.speakObjectData(data, languageCode, countryCode);
      }
      break;
    default:
      telemetry.speakTextData(String(data), languageCode, countryCode);
      break;
  }
}

function blackboardGet(key) {
  return blackboardGetOrDefault('get', key, null);
}

function blackboardRemove(key) {
  const value = blackboardGetOrDefault('remove', key, null);
  blackboard.remove(key);
  return value;
}

function blackboardGetOrDefault(label, key, defaultValue) {
  const valueType = blackboard.getType(label, key);
  switch (valueType) {
    case 'null':
      return defaultValue;
    case 'boolean':
      return blackboard.getBoolean(label, key);
    case 'number':
      return blackboard.getNumber(label, key);
    case 'string':
      return blackboard.getString(label, key);
    default:
      return blackboard.get(label, key);
  }
}

function blackboardPut(key, value) {
  switch (typeof value) {
    case 'boolean':
      blackboard.putBoolean(key, value);
      break;
    case 'number':
      blackboard.putNumber(key, value);
      break;
    case 'string':
      blackboard.putString(key, value);
      break;
    default:
      blackboard.put(key, value);
      break;
  }
}

function callJava(miscIdentifierForJavaScript, returnType, accessMethod, convertReturnValue,
    methodLookupString) {
  // According to
  // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions/rest_parameters
  // Javascript rest parameters (the ... parameter) is not available until Chrome 47.
  // The Dragonboard's WebView is based on Chrome 44, so we can't use rest parameters.
  // Get the extra parameters the old-fashioned way.
  var numNamedArgs = callJava.length;
  var extraParameters = [];
  for (var i = numNamedArgs; i < arguments.length; i++) {
    if (typeof arguments[i] == 'object') {
      extraParameters[i - numNamedArgs] = getJavaObject(arguments[i]);
    } else {
      extraParameters[i - numNamedArgs] = arguments[i];
    }
  }

  var newRest = Array.prototype.slice.call(extraParameters);
  for (var i = 0; i < newRest.length; i++) {
    if (typeof newRest[i] == 'number') {
      newRest[i] = String(newRest[i]);
    }
  }
  var newArgs = Array.prototype.slice.call(extraParameters);
  newArgs.unshift(methodLookupString, JSON.stringify(newRest));
  while (newArgs.length < 23) { // MiscAccess.callJava, callJava_boolean, and callJava_String have 23 args
    newArgs.push(null);
  }
  var result = miscIdentifierForJavaScript[accessMethod].apply(miscIdentifierForJavaScript, newArgs);
  switch (convertReturnValue) {
    case 'Number':
      result = Number(result);
      break;
  }
  return result;
}


function callHardware(miscIdentifierForJavaScript, returnType, accessMethod, convertReturnValue,
    deviceName, methodLookupString) {
  // According to
  // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions/rest_parameters
  // Javascript rest parameters (the ... parameter) is not available until Chrome 47.
  // The Dragonboard's WebView is based on Chrome 44, so we can't use rest parameters.
  // Get the extra parameters the old-fashioned way.
  var numNamedArgs = callHardware.length;
  var extraParameters = [];
  for (var i = numNamedArgs; i < arguments.length; i++) {
    if (typeof arguments[i] == 'object') {
      extraParameters[i - numNamedArgs] = getJavaObject(arguments[i]);
    } else {
      extraParameters[i - numNamedArgs] = arguments[i];
    }
  }

  var newRest = Array.prototype.slice.call(extraParameters);
  for (var i = 0; i < newRest.length; i++) {
    if (typeof newRest[i] == 'number') {
      newRest[i] = String(newRest[i]);
    }
  }
  var newArgs = Array.prototype.slice.call(extraParameters);
  newArgs.unshift(deviceName, methodLookupString, JSON.stringify(newRest));
  while (newArgs.length < 24) { // MiscAccess.callHardware, callHardware_boolean, and callHardware_String have 24 args
    newArgs.push(null);
  }
  var result = miscIdentifierForJavaScript[accessMethod].apply(miscIdentifierForJavaScript, newArgs);
  switch (convertReturnValue) {
    case 'Number':
      result = Number(result);
      break;
  }
  return result;
}

function listLength(miscIdentifierForJavaScript, o) {
  // If o is a javascript array or string, just return o.length.
  switch (typeof o) {
    case 'object':
      if (o instanceof Array) {
        return o.length;
      }
      break;
    case 'string':
      return o.length;
  }

  // Otherwise, pass o to the Java helper function.
  return miscIdentifierForJavaScript.listLength(o);
}

function listIsEmpty(miscIdentifierForJavaScript, o) {
  // If o is a javascript array or string, just return o.length == 0.
  switch (typeof o) {
    case 'object':
      if (o instanceof Array) {
        return o.length == 0;
      }
      break;
    case 'string':
      return o.length == 0;
  }

  // Otherwise, pass o to the Java helper function.
  return miscIdentifierForJavaScript.listIsEmpty(o);
}

function nullOrJson(s) {
  if (typeof s == 'string') {
    return JSON.parse(s);
  }
  return null;
}

function evalIfTruthy(o, code, otherwise) {
  return o ? eval(code) : otherwise;
}

var mapJavaToJavaScript = new WeakMap(); // Maps a Java object/array to a JavaScript object/list
var mapJavaScriptToJava = new WeakMap(); // Maps a JavaScript object/list to a Java object/array

function getObjectViaJson(miscIdentifierForJavaScript, oJava) {
  var oJavaScript = mapJavaToJavaScript.get(oJava);
  if (oJavaScript) {
    return oJavaScript;
  }
  var json = miscIdentifierForJavaScript.objectToJson(oJava);
  oJavaScript = JSON.parse(json);
  mapJavaToJavaScript.set(oJava, oJavaScript);
  mapJavaScriptToJava.set(oJavaScript, oJava);
  return oJavaScript;
}

function updateObjectViaJson(miscIdentifierForJavaScript, oJava) {
  // Remove the old values from the maps.
  var oJavaScript = mapJavaToJavaScript.get(oJava);
  if (oJavaScript) {
    mapJavaScriptToJava.delete(oJavaScript);
  }
  if (mapJavaToJavaScript.has(oJava)) {
    mapJavaToJavaScript.delete(oJava);
  }

  // Get the new values and put them in the maps.
  return getObjectViaJson(miscIdentifierForJavaScript, oJava);
}

function getJavaObject(oJavaScript) {
  var oJava = mapJavaScriptToJava.get(oJavaScript);
  if (oJava) {
    return oJava;
  }
  // If oJavaScript isn't in the map, it might be that oJavaScript came from a
  // MyBlocks block, which means it is really a Java Object.
  return oJavaScript;
}

function colorBlobsFilterByCriteria(criteria, minValue, maxValue, blobs) {
  var toRemove = [];
  for (var i = 0; i < blobs.length; i++) {
    var b = blobs[i];
    var value = 0;
    switch (criteria) {
      case 'BY_CONTOUR_AREA':
        value = b.ContourArea;
        break;
      case 'BY_DENSITY':
        value = b.Density;
        break;
      case 'BY_ASPECT_RATIO':
        value = b.AspectRatio;
        break;
      case 'BY_ARC_LENGTH':
        value = b.ArcLength;
        break;
      case 'BY_CIRCULARITY':
        value = b.Circularity;
        break;
    }
    if (value > maxValue || value < minValue) {
      toRemove.push(i);
    }
  }
  for (var i = toRemove.length - 1; i >= 0; i--) {
    blobs.splice(toRemove[i], 1);
  }
}

function colorBlobsSortByCriteria(criteria, sortOrder, blobs) {
  blobs.sort(function(c1, c2) {
    var tmp = 0;
    switch (criteria) {
      case 'BY_CONTOUR_AREA':
        tmp = Math.sign(c2.ContourArea - c1.ContourArea);
        break;
      case 'BY_DENSITY':
        tmp = Math.sign(c2.Density - c1.Density);
        break;
      case 'BY_ASPECT_RATIO':
        tmp = Math.sign(c2.AspectRatio - c1.AspectRatio);
        break;
      case 'BY_ARC_LENGTH':
        tmp = Math.sign(c2.ArcLength - c1.ArcLength);
        break;
      case 'BY_CIRCULARITY':
        tmp = Math.sign(c2.Circularity - c1.Circularity);
        break;
    }
    if (sortOrder == "ASCENDING") {
      tmp = -tmp;
    }
    return tmp;
  });
}

// The block for colorBlobsFilterByArea is no longer in the toolbox, but older blocks OpModes
// might still have it.
function colorBlobsFilterByArea(minArea, maxArea, blobs) {
  colorBlobsFilterByCriteria('BY_CONTOUR_AREA', minArea, maxArea, blobs);
}

// The block for colorBlobsSortByArea is no longer in the toolbox, but older blocks OpModes
// might still have it.
function colorBlobsSortByArea(sortOrder, blobs) {
  colorBlobsSortByCriteria('BY_CONTOUR_AREA', sortOrder, blobs);
}

// The block for colorBlobsFilterByDensity is no longer in the toolbox, but older blocks OpModes
// might still have it.
function colorBlobsFilterByDensity(minDensity, maxDensity, blobs) {
  colorBlobsFilterByCriteria('BY_DENSITY', minDensity, maxDensity, blobs);
}

// The block for colorBlobsSortByDensity is no longer in the toolbox, but older blocks OpModes
// might still have it.
function colorBlobsSortByDensity(sortOrder, blobs) {
  colorBlobsSortByCriteria('BY_DENSITY', sortOrder, blobs);
}

// The block for colorBlobsFilterByAspectRatio is no longer in the toolbox, but older blocks OpModes
// might still have it.
function colorBlobsFilterByAspectRatio(minAspectRatio, maxAspectRatio, blobs) {
  colorBlobsFilterByCriteria('BY_ASPECT_RATIO', minAspectRatio, maxAspectRatio, blobs);
}

// The block for colorBlobsSortByAspectRatio is no longer in the toolbox, but older blocks OpModes
// might still have it.
function colorBlobsSortByAspectRatio(sortOrder, blobs) {
  colorBlobsSortByCriteria('BY_ASPECT_RATIO', sortOrder, blobs);
}

// IMPORTANT!!! All identifiers in this file must be added as reserved words for the JavaScript runtime.
// This is done in the function addReservedWordsForJavaScriptRuntime in FtcBlocks_common.js.
