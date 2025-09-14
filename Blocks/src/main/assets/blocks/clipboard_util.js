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
 * @fileoverview Clipboard utilities.
 * @author lizlooney@google.com (Liz Looney)
 */

/**
 * Saves the clipboard content and calls the callback.
 */
function saveClipboardContent(clipboardContent, callback) {
  if (isOnline()) {
    saveClipboardContentViaHttp(clipboardContent, callback);
  } else {
    saveClipboardContentViaFile(clipboardContent, callback);
  }
}

/**
 * Fetches the previously saved clipboard content and calls the callback.
 */
function fetchClipboardContent(callback) {
  if (isOnline()) {
    fetchClipboardContentViaHttp(callback);
  } else {
    fetchClipboardContentViaFile(callback);
  }
}

//..........................................................................
// Code used when html/js is in a browser, loaded as an http:// URL.

// The following are generated dynamically in ProgrammingModeServer.fetchJavaScriptForServer():
// URI_SAVE_CLIPBOARD
// URI_FETCH_CLIPBOARD
// PARAM_CLIPBOARD

function saveClipboardContentViaHttp(clipboardContent, callback) {
  var xhr = new XMLHttpRequest();
  var params = PARAM_CLIPBOARD + '=' + encodeURIComponent(clipboardContent);
  xhr.open('POST', URI_SAVE_CLIPBOARD, true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        callback(true, '');
      } else {
        // TODO(lizlooney): Use specific error messages for various xhr.status values.
        callback(false, 'Save clipboard failed. Error code ' + xhr.status + '. ' + xhr.statusText);
      }
    }
  };
  xhr.send(params);
}

function fetchClipboardContentViaHttp(callback) {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', URI_FETCH_CLIPBOARD, true);
  xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4) {
      if (xhr.status === 200) {
        var clipboardContent = xhr.responseText;
        callback(clipboardContent, '');
      } else {
        // TODO(lizlooney): Use specific error messages for various xhr.status values.
        callback(null, 'Fetch clipboard failed. Error code ' + xhr.status + '. ' + xhr.statusText);
      }
    }
  };
  xhr.send();
}

//..........................................................................
// Code used when html/js is in a browser, loaded as an file:// URL.

function saveClipboardContentViaFile(clipboardContent, callback) {
  if (!db) {
    openOfflineDatabase(function(success, errorReason) {
      if (success) {
        saveClipboardContentViaFile(clipboardContent, callback);
      } else {
        callback(null, 'Save clipboard content failed. (' + errorReason + ')');
      }
    });
    return;
  }
  var otherFilesObjectStore = db.transaction(['otherFiles'], 'readwrite')
      .objectStore('otherFiles');
  var getRequest = otherFilesObjectStore.get('clipboard.xml');
  getRequest.onerror = function(event) {
    callback(false, 'Save clipboard content failed. (getRequest error)');
  };
  getRequest.onsuccess = function(event) {
    if (event.target.result === undefined) {
      callback(null, 'Save clipboard content failed. (not found)');
      return;
    }
    var value = event.target.result;
    value['Content'] = clipboardContent;
    var putRequest = otherFilesObjectStore.put(value);
    putRequest.onerror = function(event) {
      callback(false, 'Save clipboard content failed. (putRequest error)');
    };
    putRequest.onsuccess = function(event) {
      callback(true, '');
    };
  };
}

function fetchClipboardContentViaFile(callback) {
  if (!db) {
    openOfflineDatabase(function(success, errorReason) {
      if (success) {
        fetchClipboardContentViaFile(callback);
      } else {
        callback(null, 'Fetch clipboard content failed. (' + errorReason + ')');
      }
    });
    return;
  }
  var getRequest = db.transaction(['otherFiles'], 'readonly')
      .objectStore('otherFiles').get('clipboard.xml');
  getRequest.onerror = function(event) {
    callback(null, 'Fetch clipboard content failed. (getRequest error)');
  };
  getRequest.onsuccess = function(event) {
    if (event.target.result === undefined) {
      callback(null, 'Fetch clipboard content failed. (not found)');
      return;
    }
    var value = event.target.result;
    callback(value['Content'], '');
  };
}
