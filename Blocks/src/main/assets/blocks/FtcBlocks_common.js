/**
 * @license
 * Copyright 2019 Google LLC
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
 * @fileoverview functions used in both FtcBlocks.html and FtcOfflineBlocks.html
 * @author lizlooney@google.com (Liz Looney)
 */

// NOTE(lizlooney): If/when we update to a newer version of blockly, we need to check that thie
// following still works.
Blockly.Block.prototype.original_setCommentText = Blockly.Block.prototype.setCommentText;

Blockly.Block.prototype.setCommentText = function(text) {
  this.original_setCommentText(text);
  if (text == null) {
    clearBlockCommentPosition(block);
  }
}

// NOTE(lizlooney): If/when we update to a newer version of blockly, we need to check that thie
// following still works.
Blockly.Comment.prototype.original_setVisible = Blockly.Comment.prototype.setVisible;

Blockly.Comment.prototype.setVisible = function(visible) {
  const alreadyHadBubble = (this.bubble_ ? true : false);

  // If this comment is becoming invisible, save its bubble's position, which won't be available
  // after it becomes invisible.
  if (this.block_ && this.bubble_ && !visible) {
    saveBlockCommentPosition(this.block_, this.bubble_);
  }

  this.original_setVisible(visible);

  // If this comment didn't have a bubble before and now has a bubble, restore the bubble's saved
  // position.
  if (this.block_ && !alreadyHadBubble && this.bubble_) {
    restoreBlockCommentPosition(this.block_, this.bubble_);
  }
};

function initializeFtcBlocks() {
  setUpWebSocket();

  fetchJavaScriptForHardware(function(jsHardware, errorMessage) {
    if (jsHardware) {
      var newScript = document.createElement('script');
      newScript.setAttribute('type', 'text/javascript');
      newScript.innerHTML = jsHardware;
      document.getElementsByTagName('head')[0].appendChild(newScript);

      initializeBlockly();
      initializeToolbox();
      initializeAutoTransitionSelect();

      setTimeout(function() {
        initializeBlocks();
      }, 10);
    } else  {
      alert(errorMessage);
    }
  });
}

function initializeAutoTransitionSelect() {
  // AUTO_TRANSITION_OPTIONS is generated dynamically in HardwareUtil.fetchJavaScriptForHardware().
  var autoTransitionSelect = document.getElementById('project_autoTransition');
  // First add a blank option.
  const option = document.createElement('option');
  option.value = '';
  option.text = '';
  autoTransitionSelect.add(option);
  // Then add the generated options, which are the names of the TeleOp OpModes.
  for (var i = 0; i < AUTO_TRANSITION_OPTIONS.length; i++) {
    const option = document.createElement('option');
    option.value = AUTO_TRANSITION_OPTIONS[i];
    option.text = AUTO_TRANSITION_OPTIONS[i];
    autoTransitionSelect.add(option);
  }
}

function initializeBlocks() {
  var projectName = getParameterByName('project');
  if (isValidProjectName(projectName) && !containsAmpersand(projectName) && containsAtLeastOneAlphanumeric(projectName)) {
    currentProjectName = projectName;
    getBlocksJavaClassName(currentProjectName, function(className, errorMessage) {
      if (className) {
        currentClassName = className;
        Blockly.FtcJava.setClassNameForFtcJava_(currentClassName);
      } else {
        if (errorMessage) {
          alert(errorMessage);
        } else {
          alert('Error: The specified project name is not valid. Please rename it in the Blocks projects page.');
        }
      }
    });
    fetchBlkFileContent(currentProjectName, function(blkFileContent, errorMessage) {
      if (blkFileContent) {
        savedBlkFileContent = blkFileContent;
        var blocksLoadedCallback = function() {
          showJava();
        };
        loadBlocks(blkFileContent, blocksLoadedCallback);
      } else {
        alert(errorMessage);
      }
    });
  } else {
    alert('Error: The specified project name is not valid. Please rename it on the Blocks projects page.');
  }
}

function displayBanner(text, buttonText, buttonCallback) {
  banner.style.display = 'flex';
  bannerText.innerHTML = text;
  bannerButton.innerHTML = buttonText;
  bannerButton.onclick = buttonCallback;
  resizeBlocklyArea();
}

function hideBanner() {
  banner.style.display = 'none';
  resizeBlocklyArea();
}

/**
 * Get a URL parameter by name.
 * From http://stackoverflow.com/a/901144
 */
