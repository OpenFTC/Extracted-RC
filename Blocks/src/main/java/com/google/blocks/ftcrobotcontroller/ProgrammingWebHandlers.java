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

package com.google.blocks.ftcrobotcontroller;

import com.google.blocks.ftcrobotcontroller.util.ClipboardUtil;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareUtil;
import com.google.blocks.ftcrobotcontroller.util.BlocksArchive;
import com.google.blocks.ftcrobotcontroller.util.OfflineBlocksUtil;
import com.google.blocks.ftcrobotcontroller.util.ProjectsUtil;
import com.google.blocks.ftcrobotcontroller.util.FileManager;
import com.qualcomm.ftccommon.CommandList;
import com.qualcomm.robotcore.robocol.Command;


import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;
import org.firstinspires.ftc.robotserver.internal.programmingmode.ProgrammingMode;
import org.firstinspires.ftc.robotserver.internal.programmingmode.ProgrammingModeManager;
import org.firstinspires.ftc.robotcore.internal.network.NetworkConnectionHandler;
import org.firstinspires.ftc.robotserver.internal.webserver.MimeTypesUtil;
import org.firstinspires.ftc.robotserver.internal.webserver.NoCachingWebHandler;
import org.firstinspires.ftc.robotserver.internal.webserver.RobotControllerWebHandlers;
import org.firstinspires.ftc.robotserver.internal.webserver.RobotWebHandlerManager;
import org.firstinspires.ftc.robotserver.internal.webserver.SessionParametersGenerator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;
import static fi.iki.elonen.NanoHTTPD.newChunkedResponse;

/**
 * A class that provides handlers for all HTTP requests related to programming
 *
 * @author lizlooney@google.com (Liz Looney)
 */
@SuppressWarnings("WeakerAccess")
public class ProgrammingWebHandlers implements ProgrammingMode {

  public static final String TAG = ProgrammingWebHandlers.class.getSimpleName();
  @SuppressWarnings("FieldCanBeLocal")
  private static String URI_NAV_BLOCKS_OLD = "/FtcProjects.html";
  @SuppressWarnings("FieldCanBeLocal")
  private static String URI_NAV_BLOCKS = "/FtcBlocksProjects.html";
  @SuppressWarnings("FieldCanBeLocal")
  private static String URI_NAV_ONBOTJAVA = "/java/editor.html";

  private static final String URI_SERVER = "/server";
  private static final String URI_HARDWARE = "/hardware";
  private static final String URI_FILE_MANAGER_JS = "/file_manager_js";
  private static final String URI_GET_CONFIGURATION_NAME = "/get_config_name";
  private static final String URI_FETCH_OFFLINE_BLOCKS_EDITOR = "/offline_blocks_editor";
  private static final String URI_FETCH_BLOCKS_ARCHIVE = "/archive_blocks";
  private static final String URI_LIST_PROJECTS = "/list";
  private static final String URI_LIST_SAMPLES = "/samples";
  private static final String URI_FETCH_BLK = "/fetch_blk";
  private static final String URI_NEW_PROJECT = "/new";
  private static final String URI_SAVE_PROJECT = "/save";
  private static final String URI_RENAME_PROJECT = "/rename";
  private static final String URI_COPY_PROJECT = "/copy";
  private static final String URI_ENABLE_PROJECT = "/enable";
  private static final String URI_DELETE_PROJECTS = "/delete";
  private static final String URI_GET_BLOCKS_JAVA_CLASS_NAME = "/get_blocks_java_class_name";
  private static final String URI_SAVE_BLOCKS_JAVA = "/save_blocks_java";
  private static final String URI_SAVE_CLIPBOARD = "/savecb";
  private static final String URI_FETCH_CLIPBOARD = "/fetch_cb";
  private static final String URI_LIST_FILES = "/list_files";
  private static final String URI_SAVE_FILE = "/save_file";
  private static final String URI_FETCH_FILE = "/fetch_file";
  private static final String URI_FETCH_FILE_TYPE = "/fetch_file_type";
  private static final String URI_RENAME_FILE = "/rename_file";
  private static final String URI_COPY_FILE = "/copy_file";
  private static final String URI_DELETE_FILES = "/delete_files";
  private static final String URI_RESTART_ROBOT = "/restart_robot";
  private static final String URI_COLORS = RobotControllerWebHandlers.URI_COLORS;
  private static final String PARAM_FM_NAME = "fmname";
  private static final String PARAM_NAME = RobotControllerWebHandlers.PARAM_NAME;
  private static final String PARAM_NEW_NAME = RobotControllerWebHandlers.PARAM_NEW_NAME;
  private static final String PARAM_SAMPLE_NAME = "sample";
  private static final String PARAM_BLK = "blk";
  private static final String PARAM_JS = "js";
  private static final String PARAM_JAVA = "java";
  private static final String PARAM_ENABLE = "enable";
  private static final String PARAM_CLIPBOARD = "cb";
  private static final String PARAM_CONTENT = "content";

