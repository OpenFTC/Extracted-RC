/**
 * @license
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

/**
 * @fileoverview Hardware utilities.
 * @author lizlooney@google.com (Liz Looney)
 */

// Note: This file is misnamed. It includes some utilities not related to hardware.

/**
 * Fetches the JavaScript code related to the hardware in the active configuration and calls the
 * callback.
 */
function fetchJavaScriptForHardware(callback) {
  if (window.location.protocol === 'http:' || window.location.protocol === 'https:') {
    // html/js is in a browser, loaded as an http:// URL.
    fetchJavaScriptForHardwareViaHttp(callback);
  } else if (window.location.protocol === 'file:') {
    // html/js is in a browser, loaded as an file:// URL.
    fetchJavaScriptForHardwareViaFile(callback);
  }
}

function getConfigurationName(callback) {
  if (window.location.protocol === 'http:' || window.location.protocol === 'https:') {
    // html/js is in a browser, loaded as an http:// URL.
    getConfigurationNameViaHttp(callback);
  } else if (window.location.protocol === 'file:') {
    // html/js is in a browser, loaded as an file:// URL.
    getConfigurationNameViaFile(callback);
  }
}

//..........................................................................
// Code used when html/js is in a browser, loaded as a http:// URL.

// The following are generated dynamically in ProgrammingModeServer.fetchJavaScriptForServer():
// URI_HARDWARE
// PARAM_NAME

function fetchJavaScriptForHardwareViaHttp(callback) {
  var xhr = new XMLHttpRequest();
  xhr.open('GET', URI_HARDWARE, true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        var jsHardware = xhr.responseText;
        callback(jsHardware, '');
      } else {
        // TODO(lizlooney): Use specific error messages for various xhr.status values.
        callback(null, 'Fetch JavaScript for Hardware failed. Error code ' + xhr.status + '. ' + xhr.statusText);
      }
    }
  };
  xhr.send();
}

function getConfigurationNameViaHttp(callback) {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', URI_GET_CONFIGURATION_NAME, true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        var className = xhr.responseText;
        callback(className, '');
      } else {
        // TODO(lizlooney): Use specific error messages for various xhr.status values.
        callback(null, 'Get configuration name failed. Error code ' + xhr.status + '. ' + xhr.statusText);
      }
    }
  };
  xhr.send();
}

//..........................................................................
// Code used when html/js is in a browser, loaded as a file:// URL.

function fetchJavaScriptForHardwareViaFile(callback) {
  setTimeout(function() {
    callback('// See FtcOfflineBlocks.js', '');
  }, 0);
}

function getConfigurationNameViaFile(callback) {
  setTimeout(function() {
    callback(getOfflineConfigurationName(), '');
  }, 0);
}