function getParameterByName(name) {
  url = window.location.href;
  name = name.replace(/[\[\]]/g, '\\$&');
  var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
      results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return '';
  return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function afterBlocklyClipboardCaptured(clipboardContent) {
  // Save clipboard content.
  savedClipboardContent = clipboardContent;
  saveClipboardContent(savedClipboardContent, function(success, errorMessage) {
    if (! success) {
      console.log(errorMessage);
    }
  });
}

function paste() {
  // Fetch clipboard text
  fetchClipboardContent(function(clipboardContent, errorMessage) {
    if (!clipboardContent) {
      // If we failed to fetch the clipboard, use the saved clipboard content.
      clipboardContent = savedClipboardContent;
    }
    pasteContent(clipboardContent);
  });
}

function projectEnabledChanged() {
  var enabledCheckbox = document.getElementById('project_enabled');
  var isChecked = enabledCheckbox.checked;
  enableProject(currentProjectName, isChecked, function(success, errorMessage) {
    if (success) {
      projectEnabled = isChecked;
      showJava();
    } else {
      // Undo the checkbox change in the UI.
      enabledCheckbox.checked = projectEnabled;
      console.log(errorMessage);
    }
  });
}

/**
 * Saves the workspace blocks (including OpMode flavor, group, autoTransition, enable) and
 * generated javascript.
 * Called from Save button onclick.
 */
function saveButtonClicked() {
  if (blockIdsWithMissingHardware.length == 0) {
    saveProjectNow();
  } else {
    var messageDiv = document.getElementById('saveWithWarningsMessage');
    if (blockIdsWithMissingHardware.length == 1) {
      if (missingHardware.length == 1) {
        messageDiv.innerHTML = 'There is 1 block that uses a missing hardware device.';
      } else {
        messageDiv.innerHTML = 'There is 1 block that uses missing hardware devices.';
      }
    } else {
      if (missingHardware.length == 1) {
        messageDiv.innerHTML = 'There are ' + blockIdsWithMissingHardware.length +
            ' blocks that use a missing hardware device.';
      } else {
        messageDiv.innerHTML = 'There are ' + blockIdsWithMissingHardware.length +
            ' blocks that use missing hardware devices.';
      }
    }
     document.getElementById('saveWithWarningsDialog').style.display = 'block';
  }
}

function noSaveWithWarningsDialog() {
  // Close the dialog.
  document.getElementById('saveWithWarningsDialog').style.display = 'none';
}

function yesSaveWithWarningsDialog() {
  // Close the dialog.
  document.getElementById('saveWithWarningsDialog').style.display = 'none';
  saveProjectNow();
}

function getCurrentBlkFileContent() {
  var allBlocks = workspace.getAllBlocks();
  for (var iBlock = 0, block; block = allBlocks[iBlock]; iBlock++) {
    saveBlockWarningHidden(block);
    if (block.comment && block.comment.bubble_) {
      saveBlockCommentPosition(block, block.comment.bubble_);
    } else if (!block.comment) {
      clearBlockCommentPosition(block);
    }
  }
  // Get the blocks as xml (text).
  var blocksContent = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(workspace));
  var flavorSelect = document.getElementById('project_flavor');
  var flavor = flavorSelect.options[flavorSelect.selectedIndex].value;
  var group = document.getElementById('project_group').value;
  var autoTransitionSelect = document.getElementById('project_autoTransition');
  var autoTransition = autoTransitionSelect.options[autoTransitionSelect.selectedIndex].value;
  var blkFileContent = blocksContent + formatExtraXml(flavor, group, autoTransition, projectEnabled);
  // Break the blocks content into multiple lines so it is easier to read/diff.
  var formattedBlkFileContent = blkFileContent
      .replace(/></g, '>\n<')
      .replace(/>\n<\/field>/g, '></field>')
      .replace(/<\/Extra> /g, '</Extra>');
  if (!formattedBlkFileContent.endsWith('\n')) {
    formattedBlkFileContent += '\n';
  }
  return formattedBlkFileContent;
}

function saveProjectNow(opt_success_callback) {
  if (currentProjectName) {
    // Get the blocks as xml (text).
    const blkFileContent = getCurrentBlkFileContent();

    // Generate JavaScript code.
    const jsFileContent = generateJavaScriptCode();

    saveProject(currentProjectName, blkFileContent, jsFileContent,
        function(success, errorMessage) {
      if (success) {
        savedBlkFileContent = blkFileContent;
        document.getElementById('saveSuccess').style.display = 'inline-block';
        document.getElementById('saveFailure').style.display = 'none';
        window.setTimeout(function() {
          document.getElementById('saveSuccess').style.display = 'none';
        }, 3000);
        if (opt_success_callback) {
          opt_success_callback();
        }
      } else {
        document.getElementById('saveSuccess').style.display = 'none';
        document.getElementById('saveFailure').innerHTML = errorMessage;
        document.getElementById('saveFailure').style.display = 'inline-block';
      }
    });
  } else {
    alert('The specified project name is not valid');
  }
}

/**
 * After saving the project, downloads the blk file.
 * Called from Download button onclick.
 */