  ///////////////////////////////////////////////////////////////////////////////////////////////
  // WebHandler implementations
  ///////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Fetch the javaScript for the server.
   */
  private static class Server implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchJavaScriptForServer(session);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private Response fetchJavaScriptForServer(NanoHTTPD.IHTTPSession session) throws IOException {
      StringBuilder js = new StringBuilder();
      js.append("var URI_HARDWARE = '").append(URI_HARDWARE).append("';\n");
      js.append("var URI_FILE_MANAGER_JS = '").append(URI_FILE_MANAGER_JS).append("';\n");
      js.append("var URI_GET_CONFIGURATION_NAME = '").append(URI_GET_CONFIGURATION_NAME).append("';\n");
      js.append("var URI_FETCH_OFFLINE_BLOCKS_EDITOR = '").append(URI_FETCH_OFFLINE_BLOCKS_EDITOR).append("';\n");
      js.append("var URI_FETCH_BLOCKS_ARCHIVE = '").append(URI_FETCH_BLOCKS_ARCHIVE).append("';\n");
      js.append("var URI_LIST_PROJECTS = '").append(URI_LIST_PROJECTS).append("';\n");
      js.append("var URI_LIST_SAMPLES = '").append(URI_LIST_SAMPLES).append("';\n");
      js.append("var URI_FETCH_BLK = '").append(URI_FETCH_BLK).append("';\n");
      js.append("var URI_NEW_PROJECT = '").append(URI_NEW_PROJECT).append("';\n");
      js.append("var URI_SAVE_PROJECT = '").append(URI_SAVE_PROJECT).append("';\n");
      js.append("var URI_RENAME_PROJECT = '").append(URI_RENAME_PROJECT).append("';\n");
      js.append("var URI_COPY_PROJECT = '").append(URI_COPY_PROJECT).append("';\n");
      js.append("var URI_ENABLE_PROJECT = '").append(URI_ENABLE_PROJECT).append("';\n");
      js.append("var URI_DELETE_PROJECTS = '").append(URI_DELETE_PROJECTS).append("';\n");
      js.append("var URI_GET_BLOCKS_JAVA_CLASS_NAME = '").append(URI_GET_BLOCKS_JAVA_CLASS_NAME).append("';\n");
      js.append("var URI_SAVE_BLOCKS_JAVA = '").append(URI_SAVE_BLOCKS_JAVA).append("';\n");
      js.append("var URI_SAVE_CLIPBOARD = '").append(URI_SAVE_CLIPBOARD).append("';\n");
      js.append("var URI_FETCH_CLIPBOARD = '").append(URI_FETCH_CLIPBOARD).append("';\n");
      js.append("var URI_LIST_FILES = '").append(URI_LIST_FILES).append("';\n");
      js.append("var URI_SAVE_FILE = '").append(URI_SAVE_FILE).append("';\n");
      js.append("var URI_FETCH_FILE = '").append(URI_FETCH_FILE).append("';\n");
      js.append("var URI_FETCH_FILE_TYPE = '").append(URI_FETCH_FILE_TYPE).append("';\n");
      js.append("var URI_RENAME_FILE = '").append(URI_RENAME_FILE).append("';\n");
      js.append("var URI_COPY_FILE = '").append(URI_COPY_FILE).append("';\n");
      js.append("var URI_DELETE_FILES = '").append(URI_DELETE_FILES).append("';\n");
      js.append("var URI_RESTART_ROBOT = '").append(URI_RESTART_ROBOT).append("';\n");
      js.append("var PARAM_FM_NAME = '").append(PARAM_FM_NAME).append("';\n");
      js.append("var PARAM_NAME = '").append(PARAM_NAME).append("';\n");
      js.append("var PARAM_NEW_NAME = '").append(PARAM_NEW_NAME).append("';\n");
      js.append("var PARAM_SAMPLE_NAME = '").append(PARAM_SAMPLE_NAME).append("';\n");
      js.append("var PARAM_BLK = '").append(PARAM_BLK).append("';\n");
      js.append("var PARAM_JS = '").append(PARAM_JS).append("';\n");
      js.append("var PARAM_JAVA = '").append(PARAM_JAVA).append("';\n");
      js.append("var PARAM_ENABLE = '").append(PARAM_ENABLE).append("';\n");
      js.append("var PARAM_CLIPBOARD = '").append(PARAM_CLIPBOARD).append("';\n");
      js.append("var PARAM_CONTENT = '").append(PARAM_CONTENT).append("';\n");
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, "application/javascript", js.toString()));
    }
  }

  private static class RobotControllerConfiguration extends RobotControllerWebHandlers.RobotControllerConfiguration {
    @Override protected void appendVariables(StringBuilder js) {
      super.appendVariables(js);
      appendVariable(js, "URI_NAV_BLOCKS", URI_NAV_BLOCKS);
      appendVariable(js, "URI_NAV_ONBOTJAVA", URI_NAV_ONBOTJAVA);
    }
  }

  /**
   * Fetches the names of projects.
   */
  private static class ListProjects implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchProjects(session);
    }

    private Response fetchProjects(NanoHTTPD.IHTTPSession session) throws IOException {
      String jsonProjects = ProjectsUtil.fetchProjectsWithBlocks();
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, jsonProjects));
    }
  }

  /**
   * Fetches the names of samples.
   */
  private static class ListSamples implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchSamples(session);
    }

    private Response fetchSamples(NanoHTTPD.IHTTPSession session) throws IOException {
      String jsonSamples = ProjectsUtil.fetchSampleNames();
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, jsonSamples));
    }
  }

  /**
   * Fetches the JavaScript code related to the hardware in the active configuration.
   */
  private static class Hardware implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchJavaScriptForHardware(session);
    }

    private Response fetchJavaScriptForHardware(NanoHTTPD.IHTTPSession session) throws IOException {
      String jsHardware = HardwareUtil.fetchJavaScriptForHardware();
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, "application/javascript", jsHardware));
    }
  }

  /**
   * Gets the name of the active configuration.
   */
  private static class GetConfigurationName implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return getConfigurationName(session);
    }

    private Response getConfigurationName(NanoHTTPD.IHTTPSession session) throws IOException {
      String configName = HardwareUtil.getConfigurationName();
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, configName));
    }
  }

  /**
   * Fetches the offline blocks editor, with toolbox appropriate for the hardware in the active
   * configuration.
   */
  private static class FetchOfflineBlocksEditor implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchOfflineBlocksEditor(session);
    }

    private Response fetchOfflineBlocksEditor(NanoHTTPD.IHTTPSession session) throws IOException {
      return NoCachingWebHandler.setNoCache(session,
          newChunkedResponse(Response.Status.OK, "application/zip", OfflineBlocksUtil.fetchOfflineBlocksEditor()));
    }
  }

  /**
   * Fetches a new archive of all the Blocks OpModes.
   */
  private static class FetchBlocksArchive implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchBlocksArchive(session);
    }

    private Response fetchBlocksArchive(NanoHTTPD.IHTTPSession session) throws IOException {
      return NoCachingWebHandler.setNoCache(session,
          newChunkedResponse(Response.Status.OK, "application/zip", BlocksArchive.fetchBlocksArchive()));
    }
  }

  /**
   * Fetches the content of the blocks file for the given project.
   */
  private static class FetchBlockFile implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
     String name = getFirstNamedParameter(session, PARAM_NAME);
      if (name != null) {
        return fetchBlkFileContent(session, name);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " parameter is required");
      }
    }

    private Response fetchBlkFileContent(NanoHTTPD.IHTTPSession session, String projectName) throws IOException {
      String blkFileContent = ProjectsUtil.fetchBlkFileContent(projectName);
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, blkFileContent));
    }
  }

  /**
   * Creates a new project.
   */
  private static class NewProject implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String name = getFirstNamedParameter(session, PARAM_NAME);
      if (name != null) {
        String sampleName = getFirstNamedParameter(session, PARAM_SAMPLE_NAME);
        return newProject(session, name, sampleName);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " parameter is required");
      }
    }

    private Response newProject(NanoHTTPD.IHTTPSession session, String projectName, String sampleName) throws IOException {
      String blkContent = ProjectsUtil.newProject(projectName, sampleName);
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, blkContent));
    }
  }

  /**
   * Saves the blocks and JavaScript files for the given project.
   */
  private static class SaveProject implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String name = getFirstNamedParameter(session, PARAM_NAME);
      String blk = getFirstNamedParameter(session, PARAM_BLK);
      String js = getFirstNamedParameter(session, PARAM_JS);
      if (name != null && blk != null && js != null) {
        return saveProject(name, blk, js);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + ", " + PARAM_BLK + ", and " + PARAM_JS + " parameters are required");
      }
    }

    private Response saveProject(String projectName, String blkFileContent, String jsFileContent) throws IOException {
      ProjectsUtil.saveProject(projectName, blkFileContent, jsFileContent);
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Renames the given project.
   */
  private static class RenameProject implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String oldName = getFirstNamedParameter(session, PARAM_NAME);
      String newName = getFirstNamedParameter(session, PARAM_NEW_NAME);
      if (oldName != null && newName != null) {
        return renameProject(oldName, newName);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " and " + PARAM_NEW_NAME + " parameters are required");
      }
    }

    private Response renameProject(String oldProjectName, String newProjectName)
            throws IOException {
      ProjectsUtil.renameProject(oldProjectName, newProjectName);
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Copies the given project.
   */
  private static class CopyProject implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String oldName = getFirstNamedParameter(session, PARAM_NAME);
      String newName = getFirstNamedParameter(session, PARAM_NEW_NAME);
      if (oldName != null && newName != null) {
        return copyProject(oldName, newName);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " and " + PARAM_NEW_NAME + " parameters are required");
      }
    }

    private Response copyProject(String oldProjectName, String newProjectName)
            throws IOException {
      try {
        ProjectsUtil.copyProject(oldProjectName, newProjectName);
      } catch (IOExceptionWithUserVisibleMessage e) {
        return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, e.getMessage());
      }
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Enables (or disables) the given project.
   */
  private static class EnableProject implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String name = getFirstNamedParameter(session, PARAM_NAME);
      String enable = getFirstNamedParameter(session, PARAM_ENABLE);
      if (name != null && enable != null) {
        return enableProject(name, Boolean.parseBoolean(enable));
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " and " + PARAM_NEW_NAME + " parameters are required");
      }
    }

    private Response enableProject(String projectName, boolean enable)
            throws IOException {
      ProjectsUtil.enableProject(projectName, enable);
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Deletes the projects with the given names.
   */
  private static class DeleteProjects implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String names = getFirstNamedParameter(session, PARAM_NAME);
      if (names != null) {
        return deleteProjects(names);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " parameter is required");
      }
    }

    private Response deleteProjects(String starDelimitedProjectNames) throws IOException {
      String[] projectNames = starDelimitedProjectNames.split("\\*");
      ProjectsUtil.deleteProjects(projectNames);
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Gets the class name of the java class to be generated from blocks.
   */
  private static class GetBlocksJavaClassName implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String name = getFirstNamedParameter(session, PARAM_NAME);
      if (name != null) {
        return getBlocksJavaClassName(session, name);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " parameter is required");
      }
    }

    private Response getBlocksJavaClassName(NanoHTTPD.IHTTPSession session, String projectName) throws IOException {
      String className = ProjectsUtil.getBlocksJavaClassName(projectName);
      return NoCachingWebHandler.setNoCache(session, newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, className));
    }
  }

  /**
   * Saves the Java generated from blocks.
   */
  private static class SaveBlocksJava implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String relativeFileName = getFirstNamedParameter(session, PARAM_NAME);
      String javaContent = getFirstNamedParameter(session, PARAM_JAVA);
      if (relativeFileName != null && javaContent != null) {
        return saveBlocksJava(relativeFileName, javaContent);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_NAME + " and " + PARAM_JAVA + " parameters are required");
      }
    }

    private Response saveBlocksJava(String relativeFileName, String javaContent) throws IOException {
      ProjectsUtil.saveBlocksJava(relativeFileName, javaContent);
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Saves the clipboard content.
   */
  private static class SaveClipboard implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String clipboardContent = getFirstNamedParameter(session, PARAM_CLIPBOARD);
      if (clipboardContent != null) {
        return saveClipboardContent(clipboardContent);
      } else {
        return newFixedLengthResponse(
                Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
                "Bad Request: " + PARAM_CLIPBOARD + " parameter is required");
      }
    }

    private Response saveClipboardContent(String clipboardContent) throws IOException {
      ClipboardUtil.saveClipboardContent(clipboardContent);
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
    }
  }

  /**
   * Fetches the clipboard content.
   */
  private static class FetchClipboard implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      return fetchClipboardContent();
    }

    private Response fetchClipboardContent() throws IOException {
      String clipboardContent = ClipboardUtil.fetchClipboardContent();
      return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, clipboardContent);
    }
  }

  /**
   * Fetches the JavaScript code related to the file manager.
   */
  private static class FileManagerJS implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      if (fmName != null) {
        try {
          String js = FileManager.valueOf(fmName).fetchJavaScript();
          return NoCachingWebHandler.setNoCache(session,
              newFixedLengthResponse(Response.Status.OK, "application/javascript", js));
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + " parameter is required");
    }
  }

  /**
   * Fetches the names of files.
   */
  private static class ListFiles implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      if (fmName != null) {
        try {
          String json = FileManager.valueOf(fmName).fetchFiles();
          return NoCachingWebHandler.setNoCache(session,
              newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, json));
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + " parameter is required");
    }
  }

  /**
   * Saves a file.
   */
  private static class SaveFile implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      String name = getFirstNamedParameter(session, PARAM_NAME);
      String base64Content = getFirstNamedParameter(session, PARAM_CONTENT);
      if (fmName != null && name != null && base64Content != null) {
        try {
          FileManager.valueOf(fmName).saveFile(name, base64Content);
          return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + ", " + PARAM_NAME + ", and " + PARAM_CONTENT +
          " parameters are required");
    }
  }


  /**
   * Fetches the content of the file with the given name.
   */
  private static class FetchFile implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String name = getFirstNamedParameter(session, PARAM_NAME);
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      if (fmName != null && name != null) {
        try {
          String base64Content = FileManager.valueOf(fmName).fetchFileContent(name);
          return NoCachingWebHandler.setNoCache(session,
              newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, base64Content));
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + " and " + PARAM_NAME + " parameters are required");
    }
  }

  /**
   * Fetches the mime type of the file with the given name.
   */
  private static class FetchFileType implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String name = getFirstNamedParameter(session, PARAM_NAME);
      if (name != null) {
        String mimeType = MimeTypesUtil.determineMimeType(name);
        if (mimeType == null) {
          mimeType = "";
        }
        return NoCachingWebHandler.setNoCache(session,
            newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, mimeType));
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_NAME + " parameter is required");
    }
  }


  /**
   * Renames the given file.
   */
  private static class RenameFile implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      String oldName = getFirstNamedParameter(session, PARAM_NAME);
      String newName = getFirstNamedParameter(session, PARAM_NEW_NAME);
      if (fmName != null && oldName != null && newName != null) {
        try {
          FileManager.valueOf(fmName).renameFile(oldName, newName);
          return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + ", " + PARAM_NAME + ", and " + PARAM_NEW_NAME +
          " parameters are required");
    }
  }


  /**
   * Copies the given file.
   */
  private static class CopyFile implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      String oldName = getFirstNamedParameter(session, PARAM_NAME);
      String newName = getFirstNamedParameter(session, PARAM_NEW_NAME);
      if (fmName != null && oldName != null && newName != null) {
        try {
          FileManager.valueOf(fmName).copyFile(oldName, newName);
          return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + ", " + PARAM_NAME + ", and " + PARAM_NEW_NAME +
          " parameters are required");
    }
  }

  /**
   * Deletes the files with the given names.
   */
  private static class DeleteFiles implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      String fmName = getFirstNamedParameter(session, PARAM_FM_NAME);
      String starDelimitedNames = getFirstNamedParameter(session, PARAM_NAME);
      if (fmName != null && starDelimitedNames != null) {
        try {
          String[] names = starDelimitedNames.split("\\*");
          FileManager.valueOf(fmName).deleteFiles(names);
          return newFixedLengthResponse(Response.Status.OK, NanoHTTPD.MIME_PLAINTEXT, "");
        } catch (Exception e) {
          e.printStackTrace();
          return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT,
              "Internal Error");
        }
      }
      return newFixedLengthResponse(
          Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT,
          "Bad Request: " + PARAM_FM_NAME + " and " + PARAM_NAME + " parameters are required");
    }
  }

  /**
   * RestartRobot
   */
  private static class RestartRobot implements WebHandler {

    @Override
    public Response getResponse(NanoHTTPD.IHTTPSession session) throws IOException, NanoHTTPD.ResponseException {
      final NetworkConnectionHandler connectionHandler = NetworkConnectionHandler.getInstance();
      connectionHandler.injectReceivedCommand(new Command(CommandList.CMD_RESTART_ROBOT));
      return RobotWebHandlerManager.OK_RESPONSE;
    }
  }


