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

package com.google.blocks.ftcrobotcontroller.util;

import static com.google.blocks.ftcrobotcontroller.hardware.HardwareUtil.Capability;

import static org.firstinspires.ftc.robotcore.internal.system.AppUtil.BLOCKS_BLK_EXT;
import static org.firstinspires.ftc.robotcore.internal.system.AppUtil.BLOCKS_JS_EXT;
import static org.firstinspires.ftc.robotcore.internal.system.AppUtil.BLOCK_OPMODES_DIR;

import android.content.res.AssetManager;
import android.text.Html;
import android.util.Xml;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.google.blocks.ftcrobotcontroller.IOExceptionWithUserVisibleMessage;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItemMap;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareType;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareUtil;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.robotcore.external.ThrowingCallable;
import org.firstinspires.ftc.robotcore.internal.opmode.OnBotJavaHelper;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * A class that provides utility methods related to blocks projects.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
@SuppressWarnings("WeakerAccess")
public class ProjectsUtil {

  public static final String TAG = "ProjectsUtil";

  // NOTE(lizlooney): Do not change VALID_PROJECT_REGEX or the method isValidProjectName because
  // making it stricter will make it so that existing projects whose names are no longer valid will
  // not show up in the list of blocks projects and the user won't be able to rename them.
  public static final String VALID_PROJECT_REGEX =
      "^[a-zA-Z0-9 \\!\\#\\$\\%\\&\\'\\(\\)\\+\\,\\-\\.\\;\\=\\@\\[\\]\\^_\\{\\}\\~]+$";
  private static final String XML_END_TAG = "</xml>";
  private static final String XML_TAG_EXTRA = "Extra";
  private static final String XML_TAG_OP_MODE_META = "OpModeMeta";
  private static final String XML_ATTRIBUTE_FLAVOR = "flavor";
  private static final String XML_ATTRIBUTE_GROUP = "group";
  private static final String XML_ATTRIBUTE_AUTO_TRANSITION = "autoTransition";
  private static final String XML_TAG_ENABLED = "Enabled";
  private static final String XML_ATTRIBUTE_VALUE = "value";
  private static final String BLOCKS_SAMPLES_PATH = "blocks/samples";
  private static final String DEFAULT_BLOCKS_SAMPLE_NAME = "default";

  private static final OpModeMeta.Flavor DEFAULT_FLAVOR = OpModeMeta.Flavor.TELEOP;

  private static final Pattern identifierFieldPattern = Pattern.compile(
      "<field name=\"(IDENTIFIER|IDENTIFIER1|IDENTIFIER2|WEBCAM_NAME)\">(.*)</field>");
  private static final Pattern deviceNameWithSuffix = Pattern.compile(
      "(.*)(As.*)");
  private static final String[] specialDeviceNames = {
    // These must be all lower case.
    "left",
    "right",
  };

  // Prevent instantiation of utility class.
  private ProjectsUtil() {
  }

  /**
   * Returns the names and last modified time of existing blocks projects that have a blocks file.
   */
  public static String fetchProjectsWithBlocks() {
    AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);
    ReadWriteFile.ensureAllChangesAreCommitted(BLOCK_OPMODES_DIR);