function downloadButtonClicked() {
  saveProjectNow(function() {
    fetchBlkFileContent(currentProjectName, function(blkFileContent, errorMessage) {
      if (blkFileContent) {
        downloadBlocks(blkFileContent);
      } else {
        alert(errorMessage);
      }
    });
  });
}

function initializeSplit() {
  split = window.Split([blocksAndBannerArea, javaArea], {
    direction: 'horizontal',
    sizes: [75, 25],
    minSize: [200, 100],
    gutterSize: 4,
    snapOffset: 0,
    onDrag: resizeBlocklyArea,
  });
}

// Initialize global variables & blockly itself
function initializeBlockly() {
  addReservedWordsForJavaScript();
  addReservedWordsForFtcJava();
  addReservedWordsForFtcJavaObsolete();

  document.addEventListener('mousemove', onMouseMove);
  document.addEventListener('keydown', onKeyDown);

  // Blockly's text_quotes extension (which uses images for the quotes) causes the Download
  // Image feature fail. Here, we replace it with one that uses quote characters.
  Blockly.Extensions.ALL_['text_quotes'] = function() {
    for (var i = 0, input; input = this.inputList[i]; i++) {
      for (var j = 0, field; field = input.fieldRow[j]; j++) {
        if ('TEXT' == field.name) {
          var before = workspace.RTL ? '\u201D' : '\u201C';
          var after = workspace.RTL ? '\u201C' : '\u201D';
          input.insertFieldAt(j, new Blockly.FieldLabel(before));
          input.insertFieldAt(j + 2, new Blockly.FieldLabel(after));
          return;
        }
      }
    }
  };

  showJavaCheckbox = document.getElementById('show_java');
  javaArea = document.getElementById('javaArea');
  javaContent = document.getElementById('javaContent');
  parentArea = document.getElementById('parentArea');
  blocksAndBannerArea = document.getElementById('blocksAndBannerArea');
  blocklyArea = document.getElementById('blocklyArea');
  blocklyDiv = document.getElementById('blocklyDiv');
  banner = document.getElementById('banner');
  bannerText = document.getElementById('bannerText');
  bannerButton = document.getElementById('bannerBtn');
  workspace = Blockly.inject(blocklyDiv, {
    media: 'blockly/media/',
    zoom: {
      controls: true,
      wheel: true,
      startScale: 1.2,
      maxScale: 5,
      minScale: 0.3,
      scaleSpeed: 1.2},
    trashcan: false,
    toolbox: document.getElementById('toolbox')
  });

  if (parentArea.clientWidth >= 800) {
    showJavaCheckbox.checked = true;
  }
  showJavaChanged();

  parentArea.style.visibility = 'visible'; // Unhide parentArea

  window.addEventListener('resize', resizeBlocklyArea, false);
  resizeBlocklyArea();
  window.addEventListener('beforeunload', function(e) {
    // Determine whether the blocks content is the same as the last time it was saved.
    if (getCurrentBlkFileContent() == savedBlkFileContent) {
      return undefined;
    }
    // It doesn't matter what string we return here. The browser will always use a standard message
    // for security reasons.
    (e || window.event).returnValue = ''; // Gecko + IE
    return ''; // Gecko + Webkit, Safari, Chrome etc.
  });

  workspace.addChangeListener(function(event) {
    // Check blocks.
    var blockIds = [];
    switch (event.type) {
      case Blockly.Events.FINISHED_LOADING:
        blocksFinishedLoading = true;
        break;
      case Blockly.Events.BLOCK_CREATE:
        Array.prototype.push.apply(blockIds, event.ids);
        break;
      case Blockly.Events.BLOCK_CHANGE:
        if (event.blockId) {
          blockIds.push(event.blockId);
        }
        break;
      case Blockly.Events.BLOCK_DELETE:
        // Remove deleted blocks from blockIdsWithMissingHardware.
        for (var iId = 0, blockId; blockId = event.ids[iId]; iId++) {
          if (blockIdsWithMissingHardware.includes(blockId)) {
            var index = blockIdsWithMissingHardware.indexOf(blockId);
            blockIdsWithMissingHardware.splice(index, 1);
          }
        }
        break;
    }
    for (var i = 0; i < blockIds.length; i++) {
      var block = workspace.getBlockById(blockIds[i]);
      if (block) {
        if (block.type == 'procedures_defnoreturn' &&
            block.getFieldValue('NAME') == 'runOpMode' &&
            !block.getInput('PROJECT_NAME')) {
          // Add the project name to the block.
          block.appendDummyInput('PROJECT_NAME')
               .appendField(createNonEditableField(currentProjectName));
          block.moveInputBefore('PROJECT_NAME', 'STACK');

          if (!block.isEditable()) {
            // This will only happen to old blk files that were created before we made this block
            // editable in all the sample blk files.
            // Make the block editable, so the user can modify the comment.
            // Unfortunately, this will make the blk content different, so we'll think the user has
            // modified the blocks and we'll warn them if they close the tab or navigate.
            block.setEditable(true);
          }
          // Remove the mutator so the user can't add parameters to the runOpMode block.
          if (block.mutator) {
            block.setMutator(null);
            if (block.rendered) {
              block.render();
              block.bumpNeighbours_();
            }
          }
        }

        var hasWarningBits = checkBlock(block, missingHardware);
        if (hasWarningBits & WarningBits.MISSING_HARDWARE) {
          if (!blockIdsWithMissingHardware.includes(blockIds[i])) {
            blockIdsWithMissingHardware.push(blockIds[i]);
          }
        } else {
          if (blockIdsWithMissingHardware.includes(blockIds[i])) {
            var index = blockIdsWithMissingHardware.indexOf(blockIds[i]);
            blockIdsWithMissingHardware.splice(index, 1);
          }
          saveVisibleIdentifiers(block);
        }
      }
    }
    if (blocksFinishedLoading) {
      showJava();
    }
  });
}

