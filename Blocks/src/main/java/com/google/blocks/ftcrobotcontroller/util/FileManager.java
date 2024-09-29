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

package com.google.blocks.ftcrobotcontroller.util;

import static org.firstinspires.ftc.robotcore.internal.system.AppUtil.BLOCKS_SOUNDS_DIR;
import static com.google.blocks.ftcrobotcontroller.util.ProjectsUtil.escapeDoubleQuotes;

import android.text.Html;
import android.util.Base64;
import java.io.File;
import java.io.IOException;

/**
 * A enum that provides utility methods related to managing files in a directory.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public enum FileManager {
  SOUNDS(BLOCKS_SOUNDS_DIR);

  private final String VALID_NAME_REGEX =
      "^[a-zA-Z0-9 \\!\\#\\$\\%\\&\\'\\(\\)\\+\\,\\-\\.\\;\\=\\@\\[\\]\\^_\\{\\}\\~]+$";
  private final File dir;

  FileManager(File dir) {
    this.dir = dir;
  }

  public String fetchJavaScript() throws IOException {
    return new StringBuilder()
        .append("\n")
        .append("function isValidName(name) {\n")
        .append("  if (name) {\n")
        .append("    return /").append(VALID_NAME_REGEX).append("/.test(name);\n")
        .append("  }\n")
        .append("  return false;\n")
        .append("}\n\n")
        .toString();
  }

  /**
   * Returns the names of existing files.
   */
  public String fetchFiles() throws IOException {
    File[] files = dir.listFiles();
    if (files != null) {
      StringBuilder json = new StringBuilder();
      json.append("[");
      String delimiter = "";
      for (int i = 0; i < files.length; i++) {
        String name = files[i].getName();
        json.append(delimiter)
            .append("{")
            .append("\"name\":\"").append(escapeDoubleQuotes(name)).append("\", ")
            .append("\"escapedName\":\"").append(escapeDoubleQuotes(Html.escapeHtml(name))).append("\", ")
            .append("\"dateModifiedMillis\":").append(files[i].lastModified())
            .append("}");
        delimiter = ",";
      }
      json.append("]");
      return json.toString();
    }
    return "[]";
  }

  /**
   * Returns true if the given name is not null and contains only valid characters.
   * This function does not check whether the file with the given name exists.
   */
  public boolean isValidName(String name) {
    if (name != null) {
      return name.matches(VALID_NAME_REGEX);
    }
    return false;
  }

  /**
   * Returns the content of the file with the given name.
   *
   * @param name the name of the file
   */
  public String fetchFileContent(String name) throws IOException {
    if (!isValidName(name)) {
      throw new IllegalArgumentException();
    }
    byte[] content = FileUtil.readBinaryFile(new File(dir, name));
    return Base64.encodeToString(content, Base64.DEFAULT);
  }

  /**
   * Save a file.
   *
   * @param name the name of the file to save
   * @param base64Content the content to write to the file, encoded with base64.
   */
  public void saveFile(String name, String base64Content) throws IOException {
    if (!isValidName(name)) {
      throw new IllegalArgumentException();
    }
    if (!dir.exists()) {
      dir.mkdirs();
    }
    byte[] content = Base64.decode(base64Content, Base64.DEFAULT);
    File file = new File(dir, name);
    File tempBackupFile = null;
    if (file.exists()) {
      // Before writing the new content to the file, make a temporary copy of the old file,
      // just in case the control hub is unplugged (or the Android's battery dies) while we are
      // writing a file. We don't want the user to be left with the file empty/corrupt and the
      // old and new content both lost.
      long timestamp = System.currentTimeMillis();
      tempBackupFile = new File(dir, "backup_" + timestamp + "_" + name);
      FileUtil.copyFile(file, tempBackupFile);
    }
    FileUtil.writeBinaryFile(file, content);
    // Once we've written the new content to the file, we can delete the temporary copy of
    // the old file.
    if (tempBackupFile != null) {
      tempBackupFile.delete();
    }
  }

  /**
   * Renames the file with the given name.
   *
   * @param oldName the old name of the file
   * @param newName the new name of the file
   */
  public void renameFile(String oldName, String newName)
      throws IOException {
    if (!isValidName(oldName) || !isValidName(newName)) {
      throw new IllegalArgumentException();
    }
    if (!dir.exists()) {
      dir.mkdirs();
    }
    File oldFile = new File(dir, oldName);
    File newFile = new File(dir, newName);
    oldFile.renameTo(newFile);
  }

  /**
   * Copies the file with the given name.
   *
   * @param oldName the old name of the file
   * @param newName the new name of the file
   */
  public void copyFile(String oldName, String newName)
      throws IOException {
    if (!isValidName(oldName) || !isValidName(newName)) {
      throw new IllegalArgumentException();
    }
    if (!dir.exists()) {
      dir.mkdirs();
    }

    File oldFile = new File(dir, oldName);
    File newFile = new File(dir, newName);
    FileUtil.copyFile(oldFile, newFile);
  }

  /**
   * Delete the files with the given names.
   *
   * @param names the names of the files to delete
   */
  public boolean deleteFiles(String[] names) {
    for (String name : names) {
      if (!isValidName(name)) {
        throw new IllegalArgumentException();
      }
    }
    boolean success = true;
    for (String name : names) {
      File file = new File(dir, name);
      if (file.exists()) {
        if (!file.delete()) {
          success = false;
        }
      }
    }
    return success;
  }

  public String getPathForFile(String name) {
    return new File(dir, name).getAbsolutePath();
  }
}
