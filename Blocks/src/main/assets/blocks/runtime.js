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
      } else {
        telemetry.speakObjectData(data, languageCode, countryCode);
      }
      break;
    default:
      telemetry.speakTextData(String(data), languageCode, countryCode);
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
    extraParameters[i - numNamedArgs] = arguments[i]
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
    extraParameters[i - numNamedArgs] = arguments[i]
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