function resizeBlocklyArea() {
  // Compute the absolute coordinates and dimensions of blocklyArea.
  var x = 0;
  var y = 0;
  var w = blocklyArea.offsetWidth;
  var h = blocklyArea.offsetHeight;
  var element = blocklyArea;
  do {
    x += element.offsetLeft;
    y += element.offsetTop;
    element = element.offsetParent;
  } while (element);
  // Position blocklyDiv over blocklyArea.
  blocklyDiv.style.left = x + 'px';
  blocklyDiv.style.top = y + 'px';
  blocklyDiv.style.width = w + 'px';
  blocklyDiv.style.height = h + 'px';
  Blockly.svgResize(workspace);
}

function initializeToolbox() {
  workspace.updateToolbox(getToolbox());
  addToolboxIcons(workspace);
  //testAllBlocksInToolbox(workspace);
}

function loadBlocks(blkFileContent, opt_blocksLoaded_callback) {
  // The blocks content is up to and including the first </xml>.
  var i = blkFileContent.indexOf('</xml>');
  var blocksContent = blkFileContent.substring(0, i + 6);

  var extra = parseExtraXml(blkFileContent);
  var flavorSelect = document.getElementById('project_flavor');
  for (var i = 0; i < flavorSelect.options.length; i++) {
    if (flavorSelect.options[i].value == extra['flavor']) {
      flavorSelect.selectedIndex = i;
      break;
    }
  }
  document.getElementById('project_group').value = extra['group'];
  var foundAutoTransition = false;
  if (extra['autoTransition']) {
    var autoTransitionSelect = document.getElementById('project_autoTransition');
    for (var i = 0; i < autoTransitionSelect.options.length; i++) {
      if (autoTransitionSelect.options[i].value == extra['autoTransition']) {
        foundAutoTransition = true;
        autoTransitionSelect.selectedIndex = i;
        break;
      }
    }
  } else {
    foundAutoTransition = true;
  }
  setAutoTransitionDisplay();
  document.getElementById('project_enabled').checked = extra['enabled'];

  if (!foundAutoTransition) {
    alert('The OpMode named "' + extra['autoTransition'] + '" was previously chosen as the ' +
        'Preselect TeleOp. There is currently no TeleOp OpMode with that name.');
  }

  loadBlocksIntoWorkspace(blocksContent, opt_blocksLoaded_callback);
  repositionBlockComments()
  checkDownloadImageFeature();
}

/**
 * Loads the given blocksContent into the workspace.
 */
function loadBlocksIntoWorkspace(blocksContent, opt_blocksLoaded_callback) {
  document.title = titlePrefix + ' - ' + currentProjectName;
  var escapedProjectName = currentProjectName.replace(/&/g, '&amp;');
  document.getElementById('project_name').innerHTML = escapedProjectName;
  missingHardware = [];
  blockIdsWithMissingHardware = [];
  workspace.clear();
  Blockly.Xml.domToWorkspace(Blockly.Xml.textToDom(blocksContent), workspace);

  // Use a timeout to allow the workspace change event to come through. Then, show an alert
  // if any blocks have warnings.
  setTimeout(function() {
    if (blockIdsWithMissingHardware.length > 0) {
      var message = (blockIdsWithMissingHardware.length == 1)
          ? 'An error occured when trying to find the hardware devices for 1 block.\n\n'
          : 'Errors occured when trying to find the hardware devices for ' + blockIdsWithMissingHardware.length +
              ' blocks.\n\n';
      if (missingHardware.length > 0) {
        message += 'The following hardware devices were not found:\n';
        for (var i = 0; i < missingHardware.length; i++) {
          message += '    "' + missingHardware[i] + '"\n';
        }
      }
      message += '\nIf the current configuration is not the appropriate configuration for this ' +
          'blocks project, please activate the appropriate configuration and reload this page.';
      alert(message);
    }

    if (opt_blocksLoaded_callback) {
      opt_blocksLoaded_callback();
    }
  }, 50);
}

