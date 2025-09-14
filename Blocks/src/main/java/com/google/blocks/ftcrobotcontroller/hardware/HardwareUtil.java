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

package com.google.blocks.ftcrobotcontroller.hardware;

import static com.google.blocks.ftcrobotcontroller.util.CurrentGame.CURRENT_GAME_NAME;
import static com.google.blocks.ftcrobotcontroller.util.CurrentGame.CURRENT_GAME_NAME_NO_SPACES;
import static com.google.blocks.ftcrobotcontroller.util.ProjectsUtil.XML_END_TAG;
import static com.google.blocks.ftcrobotcontroller.util.ProjectsUtil.XML_EXTRA_START;
import static com.google.blocks.ftcrobotcontroller.util.ProjectsUtil.escapeSingleQuotes;
import static com.google.blocks.ftcrobotcontroller.util.ToolboxUtil.escapeForXml;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Size;

import com.google.blocks.ftcrobotcontroller.util.AvailableTtsLocalesProvider;
import com.google.blocks.ftcrobotcontroller.util.FileUtil;
import com.google.blocks.ftcrobotcontroller.util.Identifier;
import com.google.blocks.ftcrobotcontroller.util.ProjectsUtil;
import com.google.blocks.ftcrobotcontroller.util.ToolboxFolder;
import com.google.blocks.ftcrobotcontroller.util.ToolboxIcon;
import com.google.blocks.ftcrobotcontroller.util.ToolboxUtil;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.ftccommon.configuration.RobotConfigFile;
import com.qualcomm.ftccommon.configuration.RobotConfigFileManager;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.digitalchickenlabs.CachingOctoQuad;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuad;
import com.qualcomm.hardware.digitalchickenlabs.OctoQuadBase;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.maxbotix.MaxSonarI2CXL;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cCompassSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver.BlinkinPattern;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.rev.Rev9AxisImuOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunLEDStick;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerImpl;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cAddrConfig;
import com.qualcomm.robotcore.hardware.I2cAddressableDevice;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ImuOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Light;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.OrientationSensor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PWMOutput;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.DeviceConfiguration;
import com.qualcomm.robotcore.robot.RobotState;
import com.qualcomm.robotcore.util.Device;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;
import com.qualcomm.robotcore.util.SortOrder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.ExportAprilTagLibraryToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportEnumToBlocks;
import org.firstinspires.ftc.robotcore.external.ExportToBlocks;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.android.AndroidAccelerometer;
import org.firstinspires.ftc.robotcore.external.android.AndroidGyroscope;
import org.firstinspires.ftc.robotcore.external.android.AndroidOrientation;
import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;
import org.firstinspires.ftc.robotcore.external.android.AndroidTextToSpeech;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.CameraControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.FocusControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.PtzControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.WhiteBalanceControl;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Axis;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.robotcore.external.navigation.UnnormalizedAngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.robotcore.internal.opmode.BlocksClassFilter;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagMetadata;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseRaw;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ColorSpace;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;

/**
 * A class that provides utility methods related to hardware.
 *
 * @author lizlooney@google.com (Liz Looney)
 */
public class HardwareUtil {
  private static final SensorManager sensorManager = (SensorManager) AppUtil.getDefContext().getSystemService(Context.SENSOR_SERVICE);

  private static final BlocksClassFilter blocksClassFilter = BlocksClassFilter.getInstance();

  private static final String DC_MOTOR_EX_CATEGORY_NAME = "Extended";
  private static final String DC_MOTOR_DUAL_CATEGORY_NAME = "Dual";
  private static final String GAMEPAD_CATEGORY_NAME = "Gamepad"; // see toolbox/gamepad.xml
  private static final String LINEAR_OP_MODE_CATEGORY_NAME = "LinearOpMode"; // see toolbox/linear_op_mode.xml
  private static final String COLOR_CATEGORY_NAME = "Color"; // see toolbox/utilities.xml
  private static final String ELAPSED_TIME_CATEGORY_NAME = "ElapsedTime"; // see toolbox/utilities.xml

  public static final String IDENTIFIERS_USED_PREFIX = "// IDENTIFIERS_USED=";

  public enum Capability {
    BUILTIN_CAMERA("builtinCamera"),
    WEBCAM("webcam"),
    SWITCHABLE_CAMERA("switchableCamera"),
    VISION("vision");

    /**
     * The placeholderType field is used to include or omit some parts of the toolbox. For
     * example, if the robot does not have multiple webcams, the blocks that support switchable
     * camera will be omitted from the toolbox.
     */
    private final String placeholderType;

    Capability(String placeholderType) {
      this.placeholderType = placeholderType;
    }

    static Capability fromPlaceholderType(String type) {
      for (Capability capability : Capability.values()) {
        if (capability.placeholderType.equals(type)) {
          return capability;
        }
      }
      throw new IllegalArgumentException("Unexpected capability name " + type);
    }
  }

  /**
   * A {@link Set} of reserved words for FtcJava.
   */
  private static final Set<String> RESERVED_WORDS_FOR_FTCJAVA = new TreeSet<>();

  /**
   * A {@link set} of classes that are known types in FtcJava.
   */
  private static final Map<String, Class<?>[]> KNOWN_TYPES_FOR_FTCJAVA = new TreeMap<>();

  /**
   * A {@link Map} from xmlTag to List of {@link HardwareType}.
   */
  private static final Map<String, List<HardwareType>> XML_TAG_TO_HARDWARE_TYPES =
      new HashMap<String, List<HardwareType>>();

  static {
    buildKnownTypesAndReservedWordsForFtcJava();

    for (HardwareType hardwareType : HardwareType.values()) {
      for (String xmlTag : hardwareType.xmlTags) {
        List<HardwareType> list = (List<HardwareType>) XML_TAG_TO_HARDWARE_TYPES.get(xmlTag);
        if (list == null) {
          list = new ArrayList<HardwareType>();
          XML_TAG_TO_HARDWARE_TYPES.put(xmlTag, list);
        }
        list.add(hardwareType);
      }
    }
  }

  // Prevent instantiation of util class.
  private HardwareUtil() {
  }

  /**
   * Returns the corresponding {@link HardwareType}s for the given XML tag.
   */
  // visible for testing
  static Iterable<HardwareType> getHardwareTypes(String xmlTag) {
    return XML_TAG_TO_HARDWARE_TYPES.containsKey(xmlTag)
        ? Collections.<HardwareType>unmodifiableList(XML_TAG_TO_HARDWARE_TYPES.get(xmlTag))
        : Collections.<HardwareType>emptyList();
  }

  /**
   * Returns the corresponding {@link HardwareType}s for the given {@link DeviceConfiguration}.
   */
  static Iterable<HardwareType> getHardwareTypes(DeviceConfiguration deviceConfiguration) {
    return getHardwareTypes(deviceConfiguration.getConfigurationType().getXmlTag());
  }

  /**
   * Returns the JavaScript code related to the hardware in the active configuration.
   */
  public static String fetchJavaScriptForHardware() throws IOException {
    return fetchJavaScriptForHardware(HardwareItemMap.newHardwareItemMap());
  }