///////////////////////////////////////////////////////////////////////////////////////////////
  // ProgrammingWebHandlers
  ///////////////////////////////////////////////////////////////////////////////////////////////

  private volatile ProgrammingModeManager programmingModeManager;

  /**
   * Constructs a {@link ProgrammingWebHandlers} with the given port.
   */
  public ProgrammingWebHandlers() {
  }

  private WebHandler decorateWithLogging(WebHandler handler) {
    return programmingModeManager.decorate(false, handler);
  }

  @Override
  public void register(ProgrammingModeManager manager) {
    programmingModeManager = manager;
    manager.register(URI_NAV_BLOCKS_OLD,   new RobotControllerWebHandlers.Redirection("/"));
    manager.register(URI_SERVER,           decorateWithLogging(new Server()));
    manager.register(URI_HARDWARE,         decorateWithLogging(new Hardware()));
    manager.register(URI_FILE_MANAGER_JS,  decorateWithLogging(decorateWithParms(new FileManagerJS())));
    manager.register(URI_GET_CONFIGURATION_NAME, decorateWithLogging(decorateWithParms(new GetConfigurationName())));
    manager.register(URI_FETCH_OFFLINE_BLOCKS_EDITOR, decorateWithLogging(new FetchOfflineBlocksEditor()));
    manager.register(URI_FETCH_BLOCKS_ARCHIVE, decorateWithLogging(new FetchBlocksArchive()));
    manager.register(URI_LIST_PROJECTS,    decorateWithLogging(new ListProjects()));
    manager.register(URI_LIST_SAMPLES,     decorateWithLogging(new ListSamples()));
    manager.register(URI_FETCH_BLK,        decorateWithLogging(decorateWithParms(new FetchBlockFile())));
    manager.register(URI_NEW_PROJECT,      decorateWithLogging(decorateWithParms(new NewProject())));
    manager.register(URI_SAVE_PROJECT,     decorateWithLogging(decorateWithParms(new SaveProject())));
    manager.register(URI_RENAME_PROJECT,   decorateWithLogging(decorateWithParms(new RenameProject())));
    manager.register(URI_COPY_PROJECT,     decorateWithLogging(decorateWithParms(new CopyProject())));
    manager.register(URI_ENABLE_PROJECT,   decorateWithLogging(decorateWithParms(new EnableProject())));
    manager.register(URI_DELETE_PROJECTS,  decorateWithLogging(decorateWithParms(new DeleteProjects())));
    manager.register(URI_GET_BLOCKS_JAVA_CLASS_NAME, decorateWithLogging(decorateWithParms(new GetBlocksJavaClassName())));
    manager.register(URI_SAVE_BLOCKS_JAVA, decorateWithLogging(decorateWithParms(new SaveBlocksJava())));
    manager.register(URI_SAVE_CLIPBOARD,   decorateWithLogging(decorateWithParms(new SaveClipboard())));
    manager.register(URI_FETCH_CLIPBOARD,  decorateWithLogging(new FetchClipboard()));
    manager.register(URI_LIST_FILES,       decorateWithLogging(decorateWithParms(new ListFiles())));
    manager.register(URI_SAVE_FILE,        decorateWithLogging(decorateWithParms(new SaveFile())));
    manager.register(URI_FETCH_FILE,       decorateWithLogging(decorateWithParms(new FetchFile())));
    manager.register(URI_FETCH_FILE_TYPE,  decorateWithLogging(decorateWithParms(new FetchFileType())));
    manager.register(URI_RENAME_FILE,      decorateWithLogging(decorateWithParms(new RenameFile())));
    manager.register(URI_COPY_FILE,        decorateWithLogging(decorateWithParms(new CopyFile())));
    manager.register(URI_DELETE_FILES ,    decorateWithLogging(decorateWithParms(new DeleteFiles())));
    manager.register(URI_RESTART_ROBOT,    decorateWithLogging(new RestartRobot()));
    manager.register(URI_COLORS,           decorateWithLogging(manager.getRegisteredHandler(URI_COLORS)));
    manager.register(RobotControllerWebHandlers.URI_RC_CONFIG,  new RobotControllerConfiguration());
  }

  static String getFirstNamedParameter(NanoHTTPD.IHTTPSession session, String name) {
    final Map<String, List<String>> parameters = session.getParameters();
    if (!parameters.containsKey(name)) return null;
    return parameters.get(name).get(0);
  }

  /**
   * add parms generation to a {@link WebHandler}
   */
  private WebHandler decorateWithParms(WebHandler delegate) {
    return new SessionParametersGenerator(delegate);
  }
}