function repositionBlockComments() {
  var allBlocks = workspace.getAllBlocks();
  for (var iBlock = 0, block; block = allBlocks[iBlock]; iBlock++) {
    if (block.comment && block.comment.bubble_) {
      restoreBlockCommentPosition(block, block.comment.bubble_);
    }
  }
}


/**
 * Add/remove the block warning if the given block's identifier(s) are not in the active
 * configuration. Return true if the block now has a warning, false otherwise.
 */
function checkBlock(block, missingHardware) {
  var warningBits = 0;
  try {
    var warningText = null; // null will remove any previous warning.
    for (var iFieldName = 0; iFieldName < identifierFieldNames.length; iFieldName++) {
      var identifierFieldName = identifierFieldNames[iFieldName];
      var field = block.getField(identifierFieldName);
      if (field) {
        var identifierFieldValue = field.getValue();
        var identifierFound = true;
        var fieldHasOptions = false;

        if (typeof field.getOptions === 'function') {
          // The identifier field is a dropdown field with options.
          // Check if identifierFieldValue is the value of one of the options.
          var options = field.getOptions();
          fieldHasOptions = true;
          identifierFound = false;
          for (var iOption = 0; iOption < options.length; iOption++) {
            if (options[iOption][1] == identifierFieldValue) {
              identifierFound = true;
              break;
            }
          }
          if (!identifierFound) {
            // Check if identifierFieldValue is the visible name of one of the options.
            for (var iOption = 0; iOption < options.length; iOption++) {
              if (options[iOption][0] == identifierFieldValue) {
                identifierFieldValue = options[iOption][1];
                field.setValue(identifierFieldValue);
                identifierFound = true;
                break;
              }
            }
          }
        } else {
          // The identifier field is a noneditable field instead of a dropdown field.
          // Since blockly doesn't allow a dropdown field to have zero options, we need to use a
          // noneditable field (which looks similar) when there are no hardware items for this
          // type of block.
          identifierFound = false;
        }

        if (!identifierFound) {
          // identifierFieldValue is the name of the identifier that will be used in generated
          // javascript, but it is not necessarily the same as the name that is used in the
          // hardware configuration and should be displayed on the block. For example, the
          // visible identifier name may contain characters that are not allowed in a javascript
          // identifier. Also, the identifier may have a suffix that allows us to distinguish
          // between hardware devices with the same configuration name or hardware devices that
          // support multiple hardware interfaces.
          // We now store the visible identifier names using block.data.
          // Here, we try to use block.data to retrieve the visible identifier name.
          var visibleIdentifierName;
          if (block.data) {
            if (block.data.startsWith('{')) {
              var data = parseBlockDataJSON(block);
              visibleIdentifierName = data[identifierFieldName];
            } else {
              // Some older versions save as plain text instead of JSON.
              visibleIdentifierName = block.data;
            }
          } else {
            // If the blocks file is even older, we don't know what the visible name actually is.
            // The best we can do is to remove the hardware identifier suffix if there is one.
            visibleIdentifierName = removeHardwareIdentifierSuffix(identifierFieldValue);
          }
          if (typeof visibleIdentifierName === 'string') {
            if (typeof field.setText === 'function') {
              field.setText(visibleIdentifierName);
            }
            if (!missingHardware.includes(visibleIdentifierName)) {
              missingHardware.push(visibleIdentifierName);
            }
            warningBits |= WarningBits.MISSING_HARDWARE;
            if (fieldHasOptions) {
              warningText = addWarning(warningText,
                  '"' + visibleIdentifierName + '" is not in the current robot configuration.\n\n' +
                  'Please activate the configuration that contains the hardware device named "' +
                  visibleIdentifierName + '",\nor select a device that is in the current robot configuration.');
            } else {
              warningText = addWarning(warningText,
                  '"' + visibleIdentifierName + '" is not in the current robot configuration.\n\n' +
                  'Please activate the configuration that contains the hardware device named "' +
                  visibleIdentifierName + '".');
            }
          }
        }
      }
    }
    if (isObsolete(block)) {
      warningBits |= WarningBits.OBSOLETE;
      warningText = addWarning(warningText,
          'This block is obsolete and will not work correctly now.');
    } else if (wasForRelicRecovery(block)) {
      warningBits |= WarningBits.RELIC_RECOVERY;
      warningText = addWarning(warningText,
          'This block was for RelicRecovery (2017-2018) and will not work correctly now.');
    } else if (wasForRoverRuckus(block)) {
      warningBits |= WarningBits.ROVER_RUCKUS;
      warningText = addWarning(warningText,
          'This block was for RoverRuckus (2018-2019) and will not work correctly now.');
    } else if (wasForSkyStone(block)) {
      warningBits |= WarningBits.SKY_STONE;
      warningText = addWarning(warningText,
          'This block was for SkyStone (2019-2020) and will not work correctly now.');
    } else if (block.type == 'misc_callJava_return' ||
        block.type == 'misc_callJava_noReturn' ||
        block.type == 'misc_callHardware_return' ||
        block.type == 'misc_callHardware_noReturn') {
      if (!methodLookupStrings.includes(block.ftcAttributes_.methodLookupString)) {
        warningBits |= WarningBits.MISSING_METHOD;
        warningText = addWarning(warningText,
            'This block refers to a Java method that has been changed or removed. It will not ' +
            'work correctly.\n\n' +
            'Please replace or remove this block, or restore the Java method it refers to.');
      }
    }

    // If warningText is null, the following will clear a previous warning.
    var previousWarningText = block.warning ? block.warning.getText() : null;
    if (previousWarningText != warningText) {
      block.setWarningText(warningText);
      if (warningText && block.warning && !readBlockWarningHidden(block)) {
        block.warning.setVisible(true);
      }
    }
  } catch (e) {
    console.log('Unable to verify the following block due to exception.');
    console.log(block);
    console.log(e);
  }
  return warningBits;
}