  /**
   * Returns the JavaScript code related to the hardware in the given {@link HardwareItem}.
   */
  // visible for testing
  public static String fetchJavaScriptForHardware(HardwareItemMap hardwareItemMap) throws IOException {
    Context context = AppUtil.getDefContext();
    AssetManager assetManager = context.getAssets();
    StringBuilder jsHardware = new StringBuilder().append("\n");
    Map<Capability, Boolean> capabilities = getCapabilities(hardwareItemMap);

    Set<String> additionalReservedWordsForFtcJava = new HashSet<>();
    Set<String> methodLookupStrings = new HashSet<>();
    String toolbox = generateToolbox(hardwareItemMap, capabilities, assetManager,
        additionalReservedWordsForFtcJava, methodLookupStrings, jsHardware);
    toolbox = toolbox
        .replace("\n", " ")
        .replaceAll("\\> +\\<", "><");
    // The toolbox is added at the end, because it makes it easier to troubleshoot problems with
    // this code.

    Set<String> teleOpNames = new TreeSet<>();
    RegisteredOpModes registeredOpModes = RegisteredOpModes.getInstance();
    registeredOpModes.waitOpModesRegistered();
    for (OpModeMeta opModeMeta : registeredOpModes.getOpModes()) {
      if (opModeMeta.flavor == OpModeMeta.Flavor.TELEOP) {
        teleOpNames.add(opModeMeta.name);
      }
    }

    jsHardware.append("var allHardwareIdentifiers = [\n");
    for (HardwareItem hardwareItem : hardwareItemMap.getAllHardwareItems()) {
      jsHardware
          .append("    '").append(hardwareItem.identifier).append("',\n");
    }
    jsHardware
        .append("  ];\n\n");

    jsHardware.append("var IDENTIFIERS_USED_PREFIX = '").append(IDENTIFIERS_USED_PREFIX).append("';\n\n");

    jsHardware
        .append("var XML_END_TAG = '").append(escapeSingleQuotes(XML_END_TAG)).append("';\n")
        .append("var XML_EXTRA_START = '").append(escapeSingleQuotes(XML_EXTRA_START)).append("';\n\n");

    jsHardware.append("var AUTO_TRANSITION_OPTIONS = [\n");
    for (String teleOpName : teleOpNames) {
      jsHardware.append("  '").append(escapeSingleQuotes(teleOpName)).append("',\n");
    }
    jsHardware.append("];\n\n");

    jsHardware
        .append("var currentGameName = '").append(escapeSingleQuotes(CURRENT_GAME_NAME)).append("';\n")
        .append("var currentGameNameNoSpaces = '").append(escapeSingleQuotes(CURRENT_GAME_NAME_NO_SPACES)).append("';\n")
        .append("\n");

    jsHardware
        .append("var methodLookupStrings = [\n");
    for (String methodLookupString : methodLookupStrings) {
      jsHardware.append("  '").append(methodLookupString).append("',\n");
    }
    jsHardware
        .append("];\n\n");

    jsHardware
        .append("function getOctoQuadConstant(constantIdentifier) {\n")
        .append("  switch (constantIdentifier) {\n")
	.append("    case 'OCTOQUAD_CHIP_ID':\n")
        .append("      return '").append("0x").append(Integer.toHexString(OctoQuadBase.OCTOQUAD_CHIP_ID).toUpperCase()).append("';\n")
	.append("    case 'SUPPORTED_FW_VERSION_MAJ':\n")
        .append("      return '").append(OctoQuadBase.SUPPORTED_FW_VERSION_MAJ).append("';\n")
	.append("    case 'ENCODER_FIRST':\n")
        .append("      return '").append(OctoQuadBase.ENCODER_FIRST).append("';\n")
	.append("    case 'ENCODER_LAST':\n")
        .append("      return '").append(OctoQuadBase.ENCODER_LAST).append("';\n")
	.append("    case 'NUM_ENCODERS':\n")
        .append("      return '").append(OctoQuadBase.NUM_ENCODERS).append("';\n")
	.append("    case 'MIN_VELOCITY_MEASUREMENT_INTERVAL_MS':\n")
        .append("      return '").append(OctoQuadBase.MIN_VELOCITY_MEASUREMENT_INTERVAL_MS).append("';\n")
	.append("    case 'MAX_VELOCITY_MEASUREMENT_INTERVAL_MS':\n")
        .append("      return '").append(OctoQuadBase.MAX_VELOCITY_MEASUREMENT_INTERVAL_MS).append("';\n")
	.append("    case 'MIN_PULSE_WIDTH_US':\n")
        .append("      return '").append(OctoQuadBase.MIN_PULSE_WIDTH_US).append("';\n")
	.append("    case 'MAX_PULSE_WIDTH_US':\n")
        .append("      return '").append("0x").append(Integer.toHexString(OctoQuadBase.MAX_PULSE_WIDTH_US).toUpperCase()).append("';\n")
        .append("  }\n")
        .append("  return '';\n")
        .append("}\n\n");

    jsHardware
        .append("function isValidProjectName(projectName) {\n")
        .append("  if (projectName) {\n")
        .append("    return /").append(ProjectsUtil.VALID_PROJECT_REGEX).append("/.test(projectName);\n")
        .append("  }\n")
        .append("  return false;\n")
        .append("}\n\n");

    jsHardware
        .append("function getSparkFunOTOSConstant(constantIdentifier) {\n")
        .append("  switch (constantIdentifier) {\n")
	.append("    case 'MIN_SCALAR':\n")
        .append("      return '").append(SparkFunOTOS.MIN_SCALAR).append("';\n")
	.append("    case 'MAX_SCALAR':\n")
        .append("      return '").append(SparkFunOTOS.MAX_SCALAR).append("';\n")
        .append("  }\n")
        .append("  return '';\n")
        .append("}\n\n");

    jsHardware
        .append("function getColorConstant(constantIdentifier) {\n")
        .append("  switch (constantIdentifier) {\n")
	.append("    case 'BLACK':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.BLACK).toUpperCase()).append("';\n")
	.append("    case 'BLUE':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.BLUE).toUpperCase()).append("';\n")
	.append("    case 'CYAN':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.CYAN).toUpperCase()).append("';\n")
	.append("    case 'DKGRAY':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.DKGRAY).toUpperCase()).append("';\n")
	.append("    case 'GRAY':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.GRAY).toUpperCase()).append("';\n")
	.append("    case 'GREEN':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.GREEN).toUpperCase()).append("';\n")
	.append("    case 'LTGRAY':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.LTGRAY).toUpperCase()).append("';\n")
	.append("    case 'MAGENTA':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.MAGENTA).toUpperCase()).append("';\n")
	.append("    case 'RED':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.RED).toUpperCase()).append("';\n")
	.append("    case 'WHITE':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.WHITE).toUpperCase()).append("';\n")
	.append("    case 'YELLOW':\n")
        .append("      return '").append("0x").append(Integer.toHexString(Color.YELLOW).toUpperCase()).append("';\n")
        .append("  }\n")
        .append("  return '';\n")
        .append("}\n\n");

    StringBuilder blinkinPatternTooltips = new StringBuilder();
    StringBuilder blinkinPatternFromTextTooltip = new StringBuilder();
    blinkinPatternTooltips
        .append("var BLINKIN_PATTERN_TOOLTIPS = [\n");
    blinkinPatternFromTextTooltip
        .append("var BLINKIN_PATTERN_FROM_TEXT_TOOLTIP =\n")
        .append("    'Returns the pattern associated with the given text. Valid input is ' +\n");
    BlinkinPattern[] blinkinPatterns = BlinkinPattern.values();
    BlinkinPattern blinkinPattern = blinkinPatterns[0];
    for (int i = 0; i < blinkinPatterns.length - 1; blinkinPattern = blinkinPatterns[++i]) {
      blinkinPatternTooltips
          .append("  ['").append(blinkinPattern).append("', 'The BlinkinPattern value ")
          .append(blinkinPattern).append(".'],\n");
      blinkinPatternFromTextTooltip
          .append("    '").append(blinkinPattern).append(", ' +\n");
    }
    blinkinPatternTooltips
        .append("];\n");
    blinkinPatternFromTextTooltip
      .append("    'or ").append(blinkinPattern).append(".';\n");
    jsHardware
        .append(blinkinPatternTooltips)
        .append("\n")
        .append(blinkinPatternFromTextTooltip)
        .append("\n");

    // Locales
    SortedMap<String, String> languageCodes = new TreeMap<String, String>();
    SortedMap<String, String> countryCodes = new TreeMap<String, String>();
    for (Locale locale : AvailableTtsLocalesProvider.getInstance().getAvailableTtsLocales()) {
      languageCodes.put(locale.getLanguage(), locale.getDisplayLanguage());
      String countryCode = locale.getCountry();
      if (!countryCode.isEmpty()) {
        countryCodes.put(countryCode, locale.getDisplayCountry());
      }
    }
    Locale defaultLocale = Locale.getDefault();
    // Languages
    String defaultLanguageCode = defaultLocale.getLanguage();
    StringBuilder createLanguageCodeDropdown = new StringBuilder();
    StringBuilder languageCodeTooltips = new StringBuilder();
    createLanguageCodeDropdown
        .append("function createLanguageCodeDropdown() {\n")
        .append("  var CHOICES = [\n");
    languageCodeTooltips
        .append("var LANGUAGE_CODE_TOOLTIPS = [\n");
    // First item is the default language.
    addLanguage(defaultLanguageCode, defaultLocale.getDisplayLanguage(), createLanguageCodeDropdown, languageCodeTooltips);
    // Remaining lanuages are sorted alphabetically.
    for (Map.Entry<String, String> entry : languageCodes.entrySet()) {
      String languageCode = entry.getKey();
      if (!languageCode.equals(defaultLanguageCode)) {
        String languageName = entry.getValue();
        addLanguage(languageCode, languageName, createLanguageCodeDropdown, languageCodeTooltips);
      }
    }
    createLanguageCodeDropdown.append("  ];\n")
        .append("  return createFieldDropdown(CHOICES);\n")
        .append("}\n\n");
    languageCodeTooltips
        .append("];\n");
    jsHardware
        .append(createLanguageCodeDropdown)
        .append(languageCodeTooltips)
        .append("\n");
    // Countries
    String defaultCountryCode = defaultLocale.getCountry();
    StringBuilder createCountryCodeDropdown = new StringBuilder();
    StringBuilder countryCodeTooltips = new StringBuilder();
    createCountryCodeDropdown
        .append("function createCountryCodeDropdown() {\n")
        .append("  var CHOICES = [\n");
    countryCodeTooltips
        .append("var COUNTRY_CODE_TOOLTIPS = [\n");
    // First item is the default country.
    addCountry(defaultCountryCode, defaultLocale.getDisplayCountry(), createCountryCodeDropdown, countryCodeTooltips);
    // Remaining countries are sorted alphabetically.
    for (Map.Entry<String, String> entry : countryCodes.entrySet()) {
      String countryCode = entry.getKey();
      if (!countryCode.equals(defaultCountryCode)) {
        String countryName = entry.getValue();
        addCountry(countryCode, countryName, createCountryCodeDropdown, countryCodeTooltips);
      }
    }
    createCountryCodeDropdown.append("  ];\n")
        .append("  return createFieldDropdown(CHOICES);\n")
        .append("}\n\n");
    countryCodeTooltips
        .append("];\n");
    jsHardware
        .append(createCountryCodeDropdown)
        .append(countryCodeTooltips)
        .append("\n");

    // SkyStone sound resources
    StringBuilder createSkyStoneSoundResourceDropdown = new StringBuilder();
    StringBuilder skyStoneSoundResourceTooltips = new StringBuilder();
    createSkyStoneSoundResourceDropdown
        .append("function createSkyStoneSoundResourceDropdown() {\n")
        .append("  var CHOICES = [\n");
    skyStoneSoundResourceTooltips
        .append("var SKY_STONE_SOUND_RESOURCE_TOOLTIPS = [\n");
    List<String> resourceNames = new ArrayList<String>();
    for (java.lang.reflect.Field field : com.qualcomm.ftccommon.R.raw.class.getFields()) {
      String resourceName = field.getName();
      if (resourceName.toUpperCase().startsWith("SS_")) {
        resourceNames.add(resourceName);
      }
    }
    Collections.sort(resourceNames);
    for (String resourceName : resourceNames) {
      createSkyStoneSoundResourceDropdown
          .append("      ['")
          .append(escapeSingleQuotes(makeVisibleNameForDropdownItem(resourceName)))
          .append("', '")
          .append(escapeSingleQuotes(resourceName))
          .append("'],\n");
      skyStoneSoundResourceTooltips
          .append("  ['")
          .append(escapeSingleQuotes(resourceName))
          .append("', 'The SoundResource value ")
          .append(escapeSingleQuotes(resourceName))
          .append(".'],\n");
    }
    createSkyStoneSoundResourceDropdown.append("  ];\n")
        .append("  return createFieldDropdown(CHOICES);\n")
        .append("}\n\n");
    skyStoneSoundResourceTooltips
        .append("];\n");
    jsHardware
        .append(createSkyStoneSoundResourceDropdown)
        .append(skyStoneSoundResourceTooltips)
        .append("\n");

    jsHardware
        .append("var androidSoundPoolRawResPrefix = '")
        .append(AndroidSoundPool.RAW_RES_PREFIX)
        .append("';\n");

    // Identifiers
    for (Identifier identifier : Identifier.values()) {
      if (identifier.variableForJavaScript != null) {
        jsHardware
            .append("var ").append(identifier.variableForJavaScript).append(" = '")
            .append(identifier.identifierForJavaScript).append("';\n");
      }
      if (identifier.variableForFtcJava != null) {
        jsHardware
            .append("var ").append(identifier.variableForFtcJava).append(" = '")
            .append(identifier.identifierForFtcJava).append("';\n");
      }
    }

    jsHardware.append("\n");

    // Webcam device names
    jsHardware
        .append("function createWebcamDeviceNameDropdown() {\n")
        .append("  var CHOICES = [\n");
    List<HardwareItem> hardwareItemsForWebcam =
        hardwareItemMap.getHardwareItems(HardwareType.WEBCAM_NAME);
    for (HardwareItem hardwareItemForWebcam : hardwareItemsForWebcam) {
      jsHardware
          .append("    ['").append(escapeSingleQuotes(hardwareItemForWebcam.visibleName)).append("', '")
          .append(escapeSingleQuotes(hardwareItemForWebcam.deviceName)).append("'],\n");
    }
    jsHardware
        .append("  ];\n")
        .append("  return createFieldDropdown(CHOICES);\n")
        .append("}\n\n");

    // Hardware
    for (HardwareType hardwareType : HardwareType.values()) {
      // Some HardwareTypes might have a null createDropdownFunctionName. This allows us to support
      // certain hardware types, even though we don't actually provide blocks.
      if (hardwareType.createDropdownFunctionName != null) {
        List<HardwareItem> hardwareItems = hardwareItemMap.getHardwareItems(hardwareType);
        appendCreateDropdownFunction(jsHardware, hardwareType.createDropdownFunctionName,
            hardwareItems);

        // Special case for DC_MOTOR: create the dropdown function for DcMotorEx.
        if (hardwareType == HardwareType.DC_MOTOR) {
          List<HardwareItem> hardwareItemsForDcMotorEx = getHardwareItemsForDcMotorEx(hardwareItems);
          appendCreateDropdownFunction(jsHardware, "createDcMotorExDropdown", hardwareItemsForDcMotorEx);
        }
      }
    }
    jsHardware
        .append("function getHardwareIdentifierSuffixes() {\n")
        .append("  var suffixes = [\n");
    for (HardwareType hardwareType : HardwareType.values()) {
      jsHardware
          .append("    '" + hardwareType.identifierSuffixForJavaScript + "',\n");
    }
    jsHardware
        .append("  ];\n")
        .append("  return suffixes;\n")
        .append("}\n\n");

    jsHardware
        .append("function addReservedWordsForJavaScript() {\n");
    for (HardwareItem hardwareItem : hardwareItemMap.getAllHardwareItems()) {
      jsHardware
          .append("  Blockly.JavaScript.addReservedWords('")
          .append(hardwareItem.identifier).append("');\n");
    }
    List<String> identifiersForJavaScript = new ArrayList<>();
    for (Identifier identifier : Identifier.values()) {
      if (identifier.identifierForJavaScript != null && !identifier.identifierForJavaScript.isEmpty()) {
        identifiersForJavaScript.add(identifier.identifierForJavaScript);
      }
    }
    Collections.sort(identifiersForJavaScript);
    for (String identifierForJavaScript : identifiersForJavaScript) {
      jsHardware
          .append("  Blockly.JavaScript.addReservedWords('")
          .append(identifierForJavaScript).append("');\n");
    }
    jsHardware
        .append("}\n\n");

    jsHardware
        .append("function getHardwareItemDeviceName(identifier) {\n")
        .append("  switch (identifier) {\n");
    for (HardwareItem hardwareItem : hardwareItemMap.getAllHardwareItems()) {
      jsHardware
          .append("    case '").append(hardwareItem.identifier).append("':\n")
          .append("      return '").append(escapeSingleQuotes(hardwareItem.deviceName)).append("';\n");
    }
    jsHardware
        .append("  }\n")
        .append("  throw 'Unexpected identifier (' + identifier + ').';\n")
        .append("}\n\n");

    jsHardware
        .append("function getHardwareItemIdentifierForFtcJava(identifier) {\n")
        .append("  switch (identifier) {\n");
    Set<String> set = new HashSet<>();
    for (HardwareItem hardwareItem : hardwareItemMap.getAllHardwareItems()) {
      String identifierForFtcJava = HardwareItem.makeIdentifier(hardwareItem.deviceName);
      // Check that it isn't a reserved word.
      if (RESERVED_WORDS_FOR_FTCJAVA.contains(identifierForFtcJava)) {
        identifierForFtcJava += hardwareItem.hardwareType.identifierSuffixForFtcJava;
      }
      // Check if identifierForFtcJava is already in the set.
      if (!set.add(identifierForFtcJava)) {
        // Make the identifierForFtcJava unique by adding a hardware type suffix.
        identifierForFtcJava += hardwareItem.hardwareType.identifierSuffixForFtcJava;
        set.add(identifierForFtcJava);
      }
      jsHardware
          .append("    case '").append(hardwareItem.identifier).append("':\n")
          .append("      return '").append(identifierForFtcJava).append("';\n");
    }
    jsHardware
        .append("  }\n")
        .append("  throw 'Unexpected identifier (' + identifier + ').';\n")
        .append("}\n\n");

    jsHardware
        .append("function addReservedWordsForFtcJava() {\n");
    for (String word : RESERVED_WORDS_FOR_FTCJAVA) {
      jsHardware
          .append("  Blockly.FtcJava.addReservedWords('").append(word).append("');\n");
    }
    for (String word : additionalReservedWordsForFtcJava) {
      jsHardware
          .append("  Blockly.FtcJava.addReservedWords('").append(word).append("');\n");
    }
    for (HardwareItem hardwareItem : hardwareItemMap.getAllHardwareItems()) {
      jsHardware
          .append("  Blockly.FtcJava.addReservedWords(getHardwareItemIdentifierForFtcJava('")
          .append(hardwareItem.identifier).append("'));\n");
    }
    for (Identifier identifier : Identifier.values()) {
      if (identifier.identifierForFtcJava != null && !identifier.identifierForFtcJava.isEmpty()) {
        jsHardware
            .append("  Blockly.FtcJava.addReservedWords('")
            .append(identifier.identifierForFtcJava).append("');\n");
      }
    }
    jsHardware
        .append("}\n\n");

    jsHardware
        .append("function knownTypeToClassName(type) {\n")
        .append("  switch (type) {\n");
    for (Map.Entry<String, Class<?>[]> entry: KNOWN_TYPES_FOR_FTCJAVA.entrySet()) {
      String pkg = entry.getKey();
      for (Class<?> c : entry.getValue()) {
        if (!pkg.equals(c.getPackage().getName())) {
          RobotLog.e("Error: expected package " + pkg + " for " + c);
        }
        String name = c.getSimpleName();
        Class<?> enclosingClass = c.getEnclosingClass();
        while (enclosingClass != null) {
          name = enclosingClass.getSimpleName() + '.' + name;
          enclosingClass = enclosingClass.getEnclosingClass();
        }
        jsHardware
            .append("    case '").append(name).append("':\n");
        // Special cases for CRServo.Direction and DcMotor.Direction.
        if (c.equals(CRServo.class) || c.equals(DcMotor.class)) {
          jsHardware
              .append("    case '").append(name).append(".Direction':\n");
        }
      }
      jsHardware
          .append("      return '").append(pkg).append(".' + type;\n");
    }
    // For some opencv types we use the full class name because the simple class name conflicts with another class.
    jsHardware
        .append("    case '").append(org.opencv.core.Point.class.getName()).append("':\n")
        .append("    case '").append(org.opencv.core.Rect.class.getName()).append("':\n")
        .append("    case '").append(org.opencv.core.Size.class.getName()).append("':\n")
        .append("      return type;\n");
    jsHardware
        .append("  }\n")
        .append("  return knownTypeToClassNameObsolete(type);\n")
        .append("}\n\n");

    jsHardware
        .append("function getIconClass(categoryName) {\n");
    for (HardwareType hardwareType : HardwareType.values()) {
      // Some HardwareTypes might have a null toolboxCategoryName. This allows us to support
      // certain hardware types, even though we don't actually provide blocks.
      // Also, some HardwareTypes, such as BNO055IMU, do not (yet) have a toolbox icon.
      if (hardwareType.toolboxCategoryName != null &&
          hardwareType.toolboxIcon != null) {
        jsHardware
            .append("  if (categoryName == '").append(escapeSingleQuotes(hardwareType.toolboxCategoryName)).append("') {\n")
            .append("    return '").append(escapeSingleQuotes(hardwareType.toolboxIcon.cssClass)).append("';\n")
            .append("  }\n");
      }
    }
    jsHardware
        .append("  if (categoryName == '").append(escapeSingleQuotes(DC_MOTOR_DUAL_CATEGORY_NAME)).append("') {\n")
        .append("    return '").append(escapeSingleQuotes(ToolboxIcon.DC_MOTOR.cssClass)).append("';\n")
        .append("  }\n")
        .append("  if (categoryName == '").append(escapeSingleQuotes(GAMEPAD_CATEGORY_NAME)).append("') {\n")
        .append("    return '").append(escapeSingleQuotes(ToolboxIcon.GAMEPAD.cssClass)).append("';\n")
        .append("  }\n")
        .append("  if (categoryName == '").append(escapeSingleQuotes(LINEAR_OP_MODE_CATEGORY_NAME)).append("') {\n")
        .append("    return '").append(escapeSingleQuotes(ToolboxIcon.LINEAR_OPMODE.cssClass)).append("';\n")
        .append("  }\n")
        .append("  if (categoryName == '").append(escapeSingleQuotes(COLOR_CATEGORY_NAME)).append("') {\n")
        .append("    return '").append(escapeSingleQuotes(ToolboxIcon.COLOR_SENSOR.cssClass)).append("';\n")
        .append("  }\n")
        .append("  if (categoryName == '").append(escapeSingleQuotes(ELAPSED_TIME_CATEGORY_NAME)).append("') {\n")
        .append("    return '").append(escapeSingleQuotes(ToolboxIcon.ELAPSED_TIME.cssClass)).append("';\n")
        .append("  }\n")
        .append("  return '';\n")
        .append("}\n\n");

    // Generate the JS method getWarningForCapabilityRequestedBySample which takes a capability
    // that is requested by a sample OpMode.
    // If the system does not have the capability and the user should be warned about this,
    // the method returns the warning message.
    // If the system has the capability or no warning is needed, the method returns ''.
    jsHardware
        .append("function getWarningForCapabilityRequestedBySample(capability) {\n")
        .append("  switch (capability) {\n");

    for (Capability capability : Capability.values()) {
      if (!capabilities.get(capability)) {
        String warning = getCapabilityWarning(capability);
        if (warning != null) {
          jsHardware
              .append("    case '").append(capability).append("':\n")
              .append("      return '").append(warning).append("';\n");
        }
      }
    }
    jsHardware
        .append("  }\n")
        .append("  return '';\n")
        .append("}\n\n");

    // Put the toolbox at the end, because it makes it easier to troubleshoot problems with this
    // code.
    jsHardware
        .append("function getToolbox() {\n")
        .append("  return '").append(escapeSingleQuotes(toolbox)).append("';\n")
        .append("}\n\n");

    return jsHardware.toString();
  }

  /**
   * Generates a visible name for a blockly dropdown item.
   */
  static String makeVisibleNameForDropdownItem(String name) {
    int length = name.length();
    StringBuilder visibleName = new StringBuilder();

    for (int i = 0; i < length; i++) {
      char ch = name.charAt(i);
      if (ch == ' ') {
        visibleName.append('\u00A0');
      } else {
        visibleName.append(ch);
      }
    }
    return visibleName.toString();
  }

  private static void addLanguage(String languageCode, String languageName, StringBuilder dropdown, StringBuilder tooltips) {
    dropdown
        .append("      ['")
        .append(escapeSingleQuotes(makeVisibleNameForDropdownItem(languageCode)))
        .append("', '")
        .append(escapeSingleQuotes(languageCode))
        .append("'],\n");
    tooltips
        .append("  ['")
        .append(escapeSingleQuotes(languageCode))
        .append("', 'The language code for ")
        .append(escapeSingleQuotes(languageName))
        .append(".'],\n");
  }

  private static void addCountry(String countryCode, String countryName, StringBuilder dropdown, StringBuilder tooltips) {
    dropdown
        .append("      ['")
        .append(escapeSingleQuotes(makeVisibleNameForDropdownItem(countryCode)))
        .append("', '")
        .append(escapeSingleQuotes(countryCode))
        .append("'],\n");
    tooltips
        .append("  ['")
        .append(escapeSingleQuotes(countryCode))
        .append("', 'The country code for ")
        .append(escapeSingleQuotes(countryName))
        .append(".'],\n");
  }

  private static void appendCreateDropdownFunction(StringBuilder jsHardware,
      String functionName, List<HardwareItem> hardwareItems) {
    jsHardware
        .append("function ").append(functionName).append("() {\n")
        .append("  var CHOICES = [\n");
    for (HardwareItem hardwareItem : hardwareItems) {
      jsHardware
          .append("      ['").append(escapeSingleQuotes(hardwareItem.visibleName)).append("', '")
          .append(hardwareItem.identifier).append("'],\n");
    }
    jsHardware
        .append("  ];\n")
        .append("  return createFieldDropdown(CHOICES);\n")
        .append("}\n\n");
  }

  private static List<HardwareItem> getHardwareItemsForDcMotorEx(List<HardwareItem> hardwareItemsForDcMotor) {
    List<HardwareItem> hardwareItemsForDcMotorEx = new ArrayList<>();
    for (HardwareItem hardwareItemForDcMotor : hardwareItemsForDcMotor) {
      // All allowed motors support DcMotorEx.
      hardwareItemsForDcMotorEx.add(hardwareItemForDcMotor);
    }
    return hardwareItemsForDcMotorEx;
  }

  /**
   * Generates the toolbox for the blocks editor, excluding the categories for {@link HardwareType}s
   * that do not exist in the given {@link HardwareItemMap}.
   */
  @SuppressWarnings("deprecation")
  private static String generateToolbox(HardwareItemMap hardwareItemMap,
      Map<Capability, Boolean> capabilities, AssetManager assetManager,
      Set<String> additionalReservedWordsForFtcJava,
      Set<String> methodLookupStrings,
      StringBuilder jsHardware) throws IOException {
    StringBuilder xmlToolbox = new StringBuilder();
    xmlToolbox.append("<xml id=\"toolbox\" style=\"display: none\">\n");

    // assetManager can be null during tests.
    if (assetManager != null) {
      addAsset(xmlToolbox, assetManager, "toolbox/linear_op_mode.xml");
      addAsset(xmlToolbox, assetManager, "toolbox/gamepad.xml");
    }

    for (ToolboxFolder toolboxFolder : ToolboxFolder.values()) {
      xmlToolbox.append(" <category name=\"").append(toolboxFolder.label)
          .append("\">\n");
      // Sort the hardware types by toolboxCategoryName.
      SortedSet<HardwareType> hardwareTypes = new TreeSet<>(HardwareType.BY_TOOLBOX_CATEGORY_NAME);
      hardwareTypes.addAll(hardwareItemMap.getHardwareTypes());
      for (HardwareType hardwareType : hardwareTypes) {
        if (hardwareType.toolboxFolder == toolboxFolder) {
          // Some HardwareTypes might have a null toolboxCategoryName. This allows us to support
          // certain hardware types, even though we don't actually provide blocks.
          if (hardwareType.toolboxCategoryName != null) {
            addHardwareCategoryToToolbox(
                xmlToolbox, hardwareType, hardwareItemMap.getHardwareItems(hardwareType), assetManager);
          }
        }
      }
      xmlToolbox.append(" </category>\n");
    }

    addAndroidCategoriesToToolbox(xmlToolbox, assetManager);

    addExportedHardwareAndEnums(xmlToolbox, additionalReservedWordsForFtcJava, methodLookupStrings, jsHardware);

    addExportedStaticMethodsAndEnums(xmlToolbox, additionalReservedWordsForFtcJava, methodLookupStrings);

    if (assetManager != null) {
      addAssetWithPlaceholders(xmlToolbox,
          assetManager, additionalReservedWordsForFtcJava, methodLookupStrings,
          capabilities, hardwareItemMap, "toolbox/utilities.xml");

      addAsset(xmlToolbox, assetManager, "toolbox/misc.xml");
    }

    xmlToolbox.append("</xml>");
    return xmlToolbox.toString();
  }

  private static void addExportedHardwareAndEnums(StringBuilder xmlToolbox,
      Set<String> additionalReservedWordsForFtcJava,
      Set<String> methodLookupStrings,
      StringBuilder jsHardware) {
    Map<Class<? extends HardwareDevice>, Set<Method>> methodsByClass = blocksClassFilter.getHardwareMethodsByClass();
    if (methodsByClass.isEmpty()) {
      return;
    }

    OpModeManagerImpl opModeManagerImpl = OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getRootActivity());
    if (opModeManagerImpl == null) {
      RobotLog.w("Fetching blocks toolbox: Unable to get OpModeManagerImpl");
      return;
    }

    // We need to wait for the robot to be running in order to get an accurate HardwareMap.
    long startTime = System.nanoTime();
    while (opModeManagerImpl.getRobotState() != RobotState.RUNNING) {
      if (System.nanoTime() - startTime < 60_000_000_000L) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      } else {
        return;
      }
    }

    HardwareMap hardwareMap = OpModeManagerImpl.getOpModeManagerOfActivity(AppUtil.getInstance().getRootActivity()).getHardwareMap();
    if (hardwareMap == null) {
      RobotLog.w("Fetching blocks toolbox: Unable to get HardwareMap");
      return;
    }

    boolean addedAdditionalHardwareCategory = false;

    Map<Class, Set<Class<? extends Enum>>> enumClassesByEnclosingClass = blocksClassFilter.getEnumClassesByEnclosingClass();

    for (Map.Entry<Class<? extends HardwareDevice>, Set<Method>> entry : methodsByClass.entrySet()) {
      Class<? extends HardwareDevice> clazz = entry.getKey();

      // Check if the hardware map contains any of this class.
      SortedSet<String> deviceNames = hardwareMap.getAllNames(clazz);
      if (deviceNames.isEmpty()) {
        continue;
      }

      if (!addedAdditionalHardwareCategory) {
        xmlToolbox.append("<category name=\"Additional Hardware\">\n");
        addedAdditionalHardwareCategory = true;
      }

      DeviceProperties deviceProperties = clazz.getAnnotation(DeviceProperties.class);
      String createDropdownFunctionName = processDeviceNames(
          jsHardware, deviceProperties, deviceNames);

      String fullClassName = clazz.getName();
      getUserVisibleClassName(fullClassName, additionalReservedWordsForFtcJava);
      xmlToolbox.append("<category name=\"").append(deviceProperties.name()).append("\">\n");

      // Add blocks for methods.
      Set<Method> methods = entry.getValue();
      for (Method method : methods) {
        ExportToBlocks exportToBlocks = method.getAnnotation(ExportToBlocks.class);
        if (exportToBlocks == null) {
          continue;
        }
        String returnType = method.getReturnType().getName();
        String blockType = returnType.equals("void") ? "misc_callHardware_noReturn" : "misc_callHardware_return";
        String methodName = method.getName();
        Class[] parameterTypes = method.getParameterTypes();
        int color = exportToBlocks.color();
        String comment = exportToBlocks.comment();
        String tooltip = exportToBlocks.tooltip();
        String methodLookupString = BlocksClassFilter.getLookupString(method);
        methodLookupStrings.add(methodLookupString);
        xmlToolbox
            .append("<block type=\"").append(blockType).append("\">\n")
            .append("<field name=\"METHOD_NAME\">").append(methodName).append("</field>")
            .append("<mutation")
            .append(" createDropdownFunctionName=\"").append(createDropdownFunctionName).append("\"")
            .append(" methodLookupString=\"").append(methodLookupString).append("\"")
            .append(" fullClassName=\"").append(fullClassName).append("\"")
            .append(" simpleName=\"").append(clazz.getSimpleName()).append("\"")
            .append(" parameterCount=\"").append(parameterTypes.length).append("\"")
            .append(" returnType=\"").append(returnType).append("\"")
            .append(" color=\"").append(color).append("\"")
            .append(" heading=\"\"")
            .append(" comment=\"").append(escapeForXml(comment)).append("\"")
            .append(" tooltip=\"").append(escapeForXml(tooltip)).append("\"")
            .append(" accessMethod=\"").append(accessMethod(true, method.getReturnType())).append("\"")
            .append(" convertReturnValue=\"").append(convertReturnValue(method.getReturnType())).append("\"");
        processMethodArguments(xmlToolbox, parameterTypes, getParameterLabels(method), getParameterDefaultValues(method));
        // Note that processMethodArguments terminates the mutation and block tags.
      }

      // Add blocks for enums.
      Set<Class<? extends Enum>> enumClasses = enumClassesByEnclosingClass.get(clazz);
      if (enumClasses != null) {
        for (Class<? extends Enum> enumClass : enumClasses) {
          addExportedEnum(enumClass, xmlToolbox, additionalReservedWordsForFtcJava);
        }
      }

      xmlToolbox.append("</category>\n");
    }
    if (addedAdditionalHardwareCategory) {
      xmlToolbox.append("</category>\n");
    }
  }

  private static String processDeviceNames(StringBuilder jsHardware, DeviceProperties deviceProperties,
      SortedSet<String> deviceNames) {
    // Make a name for the function. Start the device's xmlTag.
    StringBuilder sb = new StringBuilder();
    String xmlTag = deviceProperties.xmlTag();
    for (int i = 0; i < xmlTag.length(); i++) {
      char ch = xmlTag.charAt(i);
      if (Character.isJavaIdentifierPart(ch)) {
        sb.append(ch);
      } else {
        sb.append('_');
      }
    }
    String functionName = sb.toString();

    jsHardware
        .append("function ").append(functionName).append("() {\n")
        .append("  var CHOICES = [\n");
    for (String deviceName : deviceNames) {
      String escapedDeviceName = escapeSingleQuotes(deviceName);
      jsHardware
          .append("      ['").append(escapedDeviceName).append("', '")
          .append(escapedDeviceName).append("'],\n");
    }
    jsHardware
        .append("  ];\n")
        .append("  return createFieldDropdown(CHOICES);\n")
        .append("}\n\n");

    return functionName;
  }

  private static void addExportedStaticMethodsAndEnums(StringBuilder xmlToolbox,
      Set<String> additionalReservedWordsForFtcJava,
      Set<String> methodLookupStrings) {
    boolean addedJavaClassesCategory = false;

    Map<Class, Set<Method>> methodsByClass = blocksClassFilter.getStaticMethodsByClass();
    Map<Class, Set<Class<? extends Enum>>> enumClassesByEnclosingClass = blocksClassFilter.getEnumClassesByEnclosingClass();

    for (Map.Entry<Class, Set<Method>> entry : methodsByClass.entrySet()) {
      if (!addedJavaClassesCategory) {
        xmlToolbox.append("<category name=\"Java Classes\">\n");
        addedJavaClassesCategory = true;
      }
      Class enclosingClass = entry.getKey();
      // Add a category for the enclosing class.
      String fullClassName = enclosingClass.getName();
      String userVisibleClassName = getUserVisibleClassName(
          fullClassName, additionalReservedWordsForFtcJava);
      xmlToolbox.append("<category name=\"").append(userVisibleClassName).append("\">\n");

      // Add blocks for methods.
      Set<Method> methods = entry.getValue();
      for (Method method : methods) {
        ExportToBlocks exportToBlocks = method.getAnnotation(ExportToBlocks.class);
        if (exportToBlocks == null) {
          continue;
        }
        String returnType = method.getReturnType().getName();
        String blockType = returnType.equals("void") ? "misc_callJava_noReturn" : "misc_callJava_return";
        String methodName = method.getName();
        Class[] parameterTypes = method.getParameterTypes();
        int color = exportToBlocks.color();
        String heading = exportToBlocks.heading();
        String comment = exportToBlocks.comment();
        String tooltip = exportToBlocks.tooltip();
        String methodLookupString = BlocksClassFilter.getLookupString(method);
        methodLookupStrings.add(methodLookupString);
        xmlToolbox
            .append("<block type=\"").append(blockType).append("\">\n")
            .append("<field name=\"CLASS_NAME\">").append(userVisibleClassName).append("</field>")
            .append("<field name=\"METHOD_NAME\">").append(methodName).append("</field>")
            .append("<mutation")
            .append(" methodLookupString=\"").append(methodLookupString).append("\"")
            .append(" fullClassName=\"").append(fullClassName).append("\"")
            .append(" simpleName=\"").append(enclosingClass.getSimpleName()).append("\"")
            .append(" parameterCount=\"").append(parameterTypes.length).append("\"")
            .append(" returnType=\"").append(returnType).append("\"")
            .append(" color=\"").append(color).append("\"")
            .append(" heading=\"").append(escapeForXml(heading)).append("\"")
            .append(" comment=\"").append(escapeForXml(comment)).append("\"")
            .append(" tooltip=\"").append(escapeForXml(tooltip)).append("\"")
            .append(" accessMethod=\"").append(accessMethod(false, method.getReturnType())).append("\"")
            .append(" convertReturnValue=\"").append(convertReturnValue(method.getReturnType())).append("\"");
        processMethodArguments(xmlToolbox, parameterTypes, getParameterLabels(method), getParameterDefaultValues(method));
        // Note that processMethodArguments terminates the mutation and block tags.
      }

      // Add blocks for enums.
      Set<Class<? extends Enum>> enumClasses = enumClassesByEnclosingClass.get(enclosingClass);
      if (enumClasses != null) {
        for (Class<? extends Enum> enumClass : enumClasses) {
          addExportedEnum(enumClass, xmlToolbox, additionalReservedWordsForFtcJava);
        }
      }

      xmlToolbox.append("</category>\n");
    }

    Set<Class> exportedClasses = methodsByClass.keySet();
    Set<Class<? extends HardwareDevice>> hardwareClasses = blocksClassFilter.getHardwareMethodsByClass().keySet();
    for (Map.Entry<Class, Set<Class<? extends Enum>>> entry : enumClassesByEnclosingClass.entrySet()) {
      Class enclosingClass = entry.getKey();
      if (exportedClasses.contains(enclosingClass) || hardwareClasses.contains(enclosingClass)) {
        // We already added the enums in this enclosing class because it also had exported methods.
        continue;
      }
      if (!addedJavaClassesCategory) {
        xmlToolbox.append("<category name=\"Java Classes\">\n");
        addedJavaClassesCategory = true;
      }
      // Add a category for the enclosing class (which might be the enum class).
      String fullClassName = enclosingClass.getName();
      String userVisibleClassName = getUserVisibleClassName(
          fullClassName, additionalReservedWordsForFtcJava);
      xmlToolbox.append("<category name=\"").append(userVisibleClassName).append("\">\n");

      Set<Class<? extends Enum>> enumClasses = entry.getValue();
      for (Class<? extends Enum> enumClass : enumClasses) {
        addExportedEnum(enumClass, xmlToolbox, additionalReservedWordsForFtcJava);
      }

      xmlToolbox.append("</category>\n");
    }

    if (addedJavaClassesCategory) {
      xmlToolbox.append("</category>\n");
    }
  }

  private static void addExportedEnum(Class<? extends Enum> enumClass, StringBuilder xmlToolbox, Set<String> additionalReservedWordsForFtcJava) {
    ExportEnumToBlocks exportEnumToBlocks = enumClass.getAnnotation(ExportEnumToBlocks.class);
    if (exportEnumToBlocks == null) {
      return;
    }
    String fullClassName = enumClass.getName();
    String userVisibleEnumName = getUserVisibleEnumName(
        fullClassName, additionalReservedWordsForFtcJava);
    int color = exportEnumToBlocks.color();
    Object[] enumValues = enumClass.getEnumConstants();

    StringBuilder sb = new StringBuilder()
        .append("<block type=\"").append("misc_enumJava").append("\">\n")
        .append("<field name=\"ENUM_NAME\">").append(userVisibleEnumName).append("</field>")
        .append("<mutation")
        .append(" fullClassName=\"").append(fullClassName).append("\"")
        .append(" color=\"").append(color).append("\"")
        .append(" enumValueCount=\"").append(enumValues.length).append("\"");
    for (int i = 0; i < enumValues.length; i++) {
      sb.append(" enumValue").append(i).append("=\"").append(enumValues[i].toString()).append("\"");
    }
    sb.append("/>"); // end of mutation
    String blockDefinitionStart = sb.toString();
    String blockDefinitionEnd = "</block>\n";

    for (Object enumValue : enumValues) {
      xmlToolbox
          .append(blockDefinitionStart)
          .append("<field name=\"ENUM_VALUE\">")
          .append(enumValue)
          .append("</field>\n")
          .append(blockDefinitionEnd);

      if (enumValues.length > 4) {
        // If there are more than 4 enum values, we only put one block in the toolbox.
        break;
      }
    }
  }

  private static String getUserVisibleClassName(String fullClassName, Set<String> additionalReservedWordsForFtcJava) {
    String className = fullClassName;
    if (className.startsWith("org.firstinspires.ftc.teamcode.") && className.lastIndexOf('.') == 30) {
      className = className.substring(31);
      additionalReservedWordsForFtcJava.add(className);
    }
    return className.replace('$', '.');
  }

  private static String getUserVisibleEnumName(String fullClassName, Set<String> additionalReservedWordsForFtcJava) {
    String className = fullClassName;
    int lastDot = className.lastIndexOf('.');
    if (lastDot != -1) {
      className = className.substring(lastDot + 1);
      int firstDollar = className.indexOf('$');
      if (firstDollar != -1) {
        String outerClassName = className.substring(0, firstDollar);
        additionalReservedWordsForFtcJava.add(outerClassName);
      }
    }
    return className.replace('$', '.');
  }

  private static void processMethodArguments(StringBuilder xmlToolbox, Class[] parameterTypes, String[] parameterLabels,
      String[] parameterDefaultValues) {
    StringBuilder argValues = new StringBuilder();
    int i = 0;
    List<String> gamepads = new ArrayList<>();
    for (Class parameterType : parameterTypes) {
      xmlToolbox.append(" argLabel").append(i).append("=\"").append(escapeForXml(parameterLabels[i])).append("\"");
      String argType = parameterType.getName();
      xmlToolbox.append(" argType").append(i).append("=\"").append(escapeForXml(argType)).append("\"");
      String argAuto = parameterProvidedAutomatically(parameterType, parameterLabels[i], gamepads);
      xmlToolbox.append(" argAuto").append(i).append("=\"").append(argAuto != null ? escapeForXml(argAuto) : "").append("\"");

      String shadow = null;
      if (argAuto != null) {
        // No socket if parameter is provided automatically.
      } else if (argType.equals("boolean")
          || argType.equals("java.lang.Boolean")) {
        shadow = ToolboxUtil.makeBooleanShadow(Boolean.parseBoolean(parameterDefaultValues[i]));
      } else if (argType.equals("char")
          || argType.equals("java.lang.Character")) {
        shadow = ToolboxUtil.makeTextShadow(
            parameterDefaultValues[i].isEmpty() ? "A" : parameterDefaultValues[i].substring(0, 1));
      } else if (argType.equals("java.lang.String")) {
        shadow = ToolboxUtil.makeTextShadow(parameterDefaultValues[i]);
      } else if (argType.equals("byte")
          || argType.equals("java.lang.Byte")) {
        byte defaultValue = 0;
        if (!parameterDefaultValues[i].isEmpty()) {
          try {
            defaultValue = Byte.parseByte(parameterDefaultValues[i]);
          } catch (NumberFormatException e) {
          }
        }
        shadow = ToolboxUtil.makeNumberShadow(defaultValue);
      } else if (argType.equals("short")
          || argType.equals("java.lang.Short")) {
        short defaultValue = 0;
        if (!parameterDefaultValues[i].isEmpty()) {
          try {
            defaultValue = Short.parseShort(parameterDefaultValues[i]);
          } catch (NumberFormatException e) {
          }
        }
        shadow = ToolboxUtil.makeNumberShadow(defaultValue);
      } else if (argType.equals("int")
          || argType.equals("java.lang.Integer")) {
        int defaultValue = 0;
        if (!parameterDefaultValues[i].isEmpty()) {
          try {
            defaultValue = Integer.parseInt(parameterDefaultValues[i]);
          } catch (NumberFormatException e) {
          }
        }
        shadow = ToolboxUtil.makeNumberShadow(defaultValue);
      } else if (argType.equals("long")
          || argType.equals("java.lang.Long")) {
        long defaultValue = 0;
        if (!parameterDefaultValues[i].isEmpty()) {
          try {
            defaultValue = Long.parseLong(parameterDefaultValues[i]);
          } catch (NumberFormatException e) {
          }
        }
        shadow = ToolboxUtil.makeNumberShadow(defaultValue);
      } else if (argType.equals("float")
          || argType.equals("java.lang.Float")
          || argType.equals("double")
          || argType.equals("java.lang.Double")) {
        double defaultValue = 0;
        if (!parameterDefaultValues[i].isEmpty()) {
          try {
            defaultValue = Double.parseDouble(parameterDefaultValues[i]);
          } catch (NumberFormatException e) {
          }
        }
        shadow = ToolboxUtil.makeNumberShadow(defaultValue);
      } else if (argType.equals(AngleUnit.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], AngleUnit.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit", "ANGLE_UNIT", defaultValue);
      } else if (argType.equals(AxesOrder.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], AxesOrder.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "axesOrder")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "axesOrder", "AXES_ORDER", defaultValue);
      } else if (argType.equals(AxesReference.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], AxesReference.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "axesReference")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "axesReference", "AXES_REFERENCE", defaultValue);
      } else if (argType.equals(Axis.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], Axis.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "axis")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "axis", "AXIS", defaultValue);
      } else if (argType.equals(BNO055IMU.AccelUnit.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], BNO055IMU.AccelUnit.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("bno055imuParameters", "accelUnit")
            : ToolboxUtil.makeTypedEnumShadow("bno055imuParameters", "accelUnit", "ACCEL_UNIT", defaultValue);
      } else if (argType.equals(BNO055IMU.SensorMode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], BNO055IMU.SensorMode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("bno055imuParameters", "sensorMode")
            : ToolboxUtil.makeTypedEnumShadow("bno055imuParameters", "sensorMode", "SENSOR_MODE", defaultValue);
      } else if (argType.equals(BNO055IMU.SystemStatus.class.getName())) {
          String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], BNO055IMU.SystemStatus.class);
          shadow = (defaultValue == null)
              ? ToolboxUtil.makeTypedEnumShadow("bno055imu", "systemStatus")
              : ToolboxUtil.makeTypedEnumShadow("bno055imu", "systemStatus", "SYSTEM_STATUS", defaultValue);
      } else if (argType.equals(CachingOctoQuad.CachingMode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], CachingOctoQuad.CachingMode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("octoquad", "cachingMode")
            : ToolboxUtil.makeTypedEnumShadow("octoquad", "cachingMode", "CACHING_MODE", defaultValue);
      } else if (argType.equals(CompassSensor.CompassMode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], CompassSensor.CompassMode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("compassSensor", "compassMode")
            : ToolboxUtil.makeTypedEnumShadow("compassSensor", "compassMode", "COMPASS_MODE", defaultValue);
      } else if (argType.equals(CurrentUnit.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], CurrentUnit.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "currentUnit")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "currentUnit", "CURRENT_UNIT", defaultValue);
      } else if (argType.equals(DcMotor.RunMode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], DcMotor.RunMode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("dcMotor", "runMode")
            : ToolboxUtil.makeTypedEnumShadow("dcMotor", "runMode", "RUN_MODE", defaultValue);
      } else if (argType.equals(DcMotor.ZeroPowerBehavior.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], DcMotor.ZeroPowerBehavior.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("dcMotor", "zeroPowerBehavior")
            : ToolboxUtil.makeTypedEnumShadow("dcMotor", "zeroPowerBehavior", "ZERO_POWER_BEHAVIOR", defaultValue);
      } else if (argType.equals(DcMotorSimple.Direction.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], DcMotorSimple.Direction.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("dcMotor", "direction")
            : ToolboxUtil.makeTypedEnumShadow("dcMotor", "direction", "DIRECTION", defaultValue);
      } else if (argType.equals(DigitalChannel.Mode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], DigitalChannel.Mode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("digitalChannel", "mode")
            : ToolboxUtil.makeTypedEnumShadow("digitalChannel", "mode", "MODE", defaultValue);
      } else if (argType.equals(DistanceUnit.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], DistanceUnit.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit", "DISTANCE_UNIT", defaultValue);
      } else if (argType.equals(ElapsedTime.Resolution.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], ElapsedTime.Resolution.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("elapsedTime2", "resolution")
            : ToolboxUtil.makeTypedEnumShadow("elapsedTime2", "resolution", "RESOLUTION", defaultValue);
      } else if (argType.equals(GoBildaPinpointDriver.DeviceStatus.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], GoBildaPinpointDriver.DeviceStatus.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "deviceStatus")
            : ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "deviceStatus", "DEVICE_STATUS", defaultValue);
      } else if (argType.equals(GoBildaPinpointDriver.EncoderDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], GoBildaPinpointDriver.EncoderDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "encoderDirection")
            : ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "encoderDirection", "ENCODER_DIRECTION", defaultValue);
      } else if (argType.equals(GoBildaPinpointDriver.GoBildaOdometryPods.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], GoBildaPinpointDriver.GoBildaOdometryPods.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "goBildaOdometryPods")
            : ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "goBildaOdometryPods", "ODOMETRY_PODS", defaultValue);
      } else if (argType.equals(GoBildaPinpointDriver.ReadData.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], GoBildaPinpointDriver.ReadData.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "readData")
            : ToolboxUtil.makeTypedEnumShadow("goBildaPinpoint", "readData", "READ_DATA", defaultValue);
      } else if (argType.equals(HuskyLens.Algorithm.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], HuskyLens.Algorithm.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("huskyLens", "algorithm")
            : ToolboxUtil.makeTypedEnumShadow("huskyLens", "algorithm", "ALGORITHM", defaultValue);
      } else if (argType.equals(IrSeekerSensor.Mode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], IrSeekerSensor.Mode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("irSeekerSensor", "mode")
            : ToolboxUtil.makeTypedEnumShadow("irSeekerSensor", "mode", "MODE", defaultValue);
      } else if (argType.equals(ModernRoboticsI2cGyro.HeadingMode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], ModernRoboticsI2cGyro.HeadingMode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("gyroSensor", "headingMode")
            : ToolboxUtil.makeTypedEnumShadow("gyroSensor", "headingMode", "HEADING_MODE", defaultValue);
      } else if (argType.equals(MotorControlAlgorithm.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], MotorControlAlgorithm.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("pidfCoefficients", "motorControlAlgorithm")
            : ToolboxUtil.makeTypedEnumShadow("pidfCoefficients", "motorControlAlgorithm", "MOTOR_CONTROL_ALGORITHM", defaultValue);
      } else if (argType.equals(OctoQuadBase.ChannelBankConfig.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], OctoQuadBase.ChannelBankConfig.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("octoquad", "channelBankConfig")
            : ToolboxUtil.makeTypedEnumShadow("octoquad", "channelBankConfig", "CHANNEL_BANK_CONFIG", defaultValue);
      } else if (argType.equals(OctoQuadBase.EncoderDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], OctoQuadBase.EncoderDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("octoquad", "encoderDirection")
            : ToolboxUtil.makeTypedEnumShadow("octoquad", "encoderDirection", "ENCODER_DIRECTION", defaultValue);
      } else if (argType.equals(OctoQuadBase.I2cRecoveryMode.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], OctoQuadBase.I2cRecoveryMode.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("octoquad", "i2cRecoveryMode")
            : ToolboxUtil.makeTypedEnumShadow("octoquad", "i2cRecoveryMode", "I2C_RECOVERY_MODE", defaultValue);
      } else if (argType.equals(Servo.Direction.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], Servo.Direction.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("servo", "direction")
            : ToolboxUtil.makeTypedEnumShadow("servo", "direction", "DIRECTION", defaultValue);
      } else if (argType.equals(ServoController.PwmStatus.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], ServoController.PwmStatus.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("servoController", "pwmStatus")
            : ToolboxUtil.makeTypedEnumShadow("servoController", "pwmStatus", "PWM_STATUS", defaultValue);
      } else if (argType.equals(Telemetry.DisplayFormat.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], Telemetry.DisplayFormat.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("telemetry", "displayFormat")
            : ToolboxUtil.makeTypedEnumShadow("telemetry", "displayFormat", "DISPLAY_FORMAT", defaultValue);
      } else if (argType.equals(TempUnit.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], TempUnit.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "tempUnit")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "tempUnit", "TEMP_UNIT", defaultValue);
      } else if (argType.equals(BuiltinCameraDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], BuiltinCameraDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "builtinCameraDirection")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "builtinCameraDirection", "CAMERA_DIRECTION", defaultValue);
      } else if (argType.equals(RevHubOrientationOnRobot.LogoFacingDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], RevHubOrientationOnRobot.LogoFacingDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("revHubOrientationOnRobot", "logoFacingDirection")
            : ToolboxUtil.makeTypedEnumShadow("revHubOrientationOnRobot", "logoFacingDirection", "LOGO_FACING_DIRECTION", defaultValue);
      } else if (argType.equals(RevHubOrientationOnRobot.UsbFacingDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], RevHubOrientationOnRobot.UsbFacingDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("revHubOrientationOnRobot", "usbFacingDirection")
            : ToolboxUtil.makeTypedEnumShadow("revHubOrientationOnRobot", "usbFacingDirection", "USB_FACING_DIRECTION", defaultValue);
      } else if (argType.equals(Rev9AxisImuOrientationOnRobot.LogoFacingDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], Rev9AxisImuOrientationOnRobot.LogoFacingDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("rev9AxisImuOrientationOnRobot", "logoFacingDirection")
            : ToolboxUtil.makeTypedEnumShadow("rev9AxisImuOrientationOnRobot", "logoFacingDirection", "LOGO_FACING_DIRECTION", defaultValue);
      } else if (argType.equals(Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("rev9AxisImuOrientationOnRobot", "i2cPortFacingDirection")
            : ToolboxUtil.makeTypedEnumShadow("rev9AxisImuOrientationOnRobot", "i2cPortFacingDirection", "I2C_PORT_FACING_DIRECTION", defaultValue);
      } else if (argType.equals(UnnormalizedAngleUnit.class.getName())) {
        String defaultValue = parseEnumDefaultValue(parameterDefaultValues[i], UnnormalizedAngleUnit.class);
        shadow = (defaultValue == null)
            ? ToolboxUtil.makeTypedEnumShadow("navigation", "unnormalizedAngleUnit")
            : ToolboxUtil.makeTypedEnumShadow("navigation", "unnormalizedAngleUnit", "UNNORMALIZED_ANGLE_UNIT", defaultValue);
      } else {
        // Leave other sockets empty?
      }
      if (shadow != null) {
        argValues
            .append("<value name=\"ARG" + i + "\">")
            .append(shadow)
            .append("</value>\n");
      }
      i++;
    }
    xmlToolbox
        .append("/>") // end of mutation
        .append(argValues)
        .append("</block>\n");
  }

  private static <T extends Enum<T>> String parseEnumDefaultValue(String s, Class<T> enumClass) {
    // First, try the string as is.
    try {
      Enum.valueOf(enumClass, s);
      return s;
    } catch (IllegalArgumentException e) {
    }
    // Second, try the string converted to upper case.
    s = s.toUpperCase(Locale.ENGLISH);
    try {
      Enum.valueOf(enumClass, s);
      return s;
    } catch (IllegalArgumentException e) {
    }
    return null;
  }

  public static String[] getParameterLabels(Method method) {
    ExportToBlocks exportToBlocks = method.getAnnotation(ExportToBlocks.class);
    String[] parameterLabels;
    if (exportToBlocks != null) {
      parameterLabels = exportToBlocks.parameterLabels();
    } else {
      parameterLabels = new String[0];
    }
    int length = method.getParameterTypes().length;
    if (parameterLabels.length != length) {
      parameterLabels = new String[length];
      for (int i = 0; i < parameterLabels.length; i++) {
        parameterLabels[i] = "";
      }
    }
    return parameterLabels;
  }

  public static String[] getParameterDefaultValues(Method method) {
    ExportToBlocks exportToBlocks = method.getAnnotation(ExportToBlocks.class);
    String[] parameterDefaultValues;
    if (exportToBlocks != null) {
      parameterDefaultValues = exportToBlocks.parameterDefaultValues();
    } else {
      parameterDefaultValues = new String[0];
    }
    int length = method.getParameterTypes().length;
    if (parameterDefaultValues.length != length) {
      parameterDefaultValues = new String[length];
      for (int i = 0; i < parameterDefaultValues.length; i++) {
        parameterDefaultValues[i] = "";
      }
    }
    return parameterDefaultValues;
  }

  private static String accessMethod(boolean hardware, Class returnType) {
    if (returnType.equals(boolean.class) ||
        returnType.equals(Boolean.class)) {
      return hardware ? "callHardware_boolean" : "callJava_boolean";
    } else if (
        returnType.equals(char.class) ||
        returnType.equals(Character.class) ||
        returnType.equals(String.class) ||
        returnType.equals(byte.class) ||
        returnType.equals(Byte.class) ||
        returnType.equals(short.class) ||
        returnType.equals(Short.class) ||
        returnType.equals(int.class) ||
        returnType.equals(Integer.class) ||
        returnType.equals(long.class) ||
        returnType.equals(Long.class) ||
        returnType.equals(float.class) ||
        returnType.equals(Float.class) ||
        returnType.equals(double.class) ||
        returnType.equals(Double.class) ||
        returnType.isEnum()) {
      return hardware ? "callHardware_String" : "callJava_String";
    }
    return hardware ? "callHardware" : "callJava";
  }

  private static String convertReturnValue(Class returnType) {
    if (returnType.equals(byte.class) ||
        returnType.equals(Byte.class) ||
        returnType.equals(short.class) ||
        returnType.equals(Short.class) ||
        returnType.equals(int.class) ||
        returnType.equals(Integer.class) ||
        returnType.equals(long.class) ||
        returnType.equals(Long.class) ||
        returnType.equals(float.class) ||
        returnType.equals(Float.class) ||
        returnType.equals(double.class) ||
        returnType.equals(Double.class)) {
      return "Number";
    }
    return "";
  }

  private static String parameterProvidedAutomatically(Class parameterType, String parameterLabel, List<String> gamepads) {
    // Return the value that should be used for the parameter when blocks is exported to java.
    // For Javascript, null is used since the real value is determined in MiscAccess.java
    if (parameterType.equals(LinearOpMode.class) ||
        parameterType.equals(OpMode.class)) {
      return "this";
    } else if (parameterType.equals(HardwareMap.class)) {
      return "hardwareMap";
    } else if (parameterType.equals(Telemetry.class)) {
      return "telemetry";
    } else if (parameterType.equals(Gamepad.class)) {
      // If the parameter label is gamepad1 or gamepad2, return that.
      if (parameterLabel.equals("gamepad1") || parameterLabel.equals("gamepad2")) {
        return parameterLabel;
      }
      // Otherwise, return the first element in the gamepads list. This will be "gamepad1" for the
      // first Gamepad parameter and "gamepad2" for the second Gamepad parameter.
      if (gamepads.isEmpty()) {
        gamepads.add("gamepad1");
        gamepads.add("gamepad2");
      }
      return gamepads.remove(0);
    }
    return null;
  }

  /**
   * Returns a warning that is shown to the user if they try to create a Blocks OpMode using a
   * sample that requires a capability that is not satisfied by the current robot configuration.
   * For example, if the user tries to create a new OpMode based on the sample
   * ConceptAprilTagSwitchableCameras and the robot does not have multiple webcams, an appropriate
   * warning will be shown.
   */
  private static String getCapabilityWarning(Capability capability) {
    switch (capability) {
      case SWITCHABLE_CAMERA:
        return "The current configuration does not have multiple webcams.";
      case VISION:
        return "The current configuration has no webcam.";
    }
    return null;
  }

  @SuppressWarnings("deprecation")
  public static Map<Capability, Boolean> getCapabilities(HardwareItemMap hardwareItemMap) {
    Map<Capability, Boolean> capabilities = new HashMap<>();
    boolean hasBuiltinCamera = Device.isRevControlHub()
        ? false // A Control Hub has no builtin cameras.
        : AppUtil.getDefContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    int numberOfWebcams = hardwareItemMap.getHardwareItems(HardwareType.WEBCAM_NAME).size();
    boolean webcam = numberOfWebcams > 0;
    boolean switchableCamera = numberOfWebcams > 1;
    capabilities.put(Capability.BUILTIN_CAMERA, hasBuiltinCamera);
    capabilities.put(Capability.WEBCAM, webcam);
    capabilities.put(Capability.SWITCHABLE_CAMERA, switchableCamera);
    capabilities.put(Capability.VISION, hasBuiltinCamera || webcam);
    return capabilities;
  }

  /**
   * Appends the text content of an asset to the given StringBuilder.
   */
  private static void addAsset(StringBuilder sb, AssetManager assetManager, String assetName)
      throws IOException {
    FileUtil.readAsset(sb, assetManager, assetName);
  }

  /**
   * Appends the text content of an asset to the given StringBuilder, but skips xml comments.
   * Note that the xml comments have to be on a single line.
   */
  private static void addAssetSansXmlComments(StringBuilder xmlToolbox, AssetManager assetManager, String assetName)
      throws IOException {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(assetManager.open(assetName)))) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith("<!--") && trimmedLine.endsWith("-->")) {
          continue;
        }
        xmlToolbox.append(line).append("\n");
      }
    }
  }

  private static void addAssetWithPlaceholders(StringBuilder xmlToolbox,
      AssetManager assetManager, Set<String> additionalReservedWordsForFtcJava, Set<String> methodLookupStrings,
      Map<Capability, Boolean> capabilities, HardwareItemMap hardwareItemMap, String assetName) throws IOException {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(assetManager.open(assetName)))) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();

        line = line.replace("<placeholder_webcam_webcamNames/>", getWebcamBlocks(hardwareItemMap));
        line = line.replace("<placeholder_apriltag_exportedAprilTagLibraries/>",
            getExportedAprilTagLibraryBlocks(additionalReservedWordsForFtcJava, methodLookupStrings));

        String prefix = "<placeholder_";
        String suffix = "/>";
        if (line.startsWith(prefix) && line.endsWith(suffix)) {
          int startOfType = prefix.length();
          int endOfType = line.indexOf('_', startOfType);
          if (endOfType != -1) {
            String type = line.substring(startOfType, endOfType);
            String childAssetName = "toolbox/" +
                line.substring(endOfType + 1, line.length() - suffix.length()).trim() + ".xml";
            Boolean allowed = capabilities.get(Capability.fromPlaceholderType(type));
            if (allowed != null) {
              if (allowed) {
                addAssetWithPlaceholders(xmlToolbox,
                    assetManager, additionalReservedWordsForFtcJava, methodLookupStrings,
                    capabilities, hardwareItemMap, childAssetName);
              } else {
                RobotLog.w("Skipping " + childAssetName + " because capability \"" + type + "\" " +
                    "is not supported by this device and/or hardware.");
              }
            } else {
              RobotLog.e("Error: Skipping " + childAssetName + " because capability \"" + type + "\" " +
                  "is not recognized.");
            }
          } else {
            RobotLog.e("Error: Unable to parse placeholder \"" + line + "\"");
          }
        } else {
          xmlToolbox.append(line).append("\n");
        }
      }
    }
  }

  private static String getWebcamBlocks(HardwareItemMap hardwareItemMap) {
    StringBuilder webcamBlocks = new StringBuilder();
    List<HardwareItem> hardwareItemsForWebcam =
        hardwareItemMap.getHardwareItems(HardwareType.WEBCAM_NAME);
    for (HardwareItem hardwareItemForWebcam : hardwareItemsForWebcam) {
      webcamBlocks
          .append("<block type=\"navigation_webcamName\"><field name=\"WEBCAM_NAME\">")
          .append(hardwareItemForWebcam.deviceName)
          .append("</field></block>\n");
    }
    return webcamBlocks.toString();
  }

  private static String getExportedAprilTagLibraryBlocks(Set<String> additionalReservedWordsForFtcJava, Set<String> methodLookupStrings) {
    StringBuilder sb = new StringBuilder();
    Map<Class, Set<Method>> methodsByClass = blocksClassFilter.getAprilTagLibraryMethodsByClass();
    if (!methodsByClass.isEmpty()) {
      for (Map.Entry<Class, Set<Method>> entry : methodsByClass.entrySet()) {
        Class enclosingClass = entry.getKey();
        String fullClassName = enclosingClass.getName();
        String userVisibleClassName =  getUserVisibleClassName(fullClassName, additionalReservedWordsForFtcJava);
        Set<Method> methods = entry.getValue();
        for (Method method : methods) {
          ExportAprilTagLibraryToBlocks exportAprilTagLibraryToBlocks = method.getAnnotation(ExportAprilTagLibraryToBlocks.class);
          if (exportAprilTagLibraryToBlocks == null) {
            continue;
          }
          String returnType = method.getReturnType().getName();
          String blockType = returnType.equals("void") ? "misc_callJava_noReturn" : "misc_callJava_return";
          String methodName = method.getName();
          Class[] parameterTypes = method.getParameterTypes();
          int color = exportAprilTagLibraryToBlocks.color();
          String heading = exportAprilTagLibraryToBlocks.heading();
          String comment = exportAprilTagLibraryToBlocks.comment();
          String tooltip = exportAprilTagLibraryToBlocks.tooltip();
          String methodLookupString = BlocksClassFilter.getLookupString(method);
          methodLookupStrings.add(methodLookupString);
          sb
              .append("<block type=\"variables_set\"><field name=\"VAR\">myAprilTagLibrary</field><value name=\"VALUE\">\n")
              .append("<block type=\"").append(blockType).append("\">\n")
              .append("<field name=\"CLASS_NAME\">").append(userVisibleClassName).append("</field>")
              .append("<field name=\"METHOD_NAME\">").append(methodName).append("</field>")
              .append("<mutation")
              .append(" methodLookupString=\"").append(methodLookupString).append("\"")
              .append(" fullClassName=\"").append(fullClassName).append("\"")
              .append(" simpleName=\"").append(enclosingClass.getSimpleName()).append("\"")
              .append(" parameterCount=\"").append(parameterTypes.length).append("\"")
              .append(" returnType=\"").append(returnType).append("\"")
              .append(" color=\"").append(color).append("\"")
              .append(" heading=\"").append(escapeForXml(heading)).append("\"")
              .append(" comment=\"").append(escapeForXml(comment)).append("\"")
              .append(" tooltip=\"").append(escapeForXml(tooltip)).append("\"")
              .append(" accessMethod=\"").append(accessMethod(false, method.getReturnType())).append("\"")
              .append(" convertReturnValue=\"").append(convertReturnValue(method.getReturnType())).append("\"");
          processMethodArguments(sb, parameterTypes, getParameterLabels(method), getParameterDefaultValues(method));
          // Note that processMethodArguments terminates the mutation and block tags.
          sb.append("</value></block>"); // This terminates the variables_set block.
        }
      }
    }
    return sb.toString();
  }

  /**
   * Adds the category for Android functionality to the toolbox, iff there is at least one
   * sub-category supported by the Android device.
   */
  private static void addAndroidCategoriesToToolbox(
      StringBuilder xmlToolbox, AssetManager assetManager)
      throws IOException {
    boolean hasAccelerometer = !sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty();
    boolean hasGyroscope = !sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).isEmpty();
    boolean hasMagneticField = !sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).isEmpty();

    StringBuilder sb = new StringBuilder();
    boolean empty = true;

    if (hasAccelerometer) {
      if (assetManager != null) {
        addAsset(sb, assetManager, "toolbox/android_accelerometer.xml");
        empty = false;
      }
    } else {
      RobotLog.w("Skipping toolbox/android_accelerometer.xml because this device does not have " +
          "an accelerometer.");
    }
    if (hasGyroscope) {
      if (assetManager != null) {
        addAsset(sb, assetManager, "toolbox/android_gyroscope.xml");
        empty = false;
      }
    } else {
      RobotLog.w("Skipping toolbox/android_gyroscope.xml because this device does not have " +
          "a gyroscope.");
    }
    if (hasAccelerometer && hasMagneticField) {
      if (assetManager != null) {
        addAsset(sb, assetManager, "toolbox/android_orientation.xml");
        empty = false;
      }
    } else {
      RobotLog.w("Skipping toolbox/android_gyroscope.xml because this device does not have " +
          "an accelerometer and a magnetic field sensor.");
    }
    if (assetManager != null) {
      addAsset(sb, assetManager, "toolbox/android_sound_pool.xml");
      addAsset(sb, assetManager, "toolbox/android_text_to_speech.xml");
      empty = false;
    }

    if (!empty) {
      xmlToolbox.append("<category name=\"Android\">\n")
          .append(sb)
          .append("</category>\n");
    }
  }

  /**
   * Adds the category for the given {@link HardwareType} to the toolbox, iff there is at least one
   * {@link HardwareItem} of that HardwareType.
   */
  private static void addHardwareCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType,
      List<HardwareItem> hardwareItems, AssetManager assetManager) throws IOException {
    if (hardwareItems != null && hardwareItems.size() > 0) {
      xmlToolbox.append("  <category name=\"").append(hardwareType.toolboxCategoryName)
          .append("\">\n");

      switch (hardwareType) {
        case ACCELERATION_SENSOR:
          addAccelerationSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case ANALOG_INPUT:
          addAnalogInputCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case BNO055IMU:
          addBNO055IMUCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case COLOR_RANGE_SENSOR:
          addColorRangeSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case COLOR_SENSOR:
          addColorSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case COMPASS_SENSOR:
          addCompassSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case CR_SERVO:
          addCRServoCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case DC_MOTOR:
          addDcMotorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case DIGITAL_CHANNEL:
          addDigitalChannelCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case DISTANCE_SENSOR:
          addDistanceSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case GOBILDA_PINPOINT:
          addGoBildaPinpointCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case GYRO_SENSOR:
          addGyroSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case HUSKY_LENS:
          addHuskyLensCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems, assetManager);
          break;
        case IMU:
          addImuCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems, assetManager);
          break;
        case IR_SEEKER_SENSOR:
          addIrSeekerSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case LED:
          addLedCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case LIMELIGHT_3A:
          addLimelight3ACategoryToToolbox(xmlToolbox, hardwareType, hardwareItems, assetManager);
          break;
        case LIGHT_SENSOR:
          addLightSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case MAX_SONAR_I2CXL:
          addMaxSonarI2CXLCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case MR_I2C_COMPASS_SENSOR:
          addMrI2cCompassSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);;
          break;
        case MR_I2C_RANGE_SENSOR:
          addMrI2cRangeSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);;
          break;
        case OCTOQUAD:
          addOctoQuadCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems, assetManager);
          break;
        case OPTICAL_DISTANCE_SENSOR:
          addOpticalDistanceSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case REV_BLINKIN_LED_DRIVER:
          addRevBlinkinLedDriverCategoryToToolbox(xmlToolbox, assetManager);
          break;
        case SERVO:
          addServoCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case SERVO_CONTROLLER:
          addServoControllerCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case SPARKFUN_LED_STICK:
          addSparkFunLEDStickCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems, assetManager);
          break;
        case SPARKFUN_OTOS:
          addSparkFunOTOSCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems, assetManager);
          break;
        case TOUCH_SENSOR:
          addTouchSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case ULTRASONIC_SENSOR:
          addUltrasonicSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        case VOLTAGE_SENSOR:
          addVoltageSensorCategoryToToolbox(xmlToolbox, hardwareType, hardwareItems);
          break;
        default:
          throw new IllegalArgumentException("Unexpected hardware type " + hardwareType);
      }

      xmlToolbox.append("  </category>\n");

      if (assetManager != null) {
        switch (hardwareType) {
          case BNO055IMU:
            addAsset(
                xmlToolbox, assetManager, "toolbox/bno055imu_parameters.xml");
            break;
          case SPARKFUN_OTOS:
            addAsset(xmlToolbox, assetManager, "toolbox/sparkfun_otos_pose2d.xml");
            addAsset(xmlToolbox, assetManager, "toolbox/sparkfun_otos_signalprocessconfig.xml");
            addAsset(xmlToolbox, assetManager, "toolbox/sparkfun_otos_status.xml");
            addAsset(xmlToolbox, assetManager, "toolbox/sparkfun_otos_version.xml");
            break;
        }
      }
    }
  }

  /**
   * Adds the category for acceleration sensor to the toolbox.
   */
  private static void addAccelerationSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Acceleration", "Acceleration");
    properties.put("XAccel", "Number");
    properties.put("YAccel", "Number");
    properties.put("ZAccel", "Number");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("toText", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for BNO055IMU to the toolbox.
   */
  private static void addBNO055IMUCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Acceleration", "Acceleration");
    properties.put("AngularOrientation", "Orientation");
    properties.put("AngularOrientationAxes", "Array");
    properties.put("AngularVelocity", "AngularVelocity");
    properties.put("AngularVelocityAxes", "Array");
    properties.put("CalibrationStatus", "String");
    properties.put("Gravity", "Acceleration");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    properties.put("LinearAcceleration", "Acceleration");
    properties.put("MagneticFieldStrength", "MagneticFlux");
    properties.put("OverallAcceleration", "Acceleration");
    properties.put("Position", "Position");
    properties.put("QuaternionOrientation", "Quaternion");
    properties.put("SystemError", "String");
    properties.put("SystemStatus", "SystemStatus");
    properties.put("Temperature", "Temperature");
    properties.put("Velocity", "Velocity");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("SystemStatus", ToolboxUtil.makeTypedEnumBlock(hardwareType, "systemStatus"));
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, enumBlocks);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> initializeArgs = new LinkedHashMap<String, String>();
    initializeArgs.put("PARAMETERS", ToolboxUtil.makeVariableGetBlock("parameters"));
    functions.put("initialize", initializeArgs);
    Map<String, String> startAccelerationIntegration_with1Args = new LinkedHashMap<String, String>();
    startAccelerationIntegration_with1Args.put("MS_POLL_INTERVAL", ToolboxUtil.makeNumberShadow(1000));
    functions.put("startAccelerationIntegration_with1", startAccelerationIntegration_with1Args);
    Map<String, String> startAccelerationIntegration_with3Args = new LinkedHashMap<String, String>();
    startAccelerationIntegration_with3Args.put("INITIAL_POSITION", ToolboxUtil.makeVariableGetBlock("position"));
    startAccelerationIntegration_with3Args.put("INITIAL_VELOCITY", ToolboxUtil.makeVariableGetBlock("velocity"));
    startAccelerationIntegration_with3Args.put("MS_POLL_INTERVAL", ToolboxUtil.makeNumberShadow(1000));
    functions.put("startAccelerationIntegration_with3", startAccelerationIntegration_with3Args);
    functions.put("stopAccelerationIntegration", null);
    functions.put("isSystemCalibrated", null);
    functions.put("isGyroCalibrated", null);
    functions.put("isAccelerometerCalibrated", null);
    functions.put("isMagnetometerCalibrated", null);
    Map<String, String> saveCalibrationDataArgs = new LinkedHashMap<String, String>();
    saveCalibrationDataArgs.put("FILE_NAME", ToolboxUtil.makeTextShadow("IMUCalibration.json"));
    functions.put("saveCalibrationData", saveCalibrationDataArgs);
    Map<String, String> getAngularVelocityArgs = new LinkedHashMap<String, String>();
    getAngularVelocityArgs.put("ANGLE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit"));
    functions.put("getAngularVelocity", getAngularVelocityArgs);
    Map<String, String> getAngularOrientationArgs = new LinkedHashMap<String, String>();
    getAngularOrientationArgs.put("AXES_REFERENCE", ToolboxUtil.makeTypedEnumShadow("navigation", "axesReference"));
    getAngularOrientationArgs.put("AXES_ORDER", ToolboxUtil.makeTypedEnumShadow("navigation", "axesOrder"));
    getAngularOrientationArgs.put("ANGLE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit"));
    functions.put("getAngularOrientation", getAngularOrientationArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for IMU to the toolbox.
   */
  private static void addImuCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems,
      AssetManager assetManager) throws IOException {
    String identifier = hardwareItems.get(0).identifier;

    // Initialization blocks
    Map<String, Map<String, String>> initFunctions = new TreeMap<String, Map<String, String>>();
    Map<String, String> initializeArgs = new LinkedHashMap<String, String>();
    initializeArgs.put("PARAMETERS", "<block type=\"imuParameters_create\"></block>");
    initFunctions.put("initialize", initializeArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, initFunctions,
                             null /* functionComments */, null /* variableSetters */, null /* enumBlocks */);

    // Property blocks
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("RobotOrientationAsQuaternion", "Quaternion");
    properties.put("RobotYawPitchRollAngles", "YawPitchRollAngles");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);

    // Function blocks
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> getRobotAngularVelocityArgs = new LinkedHashMap<String, String>();
    getRobotAngularVelocityArgs.put("ANGLE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit"));
    functions.put("getRobotAngularVelocity", getRobotAngularVelocityArgs);
    Map<String, String> getRobotOrientationArgs = new LinkedHashMap<String, String>();
    getRobotOrientationArgs.put("AXES_REFERENCE", ToolboxUtil.makeTypedEnumShadow("navigation", "axesReference", "AXES_REFERENCE", "INTRINSIC"));
    getRobotOrientationArgs.put("AXES_ORDER", ToolboxUtil.makeTypedEnumShadow("navigation", "axesOrder", "AXES_ORDER", "ZYX"));
    getRobotOrientationArgs.put("ANGLE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit"));
    functions.put("getRobotOrientation", getRobotOrientationArgs);
    functions.put("resetYaw", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);

    if (assetManager != null) {
      addAsset(xmlToolbox, assetManager, "toolbox/imu_orientation.xml");
    }
  }

  /**
   * Adds the category for analog input to the toolbox.
   */
  private static void addAnalogInputCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Voltage", "Number");
    properties.put("MaxVoltage", "Number");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);
  }

  /**
   * Adds the category for analog output to the toolbox.
   */
  private static void addAnalogOutputCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> setAnalogOutputVoltageArgs = new LinkedHashMap<String, String>();
    setAnalogOutputVoltageArgs.put("VOLTAGE", ToolboxUtil.makeNumberShadow(512));
    functions.put("setAnalogOutputVoltage_Number", setAnalogOutputVoltageArgs);
    Map<String, String> setAnalogOutputFrequencyArgs = new LinkedHashMap<String, String>();
    setAnalogOutputFrequencyArgs.put("FREQUENCY", ToolboxUtil.makeNumberShadow(100));
    functions.put("setAnalogOutputFrequency_Number", setAnalogOutputFrequencyArgs);
    Map<String, String> setAnalogOutputModeArgs = new LinkedHashMap<String, String>();
    setAnalogOutputModeArgs.put("MODE", ToolboxUtil.makeNumberShadow(0));
    functions.put("setAnalogOutputMode_Number", setAnalogOutputModeArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for color sensor to the toolbox.
   */
  private static void addColorSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Red", "Number");
    properties.put("Green", "Number");
    properties.put("Blue", "Number");
    properties.put("Alpha", "Number");
    properties.put("Argb", "Number");
    properties.put("Gain", "Number");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("Gain", new String[] { ToolboxUtil.makeNumberShadow(2) });
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> enableLedArgs = new LinkedHashMap<String, String>();
    enableLedArgs.put("ENABLE", ToolboxUtil.makeBooleanShadow(true));
    functions.put("enableLed_Boolean", enableLedArgs);
    functions.put("isLightOn", null);
    functions.put("getNormalizedColors", null);
    functions.put("toText", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for compass sensor to the toolbox.
   */
  private static void addCompassSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Direction", "Number");
    properties.put("CalibrationFailed", "Boolean");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> setModeArgs = new LinkedHashMap<String, String>();
    setModeArgs.put("COMPASS_MODE", ToolboxUtil.makeTypedEnumShadow(hardwareType, "compassMode"));
    functions.put("setMode_CompassMode", setModeArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for cr servo to the toolbox.
   */
  private static void addCRServoCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Direction", "Direction");
    properties.put("Power", "Number");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("Direction", ToolboxUtil.makeTypedEnumBlock(hardwareType, "direction"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put(
        "Direction", new String[] { ToolboxUtil.makeTypedEnumShadow(hardwareType, "direction") });
    setterValues.put("Power", new String[] {
      ToolboxUtil.makeNumberShadow(1),
      ToolboxUtil.makeNumberShadow(0)
    });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("setPwmEnable", null);
    functions.put("setPwmDisable", null);
    functions.put("isPwmEnabled", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for dc motor to the toolbox.
   */
  private static void addDcMotorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;
    String zero = ToolboxUtil.makeNumberShadow(0);
    String one = ToolboxUtil.makeNumberShadow(1);
    String five = ToolboxUtil.makeNumberShadow(5);
    String ten = ToolboxUtil.makeNumberShadow(10);
    String runMode = ToolboxUtil.makeTypedEnumShadow(hardwareType, "runMode");
    String zeroPowerBehavior = ToolboxUtil.makeTypedEnumShadow(hardwareType, "zeroPowerBehavior");
    String direction = ToolboxUtil.makeTypedEnumShadow(hardwareType, "direction");
    String angleUnit = ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit");
    String currentUnit = ToolboxUtil.makeTypedEnumShadow("navigation", "currentUnit");
    String runModeRunUsingEncoder =
        ToolboxUtil.makeTypedEnumShadow(hardwareType, "runMode", "RUN_MODE", "RUN_USING_ENCODER");

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("CurrentPosition", "Number");
    properties.put("Direction", "Direction");
    properties.put("Mode", "RunMode");
    properties.put("Power", "Number");
    properties.put("PowerFloat", "Boolean");
    properties.put("TargetPosition", "Number");
    properties.put("ZeroPowerBehavior", "ZeroPowerBehavior");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("Direction", ToolboxUtil.makeTypedEnumBlock(hardwareType, "direction"));
    enumBlocks.put("Mode", ToolboxUtil.makeTypedEnumBlock(hardwareType, "runMode"));
    enumBlocks.put("ZeroPowerBehavior", ToolboxUtil.makeTypedEnumBlock(hardwareType, "zeroPowerBehavior"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("Direction", new String[] { direction });
    setterValues.put("Mode", new String[] { runMode });
    setterValues.put("Power", new String[] { one, zero });
    setterValues.put("TargetPosition", new String[] { zero });
    setterValues.put("ZeroPowerBehavior", new String[] { zeroPowerBehavior });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("isBusy", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
    functions.clear();

    if (hardwareItems.size() > 1) {
      String identifier1 = hardwareItems.get(0).identifier;
      String identifier2 = hardwareItems.get(1).identifier;

      xmlToolbox.append("    <category name=\"" + DC_MOTOR_DUAL_CATEGORY_NAME + "\">\n");

      // Set power for both motors to 1.
      ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "Power", "Number",
          identifier1, one,
          identifier2, one);
      // Set power for both motors to 0.
      ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "Power", "Number",
          identifier1, zero,
          identifier2, zero);
      // Set run mode for both motors.
      ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "Mode", "RunMode",
          identifier1, runMode,
          identifier2, runMode);
      // Set target position for both motors.
      ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "TargetPosition", "Number",
          identifier1, zero,
          identifier2, zero);
      // Set zero power behavior for both motors.
      ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "ZeroPowerBehavior", "ZeroPowerBehavior",
          identifier1, zeroPowerBehavior,
          identifier2, zeroPowerBehavior);

      xmlToolbox.append("    </category>\n");
    }

    List<HardwareItem> hardwareItemsForDcMotorEx = getHardwareItemsForDcMotorEx(hardwareItems);
    if (!hardwareItemsForDcMotorEx.isEmpty()) {
      String identifierForDcMotorEx = hardwareItemsForDcMotorEx.get(0).identifier;

      xmlToolbox.append("    <category name=\"" + DC_MOTOR_EX_CATEGORY_NAME + "\">\n");

      properties.clear();
      properties.put("TargetPositionTolerance", "Number");
      properties.put("Velocity", "Number");
      setterValues.clear();
      setterValues.put("TargetPositionTolerance", new String[] { ten });
      setterValues.put("Velocity", new String[] { ten });
      ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifierForDcMotorEx, properties,
          setterValues, null /* enumBlocks */);

      if (hardwareItemsForDcMotorEx.size() > 1) {
        String identifier1ForDcMotorEx = hardwareItemsForDcMotorEx.get(0).identifier;
        String identifier2ForDcMotorEx = hardwareItemsForDcMotorEx.get(1).identifier;

        xmlToolbox.append("    <category name=\"" + DC_MOTOR_DUAL_CATEGORY_NAME + "\">\n");

        // Set target position tolerance for both motors.
        ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "TargetPositionTolerance", "Number",
            identifier1ForDcMotorEx, ten,
            identifier2ForDcMotorEx, ten);

        // Set velocity for both motors.
        ToolboxUtil.addDualPropertySetters(xmlToolbox, hardwareType, "Velocity", "Number",
            identifier1ForDcMotorEx, ten,
            identifier2ForDcMotorEx, ten);

        xmlToolbox.append("    </category>\n");
      }

      functions = new LinkedHashMap<String, Map<String, String>>();
      functions.put("setMotorEnable", null);
      functions.put("setMotorDisable", null);
      functions.put("isMotorEnabled", null);
      Map<String, String> setVelocity_withAngleUnitArgs = new LinkedHashMap<String, String>();
      setVelocity_withAngleUnitArgs.put("ANGULAR_RATE", ten);
      setVelocity_withAngleUnitArgs.put("ANGLE_UNIT", angleUnit);
      functions.put("setVelocity_withAngleUnit", setVelocity_withAngleUnitArgs);
      Map<String, String> getVelocity_withAngleUnitArgs = new LinkedHashMap<String, String>();
      getVelocity_withAngleUnitArgs.put("ANGLE_UNIT", angleUnit);
      functions.put("getVelocity_withAngleUnit", getVelocity_withAngleUnitArgs);
      Map<String, String> setVelocityPIDFCoefficientsArgs = new LinkedHashMap<String, String>();
      setVelocityPIDFCoefficientsArgs.put("P", ToolboxUtil.makeNumberShadow(10));
      setVelocityPIDFCoefficientsArgs.put("I", ToolboxUtil.makeNumberShadow(10));
      setVelocityPIDFCoefficientsArgs.put("D", ToolboxUtil.makeNumberShadow(10));
      setVelocityPIDFCoefficientsArgs.put("F", ToolboxUtil.makeNumberShadow(10));
      functions.put("setVelocityPIDFCoefficients", setVelocityPIDFCoefficientsArgs);
      Map<String, String> setPositionPIDFCoefficientsArgs = new LinkedHashMap<String, String>();
      setPositionPIDFCoefficientsArgs.put("P", ToolboxUtil.makeNumberShadow(5));
      functions.put("setPositionPIDFCoefficients", setPositionPIDFCoefficientsArgs);
      Map<String, String> setPIDFCoefficientsArgs = new LinkedHashMap<String, String>();
      setPIDFCoefficientsArgs.put("RUN_MODE", runModeRunUsingEncoder);
      setPIDFCoefficientsArgs.put("PIDF_COEFFICIENTS", ToolboxUtil.makeVariableGetBlock("pidfCoefficients"));
      functions.put("setPIDFCoefficients", setPIDFCoefficientsArgs);
      Map<String, String> getPIDFCoefficientsArgs = new LinkedHashMap<String, String>();
      getPIDFCoefficientsArgs.put("RUN_MODE", runModeRunUsingEncoder);
      functions.put("getPIDFCoefficients", getPIDFCoefficientsArgs);
      Map<String, String> getCurrentArgs = new LinkedHashMap<String, String>();
      getCurrentArgs.put("CURRENT_UNIT", currentUnit);
      functions.put("getCurrent", getCurrentArgs);
      Map<String, String> setCurrentAlertArgs = new LinkedHashMap<String, String>();
      setCurrentAlertArgs.put("CURRENT", five);
      setCurrentAlertArgs.put("CURRENT_UNIT", currentUnit);
      functions.put("setCurrentAlert", setCurrentAlertArgs);
      Map<String, String> getCurrentAlertArgs = new LinkedHashMap<String, String>();
      getCurrentAlertArgs.put("CURRENT_UNIT", currentUnit);
      functions.put("getCurrentAlert", getCurrentAlertArgs);
      functions.put("isOverCurrent", null);
      ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifierForDcMotorEx, functions);

      xmlToolbox.append("    </category>\n");
    }

  }

  /**
   * Adds the category for digital channel to the toolbox.
   */
  private static void addDigitalChannelCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;
    String mode = ToolboxUtil.makeTypedEnumShadow(hardwareType, "mode");

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Mode", "DigitalChannelMode");
    properties.put("State", "Boolean");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("Mode", ToolboxUtil.makeTypedEnumBlock(hardwareType, "mode"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("Mode", new String[] { mode });
    setterValues.put("State", new String[] { ToolboxUtil.makeBooleanShadow(true) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);
  }

  /**
   * Adds the category for distance sensor to the toolbox.
   */
  private static void addDistanceSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    HardwareItem hardwareItem = hardwareItems.get(0);
    String identifier = hardwareItem.identifier;

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> getDistanceArgs = new LinkedHashMap<String, String>();
    getDistanceArgs.put("DISTANCE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit"));
    functions.put("getDistance", getDistanceArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for GoBildaPinpointDriver to the toolbox.
   */
  private static void addGoBildaPinpointCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;
    String zero = ToolboxUtil.makeNumberShadow(0);
    String encoderDirection = ToolboxUtil.makeTypedEnumShadow(hardwareType, "encoderDirection");
    String readData = ToolboxUtil.makeTypedEnumShadow(hardwareType, "readData");
    String goBildaOdometryPods = ToolboxUtil.makeTypedEnumShadow(hardwareType, "goBildaOdometryPods");
    String distanceUnit = ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit");
    String mm = ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit", "DISTANCE_UNIT", "MM");
    String angleUnit = ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit");
    String unnormalizedAngleUnit = ToolboxUtil.makeTypedEnumShadow("navigation", "unnormalizedAngleUnit");

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("YawScalar", "Number");
    properties.put("DeviceID", "Number");
    properties.put("DeviceVersion", "Number");
    properties.put("LoopTime", "Number");
    properties.put("EncoderX", "Number");
    properties.put("EncoderY", "Number");
    properties.put("Frequency", "Number");
    properties.put("DeviceStatus", "DeviceStatus");
    properties.put("Position", "Pose2D");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("YawScalar", new String[] { zero });
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put(
        "DeviceStatus",
        ToolboxUtil.makeTypedEnumBlock(hardwareType, "deviceStatus", "DEVICE_STATUS", "READY"));
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("update", null);
    Map<String, String> update_withReadDataArgs = new LinkedHashMap<String, String>();
    update_withReadDataArgs.put("READ_DATA", readData);
    functions.put("update_withReadData", update_withReadDataArgs);
    Map<String, String> setOffsetsArgs = new LinkedHashMap<String, String>();
    setOffsetsArgs.put("X_OFFSET", ToolboxUtil.makeNumberShadow(-104.0));
    setOffsetsArgs.put("Y_OFFSET", ToolboxUtil.makeNumberShadow(-168.0));
    setOffsetsArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("setOffsets", setOffsetsArgs);
    functions.put("recalibrateIMU", null);
    functions.put("resetPosAndIMU", null);
    Map<String, String> setEncoderDirectionsArgs = new LinkedHashMap<String, String>();
    setEncoderDirectionsArgs.put("X_ENCODER", encoderDirection);
    setEncoderDirectionsArgs.put("Y_ENCODER", encoderDirection);
    functions.put("setEncoderDirections", setEncoderDirectionsArgs);
    Map<String, String> setEncoderResolution_withPodsArgs = new LinkedHashMap<String, String>();
    setEncoderResolution_withPodsArgs.put("PODS", goBildaOdometryPods);
    functions.put("setEncoderResolution_withPods", setEncoderResolution_withPodsArgs);
    Map<String, String> setEncoderResolution_withTicksArgs = new LinkedHashMap<String, String>();
    // goBILDA Swingarm pod is ~13.26291192 ticks/mm.
    setEncoderResolution_withTicksArgs.put("TICKS_PER_UNIT", ToolboxUtil.makeNumberShadow(13.26291192));
    setEncoderResolution_withTicksArgs.put("DISTANCE_UNIT", mm);
    functions.put("setEncoderResolution_withTicks", setEncoderResolution_withTicksArgs);
    Map<String, String> setPositionArgs = new LinkedHashMap<String, String>();
    setPositionArgs.put("POSITION", ToolboxUtil.makeVariableGetBlock("myPose2D"));
    functions.put("setPosition", setPositionArgs);
    Map<String, String> setPosXArgs = new LinkedHashMap<String, String>();
    setPosXArgs.put("POS_X", zero);
    setPosXArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("setPosX", setPosXArgs);
    Map<String, String> setPosYArgs = new LinkedHashMap<String, String>();
    setPosYArgs.put("POS_Y", zero);
    setPosYArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("setPosY", setPosYArgs);
    Map<String, String> setHeadingArgs = new LinkedHashMap<String, String>();
    setHeadingArgs.put("HEADING", zero);
    setHeadingArgs.put("ANGLE_UNIT", angleUnit);
    functions.put("setHeading", setHeadingArgs);
    Map<String, String> getPosXArgs = new LinkedHashMap<String, String>();
    getPosXArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("getPosX", getPosXArgs);
    Map<String, String> getPosYArgs = new LinkedHashMap<String, String>();
    getPosYArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("getPosY", getPosYArgs);
    Map<String, String> getHeading_withAngleUnitArgs = new LinkedHashMap<String, String>();
    getHeading_withAngleUnitArgs.put("ANGLE_UNIT", angleUnit);
    functions.put("getHeading_withAngleUnit", getHeading_withAngleUnitArgs);
    Map<String, String> getHeading_withUnnormalizedAngleUnitArgs = new LinkedHashMap<String, String>();
    getHeading_withUnnormalizedAngleUnitArgs.put("UNNORMALIZED_ANGLE_UNIT", unnormalizedAngleUnit);
    functions.put("getHeading_withUnnormalizedAngleUnit", getHeading_withUnnormalizedAngleUnitArgs);
    Map<String, String> getVelXArgs = new LinkedHashMap<String, String>();
    getVelXArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("getVelX", getVelXArgs);
    Map<String, String> getVelYArgs = new LinkedHashMap<String, String>();
    getVelYArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("getVelY", getVelYArgs);
    Map<String, String> getHeadingVelocity_withUnnormalizedAngleUnitArgs = new LinkedHashMap<String, String>();
    getHeadingVelocity_withUnnormalizedAngleUnitArgs.put("UNNORMALIZED_ANGLE_UNIT", unnormalizedAngleUnit);
    functions.put("getHeadingVelocity_withUnnormalizedAngleUnit", getHeadingVelocity_withUnnormalizedAngleUnitArgs);
    Map<String, String> getXOffsetArgs = new LinkedHashMap<String, String>();
    getXOffsetArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("getXOffset", getXOffsetArgs);
    Map<String, String> getYOffsetArgs = new LinkedHashMap<String, String>();
    getYOffsetArgs.put("DISTANCE_UNIT", distanceUnit);
    functions.put("getYOffset", getYOffsetArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for gyro sensor to the toolbox.
   */
  private static void addGyroSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;
    String headingMode = ToolboxUtil.makeTypedEnumShadow(hardwareType, "headingMode");

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Heading", "Number");
    properties.put("HeadingMode", "HeadingMode");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    properties.put("IntegratedZValue", "Number");
    properties.put("RawX", "Number");
    properties.put("RawY", "Number");
    properties.put("RawZ", "Number");
    properties.put("RotationFraction", "Number");
    properties.put("AngularVelocityAxes", "Array");
    properties.put("AngularOrientationAxes", "Array");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("HeadingMode", ToolboxUtil.makeTypedEnumBlock(hardwareType, "headingMode"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("HeadingMode", new String[] { headingMode });
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("calibrate", null);
    functions.put("isCalibrating", null);
    functions.put("resetZAxisIntegrator", null);
    Map<String, String> getAngularVelocityArgs = new LinkedHashMap<String, String>();
    getAngularVelocityArgs.put("ANGLE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit"));
    functions.put("getAngularVelocity", getAngularVelocityArgs);
    Map<String, String> getAngularOrientationArgs = new LinkedHashMap<String, String>();
    getAngularOrientationArgs.put("AXES_REFERENCE", ToolboxUtil.makeTypedEnumShadow("navigation", "axesReference"));
    getAngularOrientationArgs.put("AXES_ORDER", ToolboxUtil.makeTypedEnumShadow("navigation", "axesOrder"));
    getAngularOrientationArgs.put("ANGLE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit"));
    functions.put("getAngularOrientation", getAngularOrientationArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for HuskyLens to the toolbox.
   */
  private static void addHuskyLensCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems,
      AssetManager assetManager) throws IOException {
    String identifier = hardwareItems.get(0).identifier;

    // Functions
    Map<String, Map<String, String>> functions = new LinkedHashMap<String, Map<String, String>>();
    Map<String, String> variableSetters = new HashMap<String, String>();
    functions.put("knock", null);
    Map<String, String> selectAlgorithmArgs = new LinkedHashMap<String, String>();
    selectAlgorithmArgs.put("ALGORITHM", ToolboxUtil.makeTypedEnumShadow(hardwareType, "algorithm", "ALGORITHM", "TAG_RECOGNITION"));
    functions.put("selectAlgorithm", selectAlgorithmArgs);
    functions.put("blocks", null);
    variableSetters.put("blocks", "myHuskyLensBlocks");
    Map<String, String> blocks_withIdArgs = new LinkedHashMap<String, String>();
    blocks_withIdArgs.put("ID", ToolboxUtil.makeNumberShadow(1));
    functions.put("blocks_withId", blocks_withIdArgs);
    variableSetters.put("blocks_withId", "myHuskyLensBlocks");
    functions.put("arrows", null);
    variableSetters.put("arrows", "myHuskyLensArrows");
    Map<String, String> arrows_withIdArgs = new LinkedHashMap<String, String>();
    arrows_withIdArgs.put("ID", ToolboxUtil.makeNumberShadow(1));
    functions.put("arrows_withId", arrows_withIdArgs);
    variableSetters.put("arrows_withId", "myHuskyLensArrows");
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions,
                             null /* functionComments */, variableSetters, null /* enumBlocks */);

    if (assetManager != null) {
      addAsset(xmlToolbox, assetManager, "toolbox/husky_lens_block.xml");
      addAsset(xmlToolbox, assetManager, "toolbox/husky_lens_arrow.xml");
    }
  }

  /**
   * Adds the category for IR seeker sensor to the toolbox.
   */
  private static void addIrSeekerSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;
    String mode = ToolboxUtil.makeTypedEnumShadow(hardwareType, "mode");
    String threshold = ToolboxUtil.makeNumberShadow(0.003);

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("SignalDetectedThreshold", "Number");
    properties.put("Mode", "IrSeekerSensorMode");
    properties.put("IsSignalDetected", "Boolean");
    properties.put("Angle", "Number");
    properties.put("Strength", "Number");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("Mode", ToolboxUtil.makeTypedEnumBlock(hardwareType, "mode"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("SignalDetectedThreshold", new String[] { threshold });
    setterValues.put("Mode", new String[] { mode });
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);
  }

  /**
   * Adds the category for LED to the toolbox.
   */
  private static void addLedCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> enableLedArgs = new LinkedHashMap<String, String>();
    enableLedArgs.put("ENABLE", ToolboxUtil.makeBooleanShadow(true));
    functions.put("enableLed_Boolean", enableLedArgs);
    functions.put("isLightOn", null);
    functions.put("on", null);
    functions.put("off", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for Limelight3A to the toolbox.
   */
  private static void addLimelight3ACategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems,
      AssetManager assetManager) throws IOException {
    String identifier = hardwareItems.get(0).identifier;

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("start", null);
    functions.put("pause", null);
    functions.put("stop", null);
    functions.put("isRunning", null);
    Map<String, String> setPollRateHzArgs = new LinkedHashMap<String, String>();
    setPollRateHzArgs.put("RATE_HZ", ToolboxUtil.makeNumberShadow(100));
    functions.put("setPollRateHz", setPollRateHzArgs);
    functions.put("getTimeSinceLastUpdate", null);
    functions.put("isConnected", null);
    functions.put("getLatestResult", null);
    functions.put("getStatus", null);
    functions.put("reloadPipeline", null);
    Map<String, String> pipelineSwitchArgs = new LinkedHashMap<String, String>();
    pipelineSwitchArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    functions.put("pipelineSwitch", pipelineSwitchArgs);

    Map<String, String> updatePythonInputs_with8DoublesArgs = new LinkedHashMap<String, String>();
    updatePythonInputs_with8DoublesArgs.put("INPUT1", ToolboxUtil.makeNumberShadow(1));
    updatePythonInputs_with8DoublesArgs.put("INPUT2", ToolboxUtil.makeNumberShadow(2));
    updatePythonInputs_with8DoublesArgs.put("INPUT3", ToolboxUtil.makeNumberShadow(3));
    updatePythonInputs_with8DoublesArgs.put("INPUT4", ToolboxUtil.makeNumberShadow(4));
    updatePythonInputs_with8DoublesArgs.put("INPUT5", ToolboxUtil.makeNumberShadow(5));
    updatePythonInputs_with8DoublesArgs.put("INPUT6", ToolboxUtil.makeNumberShadow(6));
    updatePythonInputs_with8DoublesArgs.put("INPUT7", ToolboxUtil.makeNumberShadow(7));
    updatePythonInputs_with8DoublesArgs.put("INPUT8", ToolboxUtil.makeNumberShadow(8));
    functions.put("updatePythonInputs_with8Doubles", updatePythonInputs_with8DoublesArgs);
    Map<String, String> updatePythonInputs_withArray = new LinkedHashMap<String, String>();
    updatePythonInputs_withArray.put("INPUTS", ToolboxUtil.makeVariableGetBlock("pythonInputs"));
    functions.put("updatePythonInputs_withArray", updatePythonInputs_withArray);
    Map<String, String> updateRobotOrientationArgs = new LinkedHashMap<String, String>();
    updateRobotOrientationArgs.put("YAW", ToolboxUtil.makeNumberShadow(45));
    functions.put("updateRobotOrientation", updateRobotOrientationArgs);
    functions.put("shutdown", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);

    if (assetManager != null) {
      addAsset(xmlToolbox, assetManager, "toolbox/limelight.xml");
    }
  }

  /**
   * Adds the category for light sensor to the toolbox.
   */
  private static void addLightSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("LightDetected", "Number");
    properties.put("RawLightDetected", "Number");
    properties.put("RawLightDetectedMax", "Number");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> enableLedArgs = new LinkedHashMap<String, String>();
    enableLedArgs.put("ENABLE", ToolboxUtil.makeBooleanShadow(true));
    functions.put("enableLed_Boolean", enableLedArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for ColorRangeSensor to the toolbox.
   */
  private static void addColorRangeSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Red", "Number");
    properties.put("Green", "Number");
    properties.put("Blue", "Number");
    properties.put("Alpha", "Number");
    properties.put("Argb", "Number");
    properties.put("Gain", "Number");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    properties.put("LightDetected", "Number");
    properties.put("RawLightDetected", "Number");
    properties.put("RawLightDetectedMax", "Number");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("Gain", new String[] { ToolboxUtil.makeNumberShadow(2) });
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> getDistanceArgs = new LinkedHashMap<String, String>();
    getDistanceArgs.put("UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit"));
    functions.put("getDistance_Number", getDistanceArgs);
    functions.put("getNormalizedColors", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  private static void addMaxSonarI2CXLCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> getDistanceSyncArgs = new LinkedHashMap<String, String>();
    getDistanceSyncArgs.put("DISTANCE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit"));
    functions.put("getDistanceSync", getDistanceSyncArgs);
    Map<String, String> getDistanceSync_withDelayArgs = new LinkedHashMap<String, String>();
    getDistanceSync_withDelayArgs.put("DELAY", ToolboxUtil.makeNumberShadow(50));
    getDistanceSync_withDelayArgs.put("DISTANCE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit"));
    functions.put("getDistanceSync_withDelay", getDistanceSync_withDelayArgs);
    Map<String, String> getDistanceAsyncArgs = new LinkedHashMap<String, String>();
    getDistanceAsyncArgs.put("DISTANCE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit"));
    functions.put("getDistanceAsync", getDistanceAsyncArgs);
    Map<String, String> getDistanceAsync_withDelayArgs = new LinkedHashMap<String, String>();
    getDistanceAsync_withDelayArgs.put("DELAY", ToolboxUtil.makeNumberShadow(50));
    getDistanceAsync_withDelayArgs.put("DISTANCE_UNIT", ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit"));
    functions.put("getDistanceAsync_withDelay", getDistanceAsync_withDelayArgs);
    Map<String, String> writeI2cAddrToSensorEEPROMArgs = new LinkedHashMap<String, String>();
    writeI2cAddrToSensorEEPROMArgs.put("ADDRESS", ToolboxUtil.makeNumberShadow(20));
    functions.put("writeI2cAddrToSensorEEPROM", writeI2cAddrToSensorEEPROMArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for ModernRoboticsI2cCompassSensor to the toolbox.
   */
  private static void addMrI2cCompassSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Direction", "Number");
    properties.put("XAccel", "Number");
    properties.put("YAccel", "Number");
    properties.put("ZAccel", "Number");
    properties.put("XMagneticFlux", "Number");
    properties.put("YMagneticFlux", "Number");
    properties.put("ZMagneticFlux", "Number");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> setModeArgs = new LinkedHashMap<String, String>();
    setModeArgs.put("COMPASS_MODE", ToolboxUtil.makeTypedEnumShadow(hardwareType, "compassMode"));
    functions.put("setMode_CompassMode", setModeArgs);
    functions.put("isCalibrating", null);
    functions.put("calibrationFailed", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for ModernRoboticsI2cRangeSensor to the toolbox.
   */
  private static void addMrI2cRangeSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("LightDetected", "Number");
    properties.put("RawLightDetected", "Number");
    properties.put("RawLightDetectedMax", "Number");
    properties.put("RawUltrasonic", "Number");
    properties.put("RawOptical", "Number");
    properties.put("CmUltrasonic", "Number");
    properties.put("CmOptical", "Number");
    properties.put("I2cAddress7Bit", "Number");
    properties.put("I2cAddress8Bit", "Number");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("I2cAddress7Bit", new String[] { ToolboxUtil.makeNumberShadow(8) });
    setterValues.put("I2cAddress8Bit", new String[] { ToolboxUtil.makeNumberShadow(16) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> getDistanceArgs = new LinkedHashMap<String, String>();
    getDistanceArgs.put("UNIT", ToolboxUtil.makeTypedEnumShadow(hardwareType, "distanceUnit"));
    functions.put("getDistance_Number", getDistanceArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for OctoQuad to the toolbox.
   */
  private static void addOctoQuadCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems,
      AssetManager assetManager) throws IOException {
    String identifier = hardwareItems.get(0).identifier;
    String channelBankConfig = ToolboxUtil.makeTypedEnumShadow(hardwareType, "channelBankConfig");
    String i2cRecoveryMode = ToolboxUtil.makeTypedEnumShadow(hardwareType, "i2cRecoveryMode");

    // Constants
    if (assetManager != null) {
      addAsset(xmlToolbox, assetManager, "toolbox/octoquad.xml");
    }

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("ChipId", "Number");
    properties.put("FirmwareVersionString", "String");
    properties.put("ChannelBankConfig", "ChannelBankConfig");
    properties.put("I2cRecoveryMode", "I2cRecoveryMode");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("ChannelBankConfig", ToolboxUtil.makeTypedEnumBlock(hardwareType, "channelBankConfig"));
    enumBlocks.put("I2cRecoveryMode", ToolboxUtil.makeTypedEnumBlock(hardwareType, "i2cRecoveryMode"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("ChannelBankConfig", new String[] { channelBankConfig });
    setterValues.put("I2cRecoveryMode", new String[] { i2cRecoveryMode });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);

    // Functions
    enumBlocks.clear();
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("resetAllPositions", null);
    Map<String, String> resetSinglePositionArgs = new LinkedHashMap<String, String>();
    resetSinglePositionArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    functions.put("resetSinglePosition", resetSinglePositionArgs);

    Map<String, String> setSingleEncoderDirectionArgs = new LinkedHashMap<String, String>();
    setSingleEncoderDirectionArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    setSingleEncoderDirectionArgs.put("DIRECTION", ToolboxUtil.makeTypedEnumShadow(hardwareType, "encoderDirection"));
    functions.put("setSingleEncoderDirection", setSingleEncoderDirectionArgs);

    Map<String, String> getSingleEncoderDirectionArgs = new LinkedHashMap<String, String>();
    getSingleEncoderDirectionArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    enumBlocks.put("getSingleEncoderDirection", ToolboxUtil.makeTypedEnumBlock(hardwareType, "encoderDirection"));
    functions.put("getSingleEncoderDirection", getSingleEncoderDirectionArgs);

    Map<String, String> setAllVelocitySampleIntervalsArgs = new LinkedHashMap<String, String>();
    setAllVelocitySampleIntervalsArgs.put("INTERVAL_MS", ToolboxUtil.makeNumberShadow(24));
    functions.put("setAllVelocitySampleIntervals", setAllVelocitySampleIntervalsArgs);

    Map<String, String> setSingleVelocitySampleIntervalArgs = new LinkedHashMap<String, String>();
    setSingleVelocitySampleIntervalArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    setSingleVelocitySampleIntervalArgs.put("INTERVAL_MS", ToolboxUtil.makeNumberShadow(25));
    functions.put("setSingleVelocitySampleInterval", setSingleVelocitySampleIntervalArgs);

    Map<String, String> getSingleVelocitySampleIntervalArgs = new LinkedHashMap<String, String>();
    getSingleVelocitySampleIntervalArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    functions.put("getSingleVelocitySampleInterval", getSingleVelocitySampleIntervalArgs);

    Map<String, String> setSingleChannelPulseWidthParamsArgs = new LinkedHashMap<String, String>();
    setSingleChannelPulseWidthParamsArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    setSingleChannelPulseWidthParamsArgs.put("MIN_WIDTH_US", ToolboxUtil.makeNumberShadow(1));
    setSingleChannelPulseWidthParamsArgs.put("MAX_WIDTH_US", ToolboxUtil.makeNumberShadow(1024));
    functions.put("setSingleChannelPulseWidthParams", setSingleChannelPulseWidthParamsArgs);

    functions.put("resetEverything", null);
    functions.put("saveParametersToFlash", null);

    Map<String, String> setCachingModeArgs = new LinkedHashMap<String, String>();
    setCachingModeArgs.put("MODE", ToolboxUtil.makeTypedEnumShadow(hardwareType, "cachingMode"));
    functions.put("setCachingMode", setCachingModeArgs);

    functions.put("refreshCache", null);

    Map<String, String> readSinglePosition_CachingArgs = new LinkedHashMap<String, String>();
    readSinglePosition_CachingArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    functions.put("readSinglePosition_Caching", readSinglePosition_CachingArgs);

    Map<String, String> readSingleVelocity_CachingArgs = new LinkedHashMap<String, String>();
    readSingleVelocity_CachingArgs.put("INDEX", ToolboxUtil.makeNumberShadow(0));
    functions.put("readSingleVelocity_Caching", readSingleVelocity_CachingArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions,
                 null /* functionComments */, null /* variableSetters */, enumBlocks);
  }

  /**
   * Adds the category for optical distance sensor to the toolbox.
   */
  private static void addOpticalDistanceSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("LightDetected", "Number");
    properties.put("RawLightDetected", "Number");
    properties.put("RawLightDetectedMax", "Number");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> enableLedArgs = new LinkedHashMap<String, String>();
    enableLedArgs.put("ENABLE", ToolboxUtil.makeBooleanShadow(true));
    functions.put("enableLed_Boolean", enableLedArgs);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for RevBlinkinLedDriver to the toolbox.
   */
  private static void addRevBlinkinLedDriverCategoryToToolbox(
      StringBuilder xmlToolbox, AssetManager assetManager) throws IOException {
    addAsset(xmlToolbox, assetManager, "toolbox/rev_blinkin_led_driver.xml");
  }

  /**
   * Adds the category for servo to the toolbox.
   */
  private static void addServoCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Direction", "Direction");
    properties.put("Position", "Number");
    Map<String, String> enumBlocks = new HashMap<String, String>();
    enumBlocks.put("Direction", ToolboxUtil.makeTypedEnumBlock(hardwareType, "direction"));
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put(
        "Direction", new String[] { ToolboxUtil.makeTypedEnumShadow(hardwareType, "direction") });
    setterValues.put("Position", new String[] { ToolboxUtil.makeNumberShadow(0) });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, enumBlocks);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> scaleRangeArgs = new LinkedHashMap<String, String>();
    scaleRangeArgs.put("MIN", ToolboxUtil.makeNumberShadow(0.2));
    scaleRangeArgs.put("MAX", ToolboxUtil.makeNumberShadow(0.8));
    functions.put("scaleRange_Number", scaleRangeArgs);
    functions.put("setPwmEnable", null);
    functions.put("setPwmDisable", null);
    functions.put("isPwmEnabled", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for servo controller to the toolbox.
   */
  private static void addServoControllerCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("PwmStatus", "PwmStatus");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    functions.put("pwmEnable", null);
    functions.put("pwmDisable", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for SparkFunLEDStick to the toolbox.
   */
  private static void addSparkFunLEDStickCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems,
      AssetManager assetManager) throws IOException {
    String identifier = hardwareItems.get(0).identifier;
    String zero = ToolboxUtil.makeNumberShadow(0);
    String one = ToolboxUtil.makeNumberShadow(1);
    String blue = "<shadow type=\"color_constant_Number\"><field name=\"CONSTANT\">BLUE</field></shadow>\n";
    String red = "<shadow type=\"color_constant_Number\"><field name=\"CONSTANT\">RED</field></shadow>\n";
    String twenty = ToolboxUtil.makeNumberShadow(20);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> setColor_withPositionArgs = new LinkedHashMap<String, String>();
    setColor_withPositionArgs.put("POSITION", one);
    setColor_withPositionArgs.put("COLOR", blue);
    functions.put("setColor_withPosition", setColor_withPositionArgs);
    Map<String, String> setColorArgs = new LinkedHashMap<String, String>();
    setColorArgs.put("COLOR", blue);
    functions.put("setColor", setColorArgs);
    Map<String, String> setColorsArgs = new LinkedHashMap<String, String>();
    setColorsArgs.put("COLORS", ToolboxUtil.makeVariableGetBlock("ledColors"));
    functions.put("setColors", setColorsArgs);
    Map<String, String> setBrightness_withPositionArgs = new LinkedHashMap<String, String>();
    setBrightness_withPositionArgs.put("POSITION", one);
    setBrightness_withPositionArgs.put("BRIGHTNESS", twenty);
    functions.put("setBrightness_withPosition", setBrightness_withPositionArgs);
    Map<String, String> setBrightnessArgs = new LinkedHashMap<String, String>();
    setBrightnessArgs.put("BRIGHTNESS", twenty);
    functions.put("setBrightness", setBrightnessArgs);
    functions.put("turnAllOff", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for SparkFunOTOS to the toolbox.
   */
  private static void addSparkFunOTOSCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems,
      AssetManager assetManager) throws IOException {
    String identifier = hardwareItems.get(0).identifier;
    String one = ToolboxUtil.makeNumberShadow(1);

    // Constants
    if (assetManager != null) {
      addAsset(xmlToolbox, assetManager, "toolbox/sparkfun_otos.xml");
    }

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Status", "Status");
    properties.put("Offset", "Pose2D");
    properties.put("Position", "Pose2D");
    properties.put("Acceleration", "Pose2D");
    properties.put("Velocity", "Pose2D");
    properties.put("PositionStdDev", "Pose2D");
    properties.put("AccelerationStdDev", "Pose2D");
    properties.put("VelocityStdDev", "Pose2D");
    properties.put("SignalProcessConfig", "SignalProcessConfig");
    properties.put("ImuCalibrationProgress", "Number");
    properties.put("LinearUnit", "DistanceUnit");
    properties.put("LinearScalar", "Number_ReturnBoolean");
    properties.put("AngularScalar", "Number_ReturnBoolean");
    properties.put("AngularUnit", "AngleUnit");
    Map<String, String[]> setterValues = new HashMap<String, String[]>();
    setterValues.put("Offset", new String[] { ToolboxUtil.makeVariableGetBlock("myPose") });
    setterValues.put("Position", new String[] { ToolboxUtil.makeVariableGetBlock("myPose") });
    setterValues.put("SignalProcessConfig", new String[] { ToolboxUtil.makeVariableGetBlock("mySignalProcessConfig") });
    setterValues.put("LinearScalar", new String[] { one });
    setterValues.put("LinearUnit", new String[] { ToolboxUtil.makeTypedEnumShadow("navigation", "distanceUnit") });
    setterValues.put("AngularScalar", new String[] { one });
    setterValues.put("AngularUnit", new String[] { ToolboxUtil.makeTypedEnumShadow("navigation", "angleUnit") });
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        setterValues, null /* enumBlocks */);

    // Functions
    Map<String, Map<String, String>> functions = new TreeMap<String, Map<String, String>>();
    Map<String, String> getPosVelAccArgs = new LinkedHashMap<String, String>();
    getPosVelAccArgs.put("POS", ToolboxUtil.makeVariableGetBlock("pos"));
    getPosVelAccArgs.put("VEL", ToolboxUtil.makeVariableGetBlock("vel"));
    getPosVelAccArgs.put("ACC", ToolboxUtil.makeVariableGetBlock("acc"));
    functions.put("getPosVelAcc", getPosVelAccArgs);
    Map<String, String> getPosVelAccStdDevArgs = new LinkedHashMap<String, String>();
    getPosVelAccStdDevArgs.put("POS_STD_DEV", ToolboxUtil.makeVariableGetBlock("posStdDev"));
    getPosVelAccStdDevArgs.put("VEL_STD_DEV", ToolboxUtil.makeVariableGetBlock("velStdDev"));
    getPosVelAccStdDevArgs.put("ACC_STD_DEV", ToolboxUtil.makeVariableGetBlock("accStdDev"));
    functions.put("getPosVelAccStdDev", getPosVelAccStdDevArgs);
    Map<String, String> getPosVelAccAndStdDevArgs = new LinkedHashMap<String, String>();
    getPosVelAccAndStdDevArgs.put("POS", ToolboxUtil.makeVariableGetBlock("pos"));
    getPosVelAccAndStdDevArgs.put("VEL", ToolboxUtil.makeVariableGetBlock("vel"));
    getPosVelAccAndStdDevArgs.put("ACC", ToolboxUtil.makeVariableGetBlock("acc"));
    getPosVelAccAndStdDevArgs.put("POS_STD_DEV", ToolboxUtil.makeVariableGetBlock("posStdDev"));
    getPosVelAccAndStdDevArgs.put("VEL_STD_DEV", ToolboxUtil.makeVariableGetBlock("velStdDev"));
    getPosVelAccAndStdDevArgs.put("ACC_STD_DEV", ToolboxUtil.makeVariableGetBlock("accStdDev"));
    functions.put("getPosVelAccAndStdDev", getPosVelAccAndStdDevArgs);
    Map<String, String> getVersionInfoArgs = new LinkedHashMap<String, String>();
    getVersionInfoArgs.put("HW_VERSION", ToolboxUtil.makeVariableGetBlock("hwVersion"));
    getVersionInfoArgs.put("FW_VERSION", ToolboxUtil.makeVariableGetBlock("fwVersion"));
    functions.put("getVersionInfo", getVersionInfoArgs);
    functions.put("begin", null);
    functions.put("calibrateImu", null);
    Map<String, String> calibrateImuArgs = new LinkedHashMap<String, String>();
    calibrateImuArgs.put("NUM_SAMPLES", ToolboxUtil.makeNumberShadow(255));
    calibrateImuArgs.put("WAIT_UNTIL_DONE", ToolboxUtil.makeBooleanShadow(true));
    functions.put("calibrateImu_withArgs", calibrateImuArgs);
    functions.put("isConnected", null);
    functions.put("selfTest", null);
    functions.put("resetTracking", null);
    ToolboxUtil.addFunctions(xmlToolbox, hardwareType, identifier, functions);
  }

  /**
   * Adds the category for touch sensor to the toolbox.
   */
  private static void addTouchSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("IsPressed", "Boolean");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);
  }

  /**
   * Adds the category for ultrasonic sensor to the toolbox.
   */
  private static void addUltrasonicSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("UltrasonicLevel", "Number");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);
  }

  /**
   * Adds the category for voltage sensor to the toolbox.
   */
  private static void addVoltageSensorCategoryToToolbox(
      StringBuilder xmlToolbox, HardwareType hardwareType, List<HardwareItem> hardwareItems) {
    String identifier = hardwareItems.get(0).identifier;

    // Properties
    SortedMap<String, String> properties = new TreeMap<String, String>();
    properties.put("Voltage", "Number");
    ToolboxUtil.addProperties(xmlToolbox, hardwareType, identifier, properties,
        null /* setterValues */, null /* enumBlocks */);
  }

  /**
   * Upgrades the given js content based on the given {@link HardwareItemMap}.
   */
  public static String upgradeJs(String jsContent, HardwareItemMap hardwareItemMap) {
    // In previous versions, identifier suffix AsBNO055IMU was AsAdafruitBNO055IMU.
    jsContent = replaceIdentifierSuffixInJs(jsContent,
        hardwareItemMap.getHardwareItems(HardwareType.BNO055IMU),
        "AsAdafruitBNO055IMU", "AsBNO055IMU");
    // In previous versions, identifier bno055imuParametersAccess was adafruitBNO055IMUParametersAccess.
    jsContent = replaceIdentifierInJs(jsContent,
        "adafruitBNO055IMUParametersAccess", "bno055imuParametersAccess");
    return jsContent;
  }

  /**
   * Replaces an identifier suffix in js.
   */
  private static String replaceIdentifierSuffixInJs(
      String jsContent, List<HardwareItem> hardwareItemList,
      String oldIdentifierSuffix, String newIdentifierSuffix) {
    if (hardwareItemList != null) {
      for (HardwareItem hardwareItem : hardwareItemList) {
        String newIdentifier = hardwareItem.identifier;
        if (newIdentifier.endsWith(newIdentifierSuffix)) {
          String oldIdentifier = newIdentifier.substring(0, newIdentifier.length() - newIdentifierSuffix.length())
              + oldIdentifierSuffix;
          String oldCode = oldIdentifier + ".";
          String newCode = newIdentifier + ".";
          jsContent = jsContent.replace(oldCode, newCode);
        }
      }
    }
    return jsContent;
  }

  /**
   * Replaces an identifier in js.
   */
  private static String replaceIdentifierInJs(
      String jsContent, String oldIdentifier, String newIdentifier) {
    String oldCode = oldIdentifier + ".";
    String newCode = newIdentifier + ".";
    return jsContent.replace(oldCode, newCode);
  }

  /**
   * Returns the name of the active configuration.
   */
  public static String getConfigurationName() {
    RobotConfigFileManager robotConfigFileManager = new RobotConfigFileManager();
    RobotConfigFile activeConfig = robotConfigFileManager.getActiveConfig();
    return activeConfig.getName();
  }

  private static void buildKnownTypesAndReservedWordsForFtcJava() {
    // Include all the classes that we could import (see Blockly.FtcJava.generateImport_ in ftcjava.js).
    KNOWN_TYPES_FOR_FTCJAVA.put("android.graphics", new Class<?>[] {
        Color.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("android.util", new Class<?>[] {
        Size.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.ftccommon", new Class<?>[] {
        SoundPlayer.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.bosch", new Class<?>[] {
        BNO055IMU.class,
        BNO055IMU.AccelerationIntegrator.class,
        BNO055IMU.AccelUnit.class,
        BNO055IMU.Parameters.class,
        BNO055IMU.SensorMode.class,
        BNO055IMU.SystemStatus.class,
        JustLoggingAccelerationIntegrator.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.dfrobot", new Class<?>[] {
        HuskyLens.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.digitalchickenlabs", new Class<?>[] {
        CachingOctoQuad.class,
        OctoQuad.class,
        OctoQuadBase.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.gobilda", new Class<?>[] {
        GoBildaPinpointDriver.class,
        GoBildaPinpointDriver.DeviceStatus.class,
        GoBildaPinpointDriver.EncoderDirection.class,
        GoBildaPinpointDriver.GoBildaOdometryPods.class,
        GoBildaPinpointDriver.ReadData.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.limelightvision", new Class<?>[] {
        Limelight3A.class,
        LLResult.class,
        LLResultTypes.class,
        LLResultTypes.FiducialResult.class,
        LLResultTypes.ColorResult.class,
        LLStatus.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.maxbotix", new Class<?>[] {
        MaxSonarI2CXL.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.modernrobotics", new Class<?>[] {
        ModernRoboticsI2cCompassSensor.class,
        ModernRoboticsI2cGyro.class,
        ModernRoboticsI2cGyro.HeadingMode.class,
        ModernRoboticsI2cRangeSensor.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.rev", new Class<?>[] {
        RevBlinkinLedDriver.class,
        RevBlinkinLedDriver.BlinkinPattern.class,
        RevHubOrientationOnRobot.class,
        RevHubOrientationOnRobot.LogoFacingDirection.class,
        RevHubOrientationOnRobot.UsbFacingDirection.class,
        Rev9AxisImuOrientationOnRobot.class,
        Rev9AxisImuOrientationOnRobot.I2cPortFacingDirection.class,
        Rev9AxisImuOrientationOnRobot.LogoFacingDirection.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.hardware.sparkfun", new Class<?>[] {
        SparkFunLEDStick.class,
        SparkFunOTOS.class,
        SparkFunOTOS.Pose2D.class,
        SparkFunOTOS.SelfTestConfig.class,
        SparkFunOTOS.SignalProcessConfig.class,
        SparkFunOTOS.Status.class,
        SparkFunOTOS.Version.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.robotcore.eventloop.opmode", new Class<?>[] {
        Autonomous.class,
        Disabled.class,
        LinearOpMode.class,
        OpMode.class,
        TeleOp.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.robotcore.hardware", new Class<?>[] {
        AccelerationSensor.class,
        AnalogInput.class,
        CRServo.class,
        // CRServo.Direction.class is a special case because it is the same class as DcMotorSimple.Direction.class.
        // It is handled in fetchJavaScriptForHardware.
        ColorSensor.class,
        CompassSensor.class,
        CompassSensor.CompassMode.class,
        DcMotor.class,
        // DcMotor.Direction.class is a special case because it is the same class as DcMotorSimple.Direction.class.
        // It is handled in fetchJavaScriptForHardware.
        DcMotor.RunMode.class,
        DcMotor.ZeroPowerBehavior.class,
        DcMotorEx.class,
        DcMotorSimple.class,
        DcMotorSimple.Direction.class,
        DigitalChannel.class,
        DigitalChannel.Mode.class,
        DistanceSensor.class,
        Gamepad.class,
        Gamepad.LedEffect.class,
        Gamepad.LedEffect.Builder.class,
        Gamepad.RumbleEffect.class,
        Gamepad.RumbleEffect.Builder.class,
        GyroSensor.class,
        Gyroscope.class,
        I2cAddr.class,
        I2cAddrConfig.class,
        I2cAddressableDevice.class,
        IMU.class,
        IMU.Parameters.class,
        ImuOrientationOnRobot.class,
        IrSeekerSensor.class,
        IrSeekerSensor.Mode.class,
        LED.class,
        Light.class,
        LightSensor.class,
        MotorControlAlgorithm.class,
        NormalizedColorSensor.class,
        NormalizedRGBA.class,
        OpticalDistanceSensor.class,
        OrientationSensor.class,
        PIDCoefficients.class,
        PIDFCoefficients.class,
        PWMOutput.class,
        Servo.class,
        Servo.Direction.class,
        ServoController.class,
        ServoController.PwmStatus.class,
        SwitchableLight.class,
        TouchSensor.class,
        UltrasonicSensor.class,
        VoltageSensor.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("com.qualcomm.robotcore.util", new Class<?>[] {
        ElapsedTime.class,
        ElapsedTime.Resolution.class,
        Range.class,
        ReadWriteFile.class,
        RobotLog.class,
        SortOrder.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("java.lang", new Class<?>[] {
        Boolean.class,
        Byte.class,
        Character.class,
        Double.class,
        Float.class,
        Integer.class,
        Long.class,
        Number.class,
        Object.class,
        Short.class,
        String.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("java.util", new Class<?>[] {
        ArrayList.class,
        Collections.class,
        List.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("java.util.concurrent", new Class<?>[] {
        TimeUnit.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external", new Class<?>[] {
        ClassFactory.class,
        JavaUtil.class,
        Telemetry.class,
        Telemetry.DisplayFormat.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external.android", new Class<?>[] {
        AndroidAccelerometer.class,
        AndroidGyroscope.class,
        AndroidOrientation.class,
        AndroidSoundPool.class,
        AndroidTextToSpeech.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external.hardware.camera", new Class<?>[] {
        BuiltinCameraDirection.class,
        CameraName.class,
        WebcamName.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external.hardware.camera.controls", new Class<?>[] {
        CameraControl.class,
        ExposureControl.class,
        FocusControl.class,
        GainControl.class,
        PtzControl.class,
        WhiteBalanceControl.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external.matrices", new Class<?>[] {
        MatrixF.class,
        OpenGLMatrix.class,
        VectorF.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external.navigation", new Class<?>[] {
        Acceleration.class,
        AngleUnit.class,
        AngularVelocity.class,
        AxesOrder.class,
        AxesReference.class,
        Axis.class,
        CurrentUnit.class,
        DistanceUnit.class,
        MagneticFlux.class,
        Orientation.class,
        Pose2D.class,
        Pose3D.class,
        Position.class,
        Quaternion.class,
        TempUnit.class,
        Temperature.class,
        UnnormalizedAngleUnit.class,
        Velocity.class,
        YawPitchRollAngles.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.external.stream", new Class<?>[] {
        CameraStreamServer.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.robotcore.internal.system", new Class<?>[] {
        AppUtil.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.vision", new Class<?>[] {
        VisionPortal.class,
        VisionProcessor.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.vision.apriltag", new Class<?>[] {
        AprilTagDetection.class,
        AprilTagGameDatabase.class,
        AprilTagLibrary.class,
        AprilTagMetadata.class,
        AprilTagPoseFtc.class,
        AprilTagPoseRaw.class,
        AprilTagProcessor.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.firstinspires.ftc.vision.opencv", new Class<?>[] {
        ColorBlobLocatorProcessor.class,
        ColorRange.class,
        ColorSpace.class,
        ImageRegion.class,
        PredominantColorProcessor.class,
      });
    KNOWN_TYPES_FOR_FTCJAVA.put("org.opencv.core", new Class<?>[] {
        RotatedRect.class,
        Scalar.class,
      });

    // Add the known types to the reserved words.
    for (Class<?>[] classes : KNOWN_TYPES_FOR_FTCJAVA.values()) {
      for (Class<?> c : classes) {
        if (c.getEnclosingClass() != null) {
          // If the class has an enclosing class, the name will have a dot and doesn't need to be
          // added as a reserved word.
          continue;
        }
        RESERVED_WORDS_FOR_FTCJAVA.add(c.getSimpleName());
      }
    }

    // LinearOpMode members
    for (Method method : LinearOpMode.class.getMethods()) {
      if (method.getName().equals("runOpMode")) {
        // We generate code for the declaration of runOpMode.
        // If we include it in the list of reserved words, it will generate runOpMode2, which is wrong.
        continue;
      }
      RESERVED_WORDS_FOR_FTCJAVA.add(method.getName());
    }
    for (Field field : LinearOpMode.class.getFields()) {
      RESERVED_WORDS_FOR_FTCJAVA.add(field.getName());
    }
    // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
    RESERVED_WORDS_FOR_FTCJAVA.add("abstract");
    RESERVED_WORDS_FOR_FTCJAVA.add("assert");
    RESERVED_WORDS_FOR_FTCJAVA.add("boolean");
    RESERVED_WORDS_FOR_FTCJAVA.add("break");
    RESERVED_WORDS_FOR_FTCJAVA.add("byte");
    RESERVED_WORDS_FOR_FTCJAVA.add("case");
    RESERVED_WORDS_FOR_FTCJAVA.add("catch");
    RESERVED_WORDS_FOR_FTCJAVA.add("char");
    RESERVED_WORDS_FOR_FTCJAVA.add("class");
    RESERVED_WORDS_FOR_FTCJAVA.add("const");
    RESERVED_WORDS_FOR_FTCJAVA.add("continue");
    RESERVED_WORDS_FOR_FTCJAVA.add("default");
    RESERVED_WORDS_FOR_FTCJAVA.add("do");
    RESERVED_WORDS_FOR_FTCJAVA.add("double");
    RESERVED_WORDS_FOR_FTCJAVA.add("else");
    RESERVED_WORDS_FOR_FTCJAVA.add("enum");
    RESERVED_WORDS_FOR_FTCJAVA.add("extends");
    RESERVED_WORDS_FOR_FTCJAVA.add("final");
    RESERVED_WORDS_FOR_FTCJAVA.add("finally");
    RESERVED_WORDS_FOR_FTCJAVA.add("float");
    RESERVED_WORDS_FOR_FTCJAVA.add("for");
    RESERVED_WORDS_FOR_FTCJAVA.add("goto");
    RESERVED_WORDS_FOR_FTCJAVA.add("if");
    RESERVED_WORDS_FOR_FTCJAVA.add("implements");
    RESERVED_WORDS_FOR_FTCJAVA.add("import");
    RESERVED_WORDS_FOR_FTCJAVA.add("instanceof");
    RESERVED_WORDS_FOR_FTCJAVA.add("int");
    RESERVED_WORDS_FOR_FTCJAVA.add("interface");
    RESERVED_WORDS_FOR_FTCJAVA.add("long");
    RESERVED_WORDS_FOR_FTCJAVA.add("native");
    RESERVED_WORDS_FOR_FTCJAVA.add("new");
    RESERVED_WORDS_FOR_FTCJAVA.add("package");
    RESERVED_WORDS_FOR_FTCJAVA.add("private");
    RESERVED_WORDS_FOR_FTCJAVA.add("protected");
    RESERVED_WORDS_FOR_FTCJAVA.add("public");
    RESERVED_WORDS_FOR_FTCJAVA.add("return");
    RESERVED_WORDS_FOR_FTCJAVA.add("short");
    RESERVED_WORDS_FOR_FTCJAVA.add("static");
    RESERVED_WORDS_FOR_FTCJAVA.add("strictfp");
    RESERVED_WORDS_FOR_FTCJAVA.add("super");
    RESERVED_WORDS_FOR_FTCJAVA.add("switch");
    RESERVED_WORDS_FOR_FTCJAVA.add("synchronized");
    RESERVED_WORDS_FOR_FTCJAVA.add("this");
    RESERVED_WORDS_FOR_FTCJAVA.add("throw");
    RESERVED_WORDS_FOR_FTCJAVA.add("throws");
    RESERVED_WORDS_FOR_FTCJAVA.add("transient");
    RESERVED_WORDS_FOR_FTCJAVA.add("try");
    RESERVED_WORDS_FOR_FTCJAVA.add("void");
    RESERVED_WORDS_FOR_FTCJAVA.add("volatile");
    RESERVED_WORDS_FOR_FTCJAVA.add("while");
    // java.lang.*
    RESERVED_WORDS_FOR_FTCJAVA.add(AbstractMethodError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Appendable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ArithmeticException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ArrayIndexOutOfBoundsException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ArrayStoreException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(AssertionError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(AutoCloseable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Boolean.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add("BootstrapMethodError"); // Added in API level 26
    RESERVED_WORDS_FOR_FTCJAVA.add(Byte.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Character.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(CharSequence.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Class.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add("ClassValue"); // Added in API level 34
    RESERVED_WORDS_FOR_FTCJAVA.add(ClassCastException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ClassCircularityError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ClassFormatError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ClassLoader.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ClassNotFoundException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Cloneable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(CloneNotSupportedException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Comparable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Compiler.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Deprecated.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Double.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Enum.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(EnumConstantNotPresentException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Error.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Exception.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ExceptionInInitializerError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Float.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(FunctionalInterface.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IllegalAccessError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IllegalAccessException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IllegalArgumentException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IllegalMonitorStateException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IllegalStateException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IllegalThreadStateException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IncompatibleClassChangeError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(IndexOutOfBoundsException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(InheritableThreadLocal.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(InstantiationError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(InstantiationException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Integer.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(InternalError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(InterruptedException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Iterable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(LinkageError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Long.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Math.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NegativeArraySizeException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NoClassDefFoundError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NoSuchFieldError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NoSuchFieldException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NoSuchMethodError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NoSuchMethodException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NullPointerException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Number.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(NumberFormatException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Object.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(OutOfMemoryError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Override.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Package.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Process.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ProcessBuilder.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Readable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ReflectiveOperationException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Runnable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Runtime.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(RuntimeException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(RuntimePermission.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(SafeVarargs.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(SecurityException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(SecurityManager.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Short.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(StackOverflowError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(StackTraceElement.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(StrictMath.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(String.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(StringBuffer.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(StringBuilder.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(StringIndexOutOfBoundsException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(SuppressWarnings.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(System.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Thread.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ThreadDeath.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ThreadGroup.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(ThreadLocal.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Throwable.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(TypeNotPresentException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(UnknownError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(UnsatisfiedLinkError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(UnsupportedClassVersionError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(UnsupportedOperationException.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(VerifyError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(VirtualMachineError.class.getSimpleName());
    RESERVED_WORDS_FOR_FTCJAVA.add(Void.class.getSimpleName());
  }
}