    return ProjectsLockManager.lockProjectsWhile(new Supplier<String>() {
      @Override public String get() {
        File[] files = BLOCK_OPMODES_DIR.listFiles(new BlocksProjectFilenameFilter());
        if (files != null) {
          StringBuilder jsonProjects = new StringBuilder();
          jsonProjects.append("[");
          String delimiter = "";
          for (File file : files) {
            String filename = file.getName();
            String projectName = filename.substring(0, filename.length() - BLOCKS_BLK_EXT.length());
            try {
              boolean enabled = isProjectEnabled(projectName);
              jsonProjects.append(delimiter)
                  .append("{")
                  .append("\"name\":\"").append(escapeDoubleQuotes(projectName)).append("\", ")
                  .append("\"escapedName\":\"").append(escapeDoubleQuotes(Html.escapeHtml(projectName))).append("\", ")
                  .append("\"dateModifiedMillis\":").append(file.lastModified()).append(", ")
                  .append("\"enabled\":").append(enabled)
                  .append("}");
              delimiter = ",";
            } catch (IOException e) {
              RobotLog.e("ProjectsUtil.fetchProjectsWithBlocks() - problem with project " + projectName);
              RobotLog.logStackTrace(e);
            }
          }
          jsonProjects.append("]");
          return jsonProjects.toString();
        }
        return "[]";
      }
    });
  }

  public static String escapeSingleQuotes(String s) {
    return s.replace("'", "\\'");
  }

  public static String escapeDoubleQuotes(String s) {
    return s.replace("\"", "\\\"");
  }

  /**
   * Collects information about the existing blocks projects, for the offline blocks editor.
   */
  public static void fetchProjectsForOfflineBlocksEditor(
      final List<OfflineBlocksProject> offlineBlocksProjects) throws IOException {
    AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);
    ReadWriteFile.ensureAllChangesAreCommitted(BLOCK_OPMODES_DIR);

    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        File[] files = BLOCK_OPMODES_DIR.listFiles(new BlocksProjectFilenameFilter());
        if (files != null) {
          for (File file : files) {
            String filename = file.getName();
            String projectName = filename.substring(0, filename.length() - BLOCKS_BLK_EXT.length());
            String blkFileContent = fetchBlkFileContent(projectName);
            // The extraXml is after the first </xml>.
            int iXmlEndTag = blkFileContent.indexOf(XML_END_TAG);
            if (iXmlEndTag == -1) {
              // File is empty or corrupt.
              continue;
            }
            String extraXml = blkFileContent.substring(iXmlEndTag + XML_END_TAG.length());
            offlineBlocksProjects.add(new OfflineBlocksProject(filename, blkFileContent,
                projectName, file.lastModified(), isProjectEnabled(projectName, extraXml)));
          }
        }
        return null;
      }
    });
  }

  /**
   * Collects information about the existing blocks projects.
   */
  public static void fetchProjects(
      final List<BlocksProject> blocksProjects) throws IOException {
    AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);
    ReadWriteFile.ensureAllChangesAreCommitted(BLOCK_OPMODES_DIR);

    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        File[] files = BLOCK_OPMODES_DIR.listFiles(new BlocksProjectFilenameFilter());
        if (files != null) {
          for (File file : files) {
            String filename = file.getName();
            String projectName = filename.substring(0, filename.length() - BLOCKS_BLK_EXT.length());
            String blkFileContent = fetchBlkFileContent(projectName);
            blocksProjects.add(new BlocksProject(filename, blkFileContent, file.lastModified()));
          }
        }
        return null;
      }
    });
  }

  /**
   * Returns the names of blocks samples
   */
  public static String fetchSampleNames() throws IOException {
    HardwareItemMap hardwareItemMap = HardwareItemMap.newHardwareItemMap();

    StringBuilder jsonSamples = new StringBuilder();
    jsonSamples.append("[");

    AssetManager assetManager = AppUtil.getDefContext().getAssets();
    List<String> sampleFileNames = Arrays.asList(assetManager.list(BLOCKS_SAMPLES_PATH));
    Collections.sort(sampleFileNames);
    if (sampleFileNames != null) {
      String delimiter = "";
      for (String filename : sampleFileNames) {
        if (filename.endsWith(BLOCKS_BLK_EXT)) {
          String sampleName = filename.substring(0, filename.length() - BLOCKS_BLK_EXT.length());
          if (!sampleName.equals(DEFAULT_BLOCKS_SAMPLE_NAME)) {
            String blkFileContent = readSample(sampleName);
            Set<Capability> requestedCapabilities = getRequestedCapabilities(sampleName, blkFileContent);
            // TODO(lizlooney): Consider adding required hardware.
            jsonSamples
                .append(delimiter)
                .append("{")
                .append("\"name\":\"").append(escapeDoubleQuotes(sampleName)).append("\", ")
                .append("\"escapedName\":\"").append(escapeDoubleQuotes(Html.escapeHtml(sampleName))).append("\", ")
                .append("\"requestedCapabilities\":[");
            String delimiter2 = "";
            for (Capability requestedCapability : requestedCapabilities) {
              jsonSamples
                  .append(delimiter2)
                  .append("\"").append(requestedCapability).append("\"");
              delimiter2 = ",";
            }
            jsonSamples
                .append("]")
                .append("}");
            delimiter = ",";
          }
        }
      }
    }
    jsonSamples.append("]");
    return jsonSamples.toString();
  }

  static Map<String, String> getSamples(HardwareItemMap hardwareItemMap) throws IOException {
    Map<String, String> map = new TreeMap<>();
    AssetManager assetManager = AppUtil.getDefContext().getAssets();
    List<String> sampleFileNames = Arrays.asList(assetManager.list(BLOCKS_SAMPLES_PATH));
    for (String filename : sampleFileNames) {
      if (filename.endsWith(BLOCKS_BLK_EXT)) {
        String sampleName = filename.substring(0, filename.length() - BLOCKS_BLK_EXT.length());
        String blkFileContent = readSampleAndReplaceDeviceNames(sampleName, hardwareItemMap);
        if (sampleName.equals(DEFAULT_BLOCKS_SAMPLE_NAME)) {
          sampleName = "";
        }
        map.put(sampleName, blkFileContent);
      }
    }
    return map;
  }

  /**
   * Returns the set of capabilities used by the given blocks content.
   */
  private static Set<Capability> getRequestedCapabilities(String sampleName, String blkFileContent) {
    Set<Capability> requestedCapabilities = new LinkedHashSet<>();

    // The order here is important. If any of these capabilities is not supported by the robot
    // configuration, we will show the warning associated with the first missing capability.

    if (blkFileContent.contains("navigation_switchableCamera_forAllWebcams")) {
      requestedCapabilities.add(Capability.SWITCHABLE_CAMERA);
    }

    if (blkFileContent.contains("navigation_typedEnum_builtinCameraDirection") ||
        blkFileContent.contains("navigation_webcamName")) {
      requestedCapabilities.add(Capability.VISION);
    }

    return requestedCapabilities;
  }

  /**
   * Returns the {@link OpModeMeta} for existing blocks projects that have a JavaScript file and
   * are enabled.
   */
  public static List<OpModeMeta> fetchEnabledProjectsWithJavaScript() {
    return ProjectsLockManager.lockProjectsWhile(new Supplier<List<OpModeMeta>>() {
      @Override public List<OpModeMeta> get() {
        String[] filenames = BLOCK_OPMODES_DIR.list(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String filename) {
            if (filename.endsWith(BLOCKS_JS_EXT)) {
              String projectName = filename.substring(0, filename.length() - BLOCKS_JS_EXT.length());
              return isValidProjectName(projectName);
            }
            return false;
          }
        });
        List<OpModeMeta> projects = new ArrayList<OpModeMeta>();
        if (filenames != null) {
          for (String filename : filenames) {
            String projectName = filename.substring(0, filename.length() - BLOCKS_JS_EXT.length());
            OpModeMeta opModeMeta = fetchOpModeMeta(projectName);
            if (opModeMeta != null) {
              projects.add(opModeMeta);
            }
          }
        }
        return projects;
      }
    });
  }

  @Nullable
  private static OpModeMeta fetchOpModeMeta(String projectName) {
    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }
    try {
      ensureChangesAreCommitted(projectName);
      File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
      String blkFileContent = FileUtil.readFile(blkFile);
      // The extraXml is after the first </xml>.
      int iXmlEndTag = blkFileContent.indexOf(XML_END_TAG);
      if (iXmlEndTag == -1) {
        // File is empty or corrupt.
        throw new CorruptFileException("File " + blkFile.getName() + " is empty or corrupt.");
      }
      String extraXml = blkFileContent.substring(iXmlEndTag + XML_END_TAG.length());
      // Return null if the project is not enabled.
      if (!isProjectEnabled(projectName, extraXml)) {
        return null;
      }
      return createOpModeMeta(projectName, extraXml);
    } catch (IOException e) {
      if (!projectName.startsWith("backup_")) {
        RobotLog.e("ProjectsUtil.fetchOpModeMeta(\"" + projectName + "\") - failed.");
        RobotLog.logStackTrace(e);
      }
      return null;
    }
  }

  /**
   * Returns true if the given project name is not null and contains only valid characters.
   * This function does not check whether the project exists.
   */
  public static boolean isValidProjectName(String projectName) {
    if (projectName != null) {
      return projectName.matches(VALID_PROJECT_REGEX);
    }
    return false;
  }

  /**
   * Returns the content of the blocks file with the given project name. The returned content
   * may include extra XML after the blocks XML.
   *
   * @param projectName the name of the project
   */
  public static String fetchBlkFileContent(String projectName) throws IOException {
    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }
    ensureChangesAreCommitted(projectName);
    File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
    String blkFileContent = FileUtil.readFile(blkFile);

    // Separate the blocksContent from the extraXml, so we can upgrade the blocksContent.
    // The extraXml is after the first </xml>.
    int iXmlEndTag = blkFileContent.indexOf(XML_END_TAG);
    if (iXmlEndTag == -1) {
      // File is empty or corrupt.
      throw new CorruptFileException("File " + blkFile.getName() + " is empty or corrupt.");
    }
    String blocksContent = blkFileContent.substring(0, iXmlEndTag + XML_END_TAG.length());
    String extraXml = blkFileContent.substring(iXmlEndTag + XML_END_TAG.length());

    String upgradedBlocksContent = upgradeBlocks(blocksContent, HardwareItemMap.newHardwareItemMap());
    if (!upgradedBlocksContent.equals(blocksContent)) {
      blkFileContent = upgradedBlocksContent + extraXml;
    }

    return blkFileContent;
  }

  /**
   * Upgrades the given blocks content based on the given {@link HardwareItemMap}.
   */
  private static String upgradeBlocks(String blkContent, HardwareItemMap hardwareItemMap) {
    // clearFilters was renamed removeAllFilters (before PR 3267 was merged into ftc_sdk master).
    blkContent = blkContent.replace(
        "<block type=\"colorBlobLocatorProcessor_clearFilters",
        "<block type=\"colorBlobLocatorProcessor_removeAllFilters");

    // resetStartTime is deprecated. Use resetRuntime instead.
    blkContent = blkContent.replace(
        "<block type=\"linearOpMode_resetStartTime",
        "<block type=\"linearOpMode_resetRuntime");

    // Note that TensorFlow is now obsolete, but this code remains so that we can still open projects with TensorFlow blocks in the blocks editor.
    // In previous versions, there were separate blocks with block type prefixes tfodCurrentGame_
    // and tfodCustomModel_. Now TensorFlow blocks have the block type prefix tfod_.
    // All tfodCurrentGame_ blocks can safely be renamed tfod_.
    blkContent = blkContent.replace(
        "<block type=\"tfodCurrentGame_",
        "<block type=\"tfod_");
    // Some tfodCustomModel_ blocks can safely be renamed tfod_.
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_activate",
        "<block type=\"tfod_activate");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_deactivate",
        "<block type=\"tfod_deactivate");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_setClippingMargins",
        "<block type=\"tfod_setClippingMargins");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_setZoom",
        "<block type=\"tfod_setZoom");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_getRecognitions",
        "<block type=\"tfod_getRecognitions");
    // Some tfodCustomModel_ blocks can safely be renamed tfodLegacy_.
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_setModelFromAsset",
        "<block type=\"tfodLegacy_setModelFromAsset");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_setModelFromFile",
        "<block type=\"tfodLegacy_setModelFromFile");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_initialize_withIsModelTensorFlow2",
        "<block type=\"tfodLegacy_initialize_withIsModelTensorFlow2");
    blkContent = blkContent.replace(
        "<block type=\"tfodCustomModel_initialize_withAllArgs",
        "<block type=\"tfodLegacy_initialize_withAllArgs");

    // In previous versions, block type prefix bno055imu_ was adafruitBNO055IMU_.
    blkContent = blkContent.replace(
        "<block type=\"adafruitBNO055IMU_",
        "<block type=\"bno055imu_");
    // In previous versions, identifier suffix AsBNO055IMU was AsAdafruitBNO055IMU.
    blkContent = replaceIdentifierSuffixInBlocks(blkContent,
        hardwareItemMap.getHardwareItems(HardwareType.BNO055IMU),
        "AsAdafruitBNO055IMU", "AsBNO055IMU");
    // In previous versions, block type prefix bno055imuParameters_ was adafruitBNO055IMUParameters_.
    blkContent = blkContent.replace(
        "<block type=\"adafruitBNO055IMUParameters_",
        "<block type=\"bno055imuParameters_");
    // In previous versions, shadow type prefix bno055imuParameters_ was adafruitBNO055IMUParameters_.
    blkContent = blkContent.replace(
        "<shadow type=\"adafruitBNO055IMUParameters_",
        "<shadow type=\"bno055imuParameters_");
    // In previous version, value name BNO055IMU_PARAMETERS was ADAFRUIT_BNO055IMU_PARAMETERS.
    blkContent = blkContent.replace(
        "<value name=\"ADAFRUIT_BNO055IMU_PARAMETERS\">",
        "<value name=\"BNO055IMU_PARAMETERS\">");

    // In previous versions, identifier suffix AsREVModule was asLynxModule.
    blkContent = replaceIdentifierSuffixInBlocks(blkContent,
        hardwareItemMap.getHardwareItems(HardwareType.LYNX_MODULE),
        "asLynxModule", "AsREVModule");
    // In previous versions, identifier suffix AsREVColorRangeSensor was asLynxI2cColorRangeSensor.
    blkContent = replaceIdentifierSuffixInBlocks(blkContent,
        hardwareItemMap.getHardwareItems(HardwareType.COLOR_RANGE_SENSOR),
        "asLynxI2cColorRangeSensor", "AsREVColorRangeSensor");

    // In previous versions, some hardware types didn't have suffices.
    HardwareType[] typesThatDidntHaveSuffix = new HardwareType[] {
      HardwareType.ACCELERATION_SENSOR,
      HardwareType.COLOR_SENSOR,
      HardwareType.COMPASS_SENSOR,
      HardwareType.CR_SERVO,
      HardwareType.DC_MOTOR,
      HardwareType.DISTANCE_SENSOR,
      HardwareType.GYRO_SENSOR,
      HardwareType.IR_SEEKER_SENSOR,
      HardwareType.LED,
      HardwareType.LIGHT_SENSOR,
      HardwareType.SERVO,
      HardwareType.TOUCH_SENSOR,
      HardwareType.ULTRASONIC_SENSOR
    };
    for (HardwareType hardwareType : typesThatDidntHaveSuffix) {
      blkContent = replaceIdentifierSuffixInBlocks(blkContent,
          hardwareItemMap.getHardwareItems(hardwareType),
          "", hardwareType.identifierSuffixForJavaScript);
    }

    return blkContent;
  }

  /**
   * Replaces an identifier suffix in blocks.
   */
  private static String replaceIdentifierSuffixInBlocks(
      String blkContent, List<HardwareItem> hardwareItemList,
      String oldIdentifierSuffix, String newIdentifierSuffix) {
    if (hardwareItemList != null) {
      for (HardwareItem hardwareItem : hardwareItemList) {
        String newIdentifier = hardwareItem.identifier;
        if (newIdentifier.endsWith(newIdentifierSuffix)) {
          String oldIdentifier =
              newIdentifier.substring(0, newIdentifier.length() - newIdentifierSuffix.length())
              + oldIdentifierSuffix;
          String[] identifierFieldNames = new String[] {
            "IDENTIFIER",
            "IDENTIFIER1",
            "IDENTIFIER2",
          };
          for (String identifierFieldName : identifierFieldNames) {
            String oldElement = "<field name=\"" + identifierFieldName + "\">" + oldIdentifier + "</field>";
            String newElement = "<field name=\"" + identifierFieldName + "\">" + newIdentifier + "</field>";
            blkContent = blkContent.replace(oldElement, newElement);
          }
        }
      }
    }
    return blkContent;
  }

  /**
   * Returns the content of the JavaScript file with the given project name.
   *
   * @param projectName the name of the project
   */
  public static String fetchJsFileContent(String projectName) throws IOException {
    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }
    ensureChangesAreCommitted(projectName);
    File jsFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_JS_EXT);
    return FileUtil.readFile(jsFile);
  }

  private static void ensureChangesAreCommitted(String projectName) {
    File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
    File jsFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_JS_EXT);
    ReadWriteFile.ensureChangesAreCommitted(blkFile);
    ReadWriteFile.ensureChangesAreCommitted(jsFile);
  }

  /**
   * Returns the content of the blocks file for a new project. Note that this method does not save
   * the project. It just creates the content.
   *
   * @param projectName the name of the project
   * @param sampleName the name of the sample to copy.
   * @return the content of the blocks file
   */
  public static String newProject(String projectName, String sampleName) throws IOException {
    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }

    return readSampleAndReplaceDeviceNames(sampleName, HardwareItemMap.newHardwareItemMap());
  }

  private static String readSampleAndReplaceDeviceNames(String sampleName,
      HardwareItemMap hardwareItemMap) throws IOException {
    return replaceDeviceNamesInSample(readSample(sampleName), hardwareItemMap);
  }

  private static String readSample(String sampleName) throws IOException {
    if (sampleName == null || sampleName.isEmpty()) {
      sampleName = DEFAULT_BLOCKS_SAMPLE_NAME;
    }

    StringBuilder blkFileContent = new StringBuilder();
    AssetManager assetManager = AppUtil.getDefContext().getAssets();
    String assetName = BLOCKS_SAMPLES_PATH + "/" + sampleName + BLOCKS_BLK_EXT;
    FileUtil.readAsset(blkFileContent, assetManager, assetName);
    return blkFileContent.toString();
  }

  /**
   * Replaces the device names in the given blocks content based on the devices availble in the
   * given {@link HardwareItemMap}.
   */
  private static String replaceDeviceNamesInSample(String blkContent,
      HardwareItemMap hardwareItemMap) throws IOException {
    Map<HardwareType, Set<String>> sampleDeviceNamesMap = getSampleDeviceNamesMap(blkContent);

    for (Map.Entry<HardwareType, Set<String>> entry : sampleDeviceNamesMap.entrySet()) {
      HardwareType hardwareType = entry.getKey();
      Set<String> sampleDeviceNames = entry.getValue();
      if (sampleDeviceNames.isEmpty()) {
        continue;
      }

      if (hardwareItemMap.contains(hardwareType)) {
        // Get the list of HardwareItems of this hardware type from the HardwareItemMap.
        List<HardwareItem> items = hardwareItemMap.getHardwareItems(hardwareType);
        if (items.isEmpty()) {
          continue;
        }

        // Get their device names.
        List<String> actualDeviceNames = new ArrayList<>();
        for (HardwareItem item : items) {
          actualDeviceNames.add(item.deviceName);
        }

        // Figure out which sample device names to replace with which actual device names.
        Map<String, String> replacements = figureOutReplacements(sampleDeviceNames, actualDeviceNames);
        for (Map.Entry<String, String> replacementEntry : replacements.entrySet()) {
          String from = replacementEntry.getKey();
          String to = replacementEntry.getValue();
          blkContent = replaceDeviceNameInBlocks(hardwareType, blkContent, from, to);
        }
      }
    }
    return blkContent;
  }

  private static Map<HardwareType, Set<String>> getSampleDeviceNamesMap(String blkFileContent) {
    // This method can't be tested because HardwareType depends on BuiltInConfigurationType, which
    // depends on AppUtil, which calls android.os.Environment.getExternalStorageDirectory. I can't
    // figure out how to mock that. So, instead I have getSampleDeviceNamesMappedBySuffix, which
    // can be tested.
    Map<String, Set<String>> mapBySuffix = getSampleDeviceNamesMappedBySuffix(blkFileContent);

    Map<HardwareType, Set<String>> sampleDeviceNamesMap = new HashMap<>();
    for (Map.Entry<String, Set<String>> entry : mapBySuffix.entrySet()) {
      String suffix = entry.getKey();
      HardwareType hardwareType = (suffix == "WEBCAM_NAME")
          ? HardwareType.WEBCAM_NAME
          : HardwareType.fromIdentifierSuffixForJavaScript(suffix);
      if (hardwareType == null) {
        continue;
      }
      sampleDeviceNamesMap.put(hardwareType, entry.getValue());
    }
    return sampleDeviceNamesMap;
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  static Map<String, Set<String>> getSampleDeviceNamesMappedBySuffix(String blkFileContent) {
    Map<String, Set<String>> mapBySuffix = new HashMap<>();
    for (String line : blkFileContent.split("\n")) {
      Matcher matcher1 = identifierFieldPattern.matcher(line);
      if (matcher1.find()) {
        String fieldName = matcher1.group(1);
        String fieldValue = matcher1.group(2);
        if (fieldValue.equals("gamepad1") || fieldValue.equals("gamepad2")) {
          continue;
        }

        String deviceName;
        String suffix;
        if (fieldName.equals("WEBCAM_NAME")) {
          deviceName = fieldValue;
          suffix = "WEBCAM_NAME";
        } else {
          Matcher matcher2 = deviceNameWithSuffix.matcher(fieldValue);
          if (matcher2.find()) {
            deviceName = matcher2.group(1);
            suffix = matcher2.group(2);
          } else {
            continue;
          }
        }

        Set<String> deviceNames = mapBySuffix.get(suffix);
        if (deviceNames == null) {
          deviceNames = new HashSet<>();
          mapBySuffix.put(suffix, deviceNames);
        }
        deviceNames.add(deviceName);
      }
    }
    return mapBySuffix;
  }

  @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
  static Map<String, String> figureOutReplacements(Collection<String> sampleDeviceNamesArg, Collection<String> actualDeviceNamesArg) {
    Map<String, String> replacements = new HashMap<>();

    List<String> sampleDeviceNames = new ArrayList<>(sampleDeviceNamesArg);
    List<String> actualDeviceNames = new ArrayList<>(actualDeviceNamesArg);

    Collections.sort(sampleDeviceNames);
    Collections.sort(actualDeviceNames);

    // First, look for any sample device names that are the same (ignoring case) as actual device
    // names.
    for (Iterator<String> itSample = sampleDeviceNames.iterator(); itSample.hasNext();) {
      String sampleDeviceName = itSample.next();

      for (Iterator<String> itActual = actualDeviceNames.iterator(); itActual.hasNext();) {
        String actualDeviceName = itActual.next();

        if (sampleDeviceName.equalsIgnoreCase(actualDeviceName)) {
          itSample.remove();
          itActual.remove();
          if (!sampleDeviceName.equals(actualDeviceName)) {
            // If the names aren't exactly equal, add them to the replacements map.
            replacements.put(sampleDeviceName, actualDeviceName);
          }
          break;
        }
      }
    }

    // Second, handle special device names like left and right.
    for (String specialDeviceName : specialDeviceNames) {
      // Look for a sample device name that contains (ignoring case) the special device name.
      for (Iterator<String> itSample = sampleDeviceNames.iterator(); itSample.hasNext();) {
        String sampleDeviceName = itSample.next();
        if (sampleDeviceName.toLowerCase(Locale.ENGLISH).contains(specialDeviceName)) {
          // Look for an actual device name that contains (ignoring case) the special device name.
          for (Iterator<String> itActual = actualDeviceNames.iterator(); itActual.hasNext();) {
            String actualDeviceName = itActual.next();
            if (actualDeviceName.toLowerCase(Locale.ENGLISH).contains(specialDeviceName)) {
              itSample.remove();
              itActual.remove();
              replacements.put(sampleDeviceName, actualDeviceName);
              break;
            }
          }
          break;
        }
      }
    }

    // Finally go through any remaining sample device names.
    while (!sampleDeviceNames.isEmpty()) {
      if (actualDeviceNames.isEmpty()) {
        // The sample will have device names that weren't replaced because there aren't enough
        // hardware items of this type.
        break;
      }

      String sampleDeviceName = sampleDeviceNames.remove(0);
      String actualDeviceName = actualDeviceNames.remove(0);
      replacements.put(sampleDeviceName, actualDeviceName);
    }

    return replacements;
  }

  /**
   * Replaces a device name in blocks.
   */
  private static String replaceDeviceNameInBlocks(HardwareType hardwareType, String blkContent,
      String sampleDeviceName, String actualDeviceName) {
    String oldIdentifier;
    String newIdentifier;
    String[] fieldNames;
    if (hardwareType == HardwareType.WEBCAM_NAME) {
      // The webcam block (navigation_webcamName) uses the device name, rather than the identifier.
      oldIdentifier = sampleDeviceName;
      newIdentifier = actualDeviceName;
      fieldNames = new String[] {"WEBCAM_NAME"};
    } else {
      oldIdentifier = hardwareType.makeIdentifier(sampleDeviceName);
      newIdentifier = hardwareType.makeIdentifier(actualDeviceName);
      if (hardwareType == HardwareType.DC_MOTOR) {
        fieldNames = new String[] {"IDENTIFIER", "IDENTIFIER1", "IDENTIFIER2"};
      } else {
        fieldNames = new String[] {"IDENTIFIER"};
      }
    }

    for (String fieldName : fieldNames) {
      String oldElement = "<field name=\"" + fieldName + "\">" + oldIdentifier + "</field>";
      String newElement = "<field name=\"" + fieldName + "\">" + newIdentifier + "</field>";
      blkContent = blkContent.replace(oldElement, newElement);

      // The following is for <data> tags.
      oldElement = "\"" + fieldName + "\":\"" + sampleDeviceName + "\"";
      newElement = "\"" + fieldName + "\":\"" + actualDeviceName + "\"";
      blkContent = blkContent.replace(oldElement, newElement);
    }
    return blkContent;
  }

  /**
   * Save the blocks file and JavaScript file with the given project name.
   *
   * @param projectName the name of the project
   * @param blkFileContent the content to write to the blocks file.
   * @param jsFileContent the content to write to the JavaScript file.
   */
  public static void saveProject(final String projectName, final String blkFileContent, final String jsFileContent)
      throws IOException {

    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }

    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);

        File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
        File jsFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_JS_EXT);
        ReadWriteFile.updateFileRequiringCommit(blkFile, blkFileContent);
        ReadWriteFile.updateFileRequiringCommit(jsFile, jsFileContent);
        return null;
      }
    });
  }

  /**
   * Renames the blocks file and JavaScript file with the given project name.
   *
   * @param oldProjectName the old name of the project
   * @param newProjectName the new name of the project
   */
  public static void renameProject(final String oldProjectName, final String newProjectName)
      throws IOException {
    if (!isValidProjectName(oldProjectName) || !isValidProjectName(newProjectName)) {
      throw new IllegalArgumentException();
    }
    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);

        File oldBlk = new File(BLOCK_OPMODES_DIR, oldProjectName + BLOCKS_BLK_EXT);
        File newBlk = new File(BLOCK_OPMODES_DIR, newProjectName + BLOCKS_BLK_EXT);
        if (oldBlk.renameTo(newBlk)) {
          File oldJs = new File(BLOCK_OPMODES_DIR, oldProjectName + BLOCKS_JS_EXT);
          File newJs = new File(BLOCK_OPMODES_DIR, newProjectName + BLOCKS_JS_EXT);
          oldJs.renameTo(newJs);
        }
        return null;
      }
    });

  }

  /**
   * Copies the blocks file and JavaScript file with the given project name.
   *
   * @param oldProjectName the old name of the project
   * @param newProjectName the new name of the project
   */
  public static void copyProject(final String oldProjectName, final String newProjectName)
      throws IOException {
    if (!isValidProjectName(oldProjectName) || !isValidProjectName(newProjectName)) {
      throw new IllegalArgumentException();
    }
    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);

        File oldBlk = new File(BLOCK_OPMODES_DIR, oldProjectName + BLOCKS_BLK_EXT);
        File newBlk = new File(BLOCK_OPMODES_DIR, newProjectName + BLOCKS_BLK_EXT);
        FileUtil.copyFile(oldBlk, newBlk);
        try {
          File oldJs = new File(BLOCK_OPMODES_DIR, oldProjectName + BLOCKS_JS_EXT);
          File newJs = new File(BLOCK_OPMODES_DIR, newProjectName + BLOCKS_JS_EXT);
          FileUtil.copyFile(oldJs, newJs);
        } catch (IOException e) {
          throw new IOExceptionWithUserVisibleMessage(
              "The Blocks OpMode was successfully copied, but the new OpMode cannot be run until it " +
              "is saved in the Blocks editor.");
        }
        return null;
      }
    });

  }

  /**
   * Enables (or disables) the project with the given name.
   *
   * @param projectName the name of the project
   * @param enable whether to enable (or disable) the project
   */
  public static void enableProject(final String projectName, final boolean enable)
      throws IOException {

    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }

    ensureChangesAreCommitted(projectName);

    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
        String blkFileContent = FileUtil.readFile(blkFile);

        // Separate the blocksContent from the extraXml, so we can extract the OpModeMeta from the extraXml.
        // The extraXml is after the first </xml>.
        int iXmlEndTag = blkFileContent.indexOf(XML_END_TAG);
        if (iXmlEndTag == -1) {
          // File is empty or corrupt.
          throw new CorruptFileException("File " + blkFile.getName() + " is empty or corrupt.");
        }
        String blocksContent = blkFileContent.substring(0, iXmlEndTag + XML_END_TAG.length());
        String extraXml = blkFileContent.substring(iXmlEndTag + XML_END_TAG.length());
        OpModeMeta opModeMeta = createOpModeMeta(projectName, extraXml);

        // Regenerate the extra xml with the enable argument.
        String newBlkFileContent = blocksContent +
            formatExtraXml(opModeMeta.flavor, opModeMeta.group, opModeMeta.autoTransition, enable);
        ReadWriteFile.updateFileRequiringCommit(blkFile, newBlkFileContent);
        return null;
      }
    });
  }

  /**
   * Delete the blocks and JavaScript files for the given project names.
   *
   * @param projectNames the names of the projects to delete
   */
  public static Boolean deleteProjects(final String[] projectNames) {

    return ProjectsLockManager.lockProjectsWhile(new Supplier<Boolean>() {
      @Override public Boolean get() {
        for (String projectName : projectNames) {
          if (!isValidProjectName(projectName)) {
            throw new IllegalArgumentException();
          }
        }
        boolean success = true;
        for (String projectName : projectNames) {
          File jsFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_JS_EXT);
          if (jsFile.exists()) {
            if (!jsFile.delete()) {
              success = false;
            }
          }
          if (success) {
            File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
            if (blkFile.exists()) {
              if (!blkFile.delete()) {
                success = false;
              }
            }
          }
        }
        return success;
      }
    });
  }

  public static String getBlocksJavaClassName(String projectName) {
    StringBuilder className = new StringBuilder();

    char ch = projectName.charAt(0);
    if (Character.isJavaIdentifierStart(ch)) {
      className.append(ch);
    } else if (Character.isJavaIdentifierPart(ch)) {
      className.append('_').append(ch);
    }
    int length = projectName.length();
    for (int i = 1; i < length; i++) {
      ch = projectName.charAt(i);
      if (Character.isJavaIdentifierPart(ch)) {
        className.append(ch);
      }
    }

    // Make sure the name doesn't match an existing class in
    // /sdcard/FIRST/java/src/org/firstinspires/ftc/teamcode.
    File dir = new File(OnBotJavaHelper.srcDir, "org/firstinspires/ftc/teamcode");
    String base = className.toString();
    File file = new File(dir, base + ".java");
    if (file.exists()) {
      int i = 1; // Note that this is immediately incremented to 2.
      do {
        i++;
        file = new File(dir, base + i + ".java");
      } while (file.exists());
      className.append(i);
    }
    return className.toString();
  }

  /**
   * Save the Java generated from blocks.
   *
   * @param relativeFileName the name of the file
   * @param javaContent the content to write to the Java file.
   */
  public static void saveBlocksJava(final String relativeFileName, final String javaContent)
      throws IOException {

    ProjectsLockManager.lockProjectsWhile(new ThrowingCallable<Void, IOException>() {
      @Override public Void call() throws IOException {
        AppUtil.getInstance().ensureDirectoryExists(BLOCK_OPMODES_DIR, false);

        int lastSlash = relativeFileName.lastIndexOf("/");
        String relativeDir = relativeFileName.substring(0, lastSlash + 1);
        String filename = relativeFileName.substring(lastSlash + 1);
        File dir = new File(BLOCK_OPMODES_DIR, "../java/src/" + relativeDir);
        dir.mkdirs();
        File javaFile = new File(dir, filename);
        ReadWriteFile.updateFileRequiringCommit(javaFile, javaContent);
        return null;
      }
    });
  }

  /**
   * Formats the extra XML.
   */
  private static String formatExtraXml(OpModeMeta.Flavor flavor, String group, String autoTransition, boolean enabled)
      throws IOException {
    XmlSerializer serializer = Xml.newSerializer();
    StringWriter writer = new StringWriter();
    serializer.setOutput(writer);
    serializer.startDocument("UTF-8", true);
    serializer.startTag("", XML_TAG_EXTRA);
    serializer.startTag("", XML_TAG_OP_MODE_META);
    serializer.attribute("", XML_ATTRIBUTE_FLAVOR, flavor.toString());
    serializer.attribute("", XML_ATTRIBUTE_GROUP, group);
    if (autoTransition != null) {
      serializer.attribute("", XML_ATTRIBUTE_AUTO_TRANSITION, autoTransition);
    }
    serializer.endTag("", XML_TAG_OP_MODE_META);
    serializer.startTag("", XML_TAG_ENABLED);
    serializer.attribute("", XML_ATTRIBUTE_VALUE, Boolean.toString(enabled));
    serializer.endTag("", XML_TAG_ENABLED);
    serializer.endTag("", XML_TAG_EXTRA);
    serializer.endDocument();
    return writer.toString();
  }

  /**
   * Creates an {@link OpModeMeta} instance with the given name, extracting the flavor, group, and
   * autoTransition from the given extraXml.
   */
  private static OpModeMeta createOpModeMeta(String projectName, String extraXml) {
    OpModeMeta.Flavor flavor = DEFAULT_FLAVOR;
    String group = "";
    String autoTransition = null;

    try {
      XmlPullParser parser = Xml.newPullParser();
      parser.setInput(new StringReader(removeNewLines(extraXml)));
      int eventType = parser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG) {
          if (parser.getName().equals(XML_TAG_OP_MODE_META)) {
            for (int i = 0; i < parser.getAttributeCount(); i++) {
              String name = parser.getAttributeName(i);
              String value = parser.getAttributeValue(i);
              if (name.equals(XML_ATTRIBUTE_FLAVOR)) {
                flavor = OpModeMeta.Flavor.valueOf(value.toUpperCase(Locale.ENGLISH));
              } else if (name.equals(XML_ATTRIBUTE_GROUP)) {
                if (!value.isEmpty() && !value.equals(OpModeMeta.DefaultGroup)) {
                  group = value;
                }
              } else if (name.equals(XML_ATTRIBUTE_AUTO_TRANSITION)) {
                if (!value.isEmpty()) {
                  autoTransition = value;
                }
              }
            }
          }
        }
        eventType = parser.next();
      }
    } catch (IOException | XmlPullParserException e) {
      RobotLog.e("ProjectsUtil.createOpmodeMeta(\"" + projectName + "\", ...) - failed to parse xml.");
      RobotLog.logStackTrace(e);
    }
    OpModeMeta.Builder builder = new OpModeMeta.Builder()
        .setName(projectName)
        .setFlavor(flavor)
        .setGroup(group)
        .setSource(OpModeMeta.Source.BLOCKLY);
    if (autoTransition != null) {
      builder.setTransitionTarget(autoTransition);
    }
    return builder.build();
  }

  private static boolean isProjectEnabled(String projectName) throws IOException {
    if (!isValidProjectName(projectName)) {
      throw new IllegalArgumentException();
    }
    ensureChangesAreCommitted(projectName);
    File blkFile = new File(BLOCK_OPMODES_DIR, projectName + BLOCKS_BLK_EXT);
    String blkFileContent = FileUtil.readFile(blkFile);
    // The extraXml is after the first </xml>.
    int iXmlEndTag = blkFileContent.indexOf(XML_END_TAG);
    if (iXmlEndTag == -1) {
      // File is empty or corrupt.
      throw new CorruptFileException("File " + blkFile.getName() + " is empty or corrupt.");
    }
    String extraXml = blkFileContent.substring(iXmlEndTag + XML_END_TAG.length());
    return isProjectEnabled(projectName, extraXml);
  }

  /**
   * Returns false if the given extraXml contains the tag/attribute for enabling a project and the
   * value of attribute is false. Otherwise it returns true.
   */
  private static boolean isProjectEnabled(String projectName, String extraXml) {
    boolean enabled = true;

    try {
      XmlPullParser parser = Xml.newPullParser();
      parser.setInput(new StringReader(removeNewLines(extraXml)));
      int eventType = parser.getEventType();
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG) {
          if (parser.getName().equals(XML_TAG_ENABLED)) {
            for (int i = 0; i < parser.getAttributeCount(); i++) {
              String name = parser.getAttributeName(i);
              String value = parser.getAttributeValue(i);
              if (name.equals(XML_ATTRIBUTE_VALUE)) {
                enabled = Boolean.parseBoolean(value);
              }
            }
          }
        }
        eventType = parser.next();
      }
    } catch (IOException | XmlPullParserException e) {
      RobotLog.e("ProjectsUtil.isProjectEnabled(\"" + projectName + "\", ...) - failed to parse xml.");
      RobotLog.logStackTrace(e);
    }

    return enabled;
  }

  private static String removeNewLines(String text) {
    return text.replace("\n", "");
  }

  static class BlocksProjectFilenameFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String filename) {
      if (filename.endsWith(BLOCKS_BLK_EXT)) {
        String projectName = filename.substring(0, filename.length() - BLOCKS_BLK_EXT.length());
        return isValidProjectName(projectName);
      }
      return false;
    }
  }
}