function addWarning(warningText, textToAdd) {
  if (warningText == null) {
    warningText = '';
  } else {
    warningText += '\n\n';
  }
  warningText += textToAdd;
  return warningText;
}

function removeHardwareIdentifierSuffix(identifierFieldValue) {
  var suffixes = getHardwareIdentifierSuffixes();
  for (var i = 0; i < suffixes.length; i++) {
    var suffix = suffixes[i];
    if (identifierFieldValue.endsWith(suffix)) {
      identifierFieldValue =
          identifierFieldValue.substring(0, identifierFieldValue.length - suffix.length);
      break;
    }
  }
  return identifierFieldValue;
}

function parseBlockDataJSON(block) {
  if (block.data && block.data.startsWith('{')) {
    try {
      return JSON.parse(block.data);
    } catch (err) {
    }
  }
  return null;
}

function stringifyBlockDataJSON(block, data) {
  block.data = data ? JSON.stringify(data) : null;
  if (block.data == '{}') {
    block.data = null;
  }
}

function saveBlockWarningHidden(block) {
  var data = parseBlockDataJSON(block);
  if (block.warning) {
    if (!block.warning.isVisible()) {
      if (!data) {
        data = Object.create(null);
      }
      data.block_warning_hidden = true;
    } else {
      if (data) {
        delete data.block_warning_hidden;
      }
    }
  }

  stringifyBlockDataJSON(block, data);
}

function readBlockWarningHidden(block) {
  var data = parseBlockDataJSON(block);
  if (data) {
    if (data.block_warning_hidden) {
      return true;
    }
  }

  return false;
}

// NOTE(lizlooney): If/when we update to a newer version of blockly, we need to check that thie
// following still works.
function saveBlockCommentPosition(block, bubble) {
  var data = parseBlockDataJSON(block);
  if (!data) {
    data = Object.create(null);
  }
  data.commentPositionLeft = Math.round(bubble.relativeLeft_);
  data.commentPositionTop = Math.round(bubble.relativeTop_);
  stringifyBlockDataJSON(block, data);
}

function clearBlockCommentPosition(block) {
  var data = parseBlockDataJSON(block);
  if (data) {
    delete data.commentPositionLeft;
    delete data.commentPositionTop;
    stringifyBlockDataJSON(block, data);
  }
}

// NOTE(lizlooney): If/when we update to a newer version of blockly, we need to check that thie
// following still works.
function restoreBlockCommentPosition(block, bubble) {
  var data = parseBlockDataJSON(block);
  if (data && typeof data.commentPositionLeft == "number" && typeof data.commentPositionTop == "number") {
    if (bubble.relativeLeft_ != data.commentPositionLeft || bubble.relativeTop_ != data.commentPositionTop) {
      bubble.relativeLeft_ = data.commentPositionLeft
      bubble.relativeTop_ = data.commentPositionTop
      bubble.positionBubble_();
      bubble.renderArrow_();
    }
  }
}

