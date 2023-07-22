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
 * @fileoverview Toolbox utilities.
 * @author lizlooney@google.com (Liz Looney)
 */

function addToolboxIcons(workspace) {
  addToolboxIconsForChildren(workspace.toolbox_.tree_.getChildren());
}

function addToolboxIconsForChildren(children) {
  for (var i = 0, child; child = children[i]; i++) {
    if (child.getChildCount() > 0) {
      addToolboxIconsForChildren(child.getChildren());
    } else {
      var iconClass = getIconClass(child.getText());
      if (iconClass) {
        child.setIconClass('toolbox-node-icon ' + iconClass);
      }
    }
  }
}

function testAllBlocksInToolbox(workspace) {
  alert('Press OK to run tests on all blocks in the toolbox.');
  Blockly.JavaScript.init(workspace);
  Blockly.FtcJava.init(workspace);
  testBlocksForChildren(workspace, workspace.toolbox_.tree_.getChildren());
  alert('Completed tests on all blocks in the toolbox.');
}

function testBlocksForChildren(workspaceForCreatingBlocks, children) {
  for (var i = 0, child; child = children[i]; i++) {
    if (child.getChildCount() > 0) {
      testBlocksForChildren(workspaceForCreatingBlocks, child.getChildren());
    }
    if (child.blocks && child.blocks.length) {
      for (var j = 0, xmlChild; xmlChild = child.blocks[j]; j++) {
        if (typeof xmlChild == 'object') {
          var block = Blockly.Xml.domToBlock(xmlChild, workspaceForCreatingBlocks);
          testBlockJavaScript(block);
          testBlockFtcJava(block);
        }
      }
    }
  }
}

function testBlockJavaScript(block) {
  var code = null;
  try {
    code = Blockly.JavaScript.blockToCode(block);
  } catch (e) {
    console.log("Error - " + e + " - Unable to test block..."); console.log(xmlChild);
  }
  if (block.outputConnection) {
    if (!Array.isArray(code)) {
      console.log("Error - Blockly.JavaScript['" + block.type + "'] is generating a " + (typeof code) + ", but should generate an array");
    }
  } else {
    if (typeof code != "string") {
      console.log("Error - Blockly.JavaScript['" + block.type + "'] is generating a " + (typeof code) + ", but should generate an string");
    }
  }
}


function testBlockFtcJava(block) {
  var code = null;
  try {
    code = Blockly.FtcJava.blockToCode(block);
  } catch (e) {
    console.log("Error - " + e + " - Unable to test block..."); console.log(xmlChild);
  }
  if (block.outputConnection) {
    if (!Array.isArray(code)) {
      console.log("Error - Blockly.FtcJava['" + block.type + "'] is generating a " + (typeof code) + ", but should generate an array");
    }
  } else {
    if (typeof code != "string") {
      console.log("Error - Blockly.FtcJava['" + block.type + "'] is generating a " + (typeof code) + ", but should generate an string");
    }
  }
}