function saveVisibleIdentifiers(block) {
  var data = parseBlockDataJSON(block);

  for (var iFieldName = 0; iFieldName < identifierFieldNames.length; iFieldName++) {
    var identifierFieldName = identifierFieldNames[iFieldName];
    var field = block.getField(identifierFieldName);
    if (field) {
      if (typeof field.getOptions === 'function') {
        // The identifier field is a dropdown field with options.
        // Save the user visible identifiers using block.data, so we can use it in the future if
        // the hardware device is not found in the configuration.
        if (!data) {
          data = Object.create(null);
        }
        // field.getText() returns the text with spaces changed to non-breakable spaces \u00A0.
        // Here we change them back to normal spaces. Without this, the character becomes &nbsp; in
        // the blk file when saved with Safari.
        data[identifierFieldName] = field.getText().replace(/\u00A0/g, ' ');
      }
    }
  }

  stringifyBlockDataJSON(block, data);
}

function onMouseMove(e) {
  mouseX = e.clientX;
  mouseY = e.clientY;
}

function onKeyDown(e) {
  if (Blockly.mainWorkspace.options.readOnly || Blockly.utils.isTargetInput(e)) {
    // No key actions on readonly workspaces.
    // When focused on an HTML text input widget, don't trap any keys.
    return;
  }
  if (e.altKey || e.ctrlKey || e.metaKey) {
    if (Blockly.selected &&
        Blockly.selected.isDeletable() && Blockly.selected.isMovable()) {
      if (e.keyCode == 67 || // 'c' for copy.
          e.keyCode == 88) { // 'x' for cut.
        // Use a timeout so we can capture the blockly clipboard.
        setTimeout(function() {
          captureBlocklyClipboard();
        }, 1);
      }
    }

    if (e.keyCode == 86) { // 'v' for paste.
      // Override blockly's default paste behavior.
      paste();
      e.stopImmediatePropagation();
    }
  }
}

function captureBlocklyClipboard() {
  if (Blockly.clipboardXml_) {
    if (previousClipboardXml != Blockly.clipboardXml_) {
      previousClipboardXml = Blockly.clipboardXml_;

      // Convert to text.
      var xml = goog.dom.createDom('xml');
      xml.appendChild(Blockly.clipboardXml_);
      var serializer = new XMLSerializer();
      var clipboardContent = serializer.serializeToString(xml);
      xml.removeChild(Blockly.clipboardXml_);

      afterBlocklyClipboardCaptured(clipboardContent);
    }
  }
}

function pasteContent(clipboardContent) {
  if (!clipboardContent) {
    return;
  }
  var parser = new DOMParser();
  var xmlDoc = parser.parseFromString(clipboardContent, 'text/xml');
  var blocks = xmlDoc.getElementsByTagName('block');
  if (blocks.length > 0) {
    var block = blocks[0];
    // Place the pasted block near mouse.
    var svg = workspace.getParentSvg();
    var point = svg.createSVGPoint();
    point.x = mouseX;
    point.y = mouseY;
    point = point.matrixTransform(svg.getScreenCTM().inverse());
    point = point.matrixTransform(workspace.getCanvas().getCTM().inverse());
    block.setAttribute('x', point.x);
    block.setAttribute('y', point.y);
    workspace.paste(block);
  }
}

function downloadBlocks(blkFileContent) {
  var a = document.getElementById('download_link');
  a.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(blkFileContent);
  a.download = currentProjectName + '.blk';
  a.click();
}

function checkDownloadImageFeature() {
  // Show and enable the download image button if workspace.svgBlockCanvas_ and canvas.toBlob is
  // defined.
  if (workspace.svgBlockCanvas_ !== undefined) {
    var canvasElement = document.createElement('canvas');
    if (canvasElement.toBlob !== undefined) {
      var downloadImageButton = document.getElementById('downloadImageButton');
      downloadImageButton.disabled = false;
      downloadImageButton.style.display = 'inline';
    }
  }
}

function downloadImageButtonClicked() {
  // Clone the workspace' svg canvas.
  var svgCanvas = workspace.svgBlockCanvas_;
  var clone = svgCanvas.cloneNode(true);
  var box;
  if (svgCanvas.tagName == 'svg') {
    box = svgCanvas.getBoundingClientRect();
    var metrics = workspace.getMetrics();
    var left = (parseFloat(metrics.contentLeft) - parseFloat(metrics.viewLeft)).toString();
    var top = (parseFloat(metrics.contentTop) - parseFloat(metrics.viewTop)).toString();
    var right = (parseFloat(metrics.contentWidth)).toString();
    var bottom = (parseFloat(metrics.contentHeight)).toString();
    clone.setAttribute('viewBox', left + ' ' + top + ' ' + right + ' ' + bottom);
  } else {
    clone.setAttribute('transform',
        clone.getAttribute('transform')
            .replace(/translate\(.*?\)/, '')
            .replace(/scale\(.*?\)/, '')
            .trim());
    var svg = document.createElementNS('http://www.w3.org/2000/svg','svg')
    svg.setAttribute('xmlns', 'http://www.w3.org/2000/svg');
    svg.appendChild(clone)
    clone = svg;
    box = svgCanvas.getBBox();
    clone.setAttribute('viewBox', box.x + ' ' + box.y + ' ' + box.width + ' ' + box.height);
  }
  clone.setAttribute('version', '1.1');
  clone.setAttribute('width', box.width);
  clone.setAttribute('height', box.height);
  clone.setAttribute('style', 'background-color: #FFFFFF');
  var divElement = document.createElement('div');
  divElement.appendChild(clone);

  // Collect style sheets.
  var css = '';
  var sheets = document.styleSheets;
  for (var i = 0; i < sheets.length; i++) {
    if (isExternal(sheets[i].href)) {
      continue;
    }
    var rules = null;
    try {
      rules = sheets[i].cssRules;
    } catch (err) {
      // Cannot access cssRules from external css files in offline blocks editor.
    }
    if (rules != null) {
      for (var j = 0; j < rules.length; j++) {
        var rule = rules[j];
        if (typeof(rule.style) != 'undefined') {
          var match = null;
          try {
            match = svgCanvas.querySelector(rule.selectorText);
          } catch (err) {
          }
          if (match && rule.selectorText.indexOf('blocklySelected') == -1) {
            css += rule.selectorText + ' { ' + rule.style.cssText + ' }\n';
          } else if(rule.cssText.match(/^@font-face/)) {
            css += rule.cssText + '\n';
          }
        }
      }
    }
  }
  var styleElement = document.createElement('style');
  styleElement.setAttribute('type', 'text/css');
  styleElement.innerHTML = '<![CDATA[\n' + css + '\n]]>';
  var defsElement = document.createElement('defs');
  defsElement.appendChild(styleElement);
  clone.insertBefore(defsElement, clone.firstChild);

  // TODO(lizlooney): hide all blocklyScrollbarHandle, blocklyScrollbarBackground, image,
  // .blocklyMainBackground, rectCorner, indicatorWarning?

  var doctype = '<?xml version="1.0" standalone="no"?>' +
      '<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" ' +
      '"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">';
  var svg = doctype + divElement.innerHTML;
  svg = svg.replace(/&nbsp/g, '&#160');
  svg = svg.replace(/sans-serif/g, 'Arial, Verdana, "Nimbus Sans L", Helvetica');
  var uri = 'data:image/svg+xml;base64,' + window.btoa(unescape(encodeURIComponent(svg)));

  var image = new Image();
  image.onload = function() {
    var canvasElement = document.createElement('canvas');
    canvasElement.width = image.width;
    canvasElement.height = image.height;
    canvasElement.getContext('2d').drawImage(image, 0, 0);
    canvasElement.toBlob(function(blob) {
      var a = document.getElementById('download_link');
      a.href = URL.createObjectURL(blob);
      a.download = currentProjectName + '.png';
      a.click();
    });
  };
  image.onerror = function (e) {
    alert('Unable to download blocks image. Sorry about that!');
  };
  image.src = uri;
}

function isExternal(url) {
  return url && url.lastIndexOf('http', 0) == 0 && url.lastIndexOf(window.location.host) == -1;
}

function projectFlavorChanged() {
  setAutoTransitionDisplay();
  showJava();
}

function setAutoTransitionDisplay() {
  var flavorSelect = document.getElementById('project_flavor');
  var flavor = flavorSelect.options[flavorSelect.selectedIndex].value;
  var display = (flavor == 'AUTONOMOUS') ? 'inline-block' : 'none';
  document.getElementById('project_autoTransition_label').style.display = display;
  document.getElementById('project_autoTransition').style.display = display;
}

function projectGroupChanged() {
  showJava();
}

function projectAutoTransitionChanged() {
  showJava();
}

function showJavaChanged() {
  if (document.getElementById('show_java').checked) {
    javaArea.style.display = 'flex';
    initializeSplit();
  } else {
    if (split) split.destroy();
    blocksAndBannerArea.style.width="100%";
    javaArea.style.display = 'none';
  }
  resizeBlocklyArea();
  showJava();
}

function showJava() {
  if (document.getElementById('show_java').checked) {
    var javaCode = generateJavaCode();
    if (javaCode) {
      javaContent.textContent = javaCode;
      javaContent.style.color = 'black';
      return;
    }
    javaContent.style.color = 'gray';
  }
}

function generateJavaCode() {
  // Get the blocks as xml (text).
  var blocksContent = Blockly.Xml.domToText(Blockly.Xml.workspaceToDom(workspace));
  // Don't bother exporting if there are no blocks.
  if (blocksContent.indexOf('<block') > -1) {
    if (currentClassName) {
      // Generate Java code.
      return Blockly.FtcJava.workspaceToCode(workspace);
    }
  }
  return '';
}
