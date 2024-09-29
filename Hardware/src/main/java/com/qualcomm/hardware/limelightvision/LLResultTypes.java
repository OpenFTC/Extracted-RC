package com.qualcomm.hardware.limelightvision;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Parent class for all Limelight Result Types
 */
public class LLResultTypes {
    
    /**
     * Parses a JSONArray of points into a List of Lists representing 2D coordinates.
     *
     * @param cornersArray The JSONArray containing point data. Each element should be a JSONArray with two elements representing x and y coordinates.
     * @return A List of Lists, where each inner List contains two Double values representing x and y coordinates.
     *         Returns an empty List if the input is null or improperly formatted.
     */
    private static List<List<Double>> parsePoints(JSONArray cornersArray) {
        List<List<Double>> points = new ArrayList<>();
        if (cornersArray != null) {
            for (int i = 0; i < cornersArray.length(); i++) {
                JSONArray point = cornersArray.optJSONArray(i);
                if (point != null && point.length() == 2) {
                    List<Double> cornerPoint = new ArrayList<>();
                    cornerPoint.add(point.optDouble(0, 0.0));
                    cornerPoint.add(point.optDouble(1, 0.0));
                    points.add(cornerPoint);
                }
            }
        }
        return points;
    }

    /**
     * Parses a JSONArray into a double array representing a robot pose.
     * The array should contain 6 elements: x, y, z, roll, pitch, yaw.
     *
     * @param array The JSONArray to convert.
     * @return A double array representing the robot pose. If the input is null or has fewer than 6 elements,
     *         returns an array of 6 zeros.
     */
    private static double[] parsePoseArray(JSONArray array) {
        double[] pose = new double[6];  // Initialize with zeros
        if (array != null && array.length() >= 6) {
            for (int i = 0; i < 6; i++) {
                pose[i] = array.optDouble(i, 0.0);
            }
        }
        return pose;
    }

    /**
     * Represents a barcode pipeline result. A barcode pipeline may generate multiple valid results.
     */
    public static class BarcodeResult {

        private String family;
        private String data;
        private double targetXPixels;
        private double targetYPixels;
        private double targetXDegrees;
        private double targetYDegrees;
        private double targetXDegreesNoCrosshair;
        private double targetYDegreesNoCrosshair;
        private double targetArea;
        private List<List<Double>> targetCorners;

        /**
         * Constructs a BarcodeResult from JSON data.
         *
         * @param data The JSON object containing barcode data.
         */
        protected BarcodeResult(JSONObject data) {
            this.family = data.optString("fam", "");
            this.data = data.optString("data", "");
            this.targetXPixels = data.optDouble("txp", 0.0);
            this.targetYPixels = data.optDouble("typ", 0.0);
            this.targetXDegrees = data.optDouble("tx", 0.0);
            this.targetYDegrees = data.optDouble("ty", 0.0);
            this.targetXDegreesNoCrosshair = data.optDouble("tx_nocross", 0.0);
            this.targetYDegreesNoCrosshair = data.optDouble("ty_nocross", 0.0);
            this.targetArea = data.optDouble("ta", 0.0);
            this.targetCorners = LLResultTypes.parsePoints(data.optJSONArray("pts"));
        }

        /**
         * Gets the family of the barcode.
         *
         * @return The barcode family.
         */
        public String getFamily() {
            return family;
        }

        /**
         * Gets the data contained in the barcode.
         *
         * @return The barcode data.
         */
        public String getData() {
            return data;
        }

        /**
         * Gets the horizontal offset of the barcode from the crosshair in pixels.
         *
         * @return The horizontal offset in pixels.
         */
        public double getTargetXPixels() {
            return targetXPixels;
        }

        /**
         * Gets the vertical offset of the barcode from the crosshair in pixels.
         *
         * @return The vertical offset in pixels.
         */
        public double getTargetYPixels() {
            return targetYPixels;
        }

        /**
         * Gets the horizontal offset of the barcode from the crosshair in degrees.
         *
         * @return The horizontal offset in degrees.
         */
        public double getTargetXDegrees() {
            return targetXDegrees;
        }

        /**
         * Gets the vertical offset of the barcode from the crosshair in degrees.
         *
         * @return The vertical offset in degrees.
         */
        public double getTargetYDegrees() {
            return targetYDegrees;
        }

        /**
         * Gets the horizontal offset of the barcode from the principal pixel in degrees.
         *
         * @return The horizontal offset in degrees.
         */
        public double getTargetXDegreesNoCrosshair() {
            return targetXDegreesNoCrosshair;
        }

        /**
         * Gets the vertical offset of the barcode from the principal pixel in degrees.
         *
         * @return The vertical offset in degrees.
         */
        public double getTargetYDegreesNoCrosshair() {
            return targetYDegreesNoCrosshair;
        }

        /**
         * Gets the area of the detected barcode as a percentage of the image.
         *
         * @return The target area (0-1).
         */
        public double getTargetArea() {
            return targetArea;
        }

        /**
         * Gets the four corner points of the detected barcode.
         *
         * @return A list of corner points, each point represented as a list of two doubles [x, y].
         */
        public List<List<Double>> getTargetCorners() {
            return targetCorners;
        }

    }

    /**
     * Represents a classifier pipeline result.
     */
    public static class ClassifierResult {
        private String className;
        private int classId;
        private double confidence;

        /**
         * Constructs a ClassifierResult from JSON data.
         *
         * @param data The JSON object containing classifier data.
         */
        protected ClassifierResult(JSONObject data) {
            this.className = data.optString("class", "");
            this.classId = data.optInt("classID", 0);
            this.confidence = data.optDouble("conf", 0.0);
        }

        /**
         * Gets the class name of the classifier result (eg book, car, gamepiece).
         *
         * @return The class name
         */
        public String getClassName() {
            return className;
        }

        /**
         * Gets the class index of the classifier result.
         *
         * @return The class index
         */
        public int getClassId() {
            return classId;
        }

        /**
         * Gets the confidence score of the classification.
         *
         * @return The confidence score of the classification (0-100).
         */
        public double getConfidence() {
            return confidence;
        }
    }

    /**
     * Represents a detector pipeline result. A detector pipeline may generate multiple valid results.
     */
    public static class DetectorResult {
        private String className;
        private int classId;
        private double confidence;
        private double targetArea;
        private double targetXPixels;
        private double targetYPixels;
        private double targetXDegrees;
        private double targetYDegrees;
        private double targetXDegreesNoCrosshair;
        private double targetYDegreesNoCrosshair;
        private List<List<Double>> targetCorners;

        /**
         * Constructs a DetectorResult from JSON data.
         *
         * @param data The JSON object containing classifier data.
         */
        protected DetectorResult(JSONObject data) {
            this.className = data.optString("class", "");
            this.classId = data.optInt("classID", 0);
            this.confidence = data.optDouble("conf", 0.0);
            this.targetArea = data.optDouble("ta", 0.0);
            this.targetXPixels = data.optDouble("txp", 0.0);
            this.targetYPixels = data.optDouble("typ", 0.0);
            this.targetXDegrees = data.optDouble("tx", 0.0);
            this.targetYDegrees = data.optDouble("ty", 0.0);
            this.targetXDegreesNoCrosshair = data.optDouble("tx_nocross", 0.0);
            this.targetYDegreesNoCrosshair = data.optDouble("ty_nocross", 0.0);
            this.targetCorners = LLResultTypes.parsePoints(data.optJSONArray("pts"));
        }

        /**
         * Gets the class name of the detector result (eg book, car, gamepiece).
         *
         * @return The class name
         */
        public String getClassName() {
            return className;
        }


        /**
         * Gets the class index of the detector result.
         *
         * @return The class index
         */
        public int getClassId() {
            return classId;
        }

        /**
         * Gets the confidence score of the classification.
         *
         * @return The confidence score of the classification (0-100).
         */
        public double getConfidence() {
            return confidence;
        }

        /**
         * Gets the four corner points of the detected result.
         *
         * @return A list of corner points, each point represented as a list of two doubles [x, y].
         */
        public List<List<Double>> getTargetCorners() {
            return targetCorners;
        }

        /**
         * Gets the undistorted area of the target as a percentage of the image area
         *
         * @return target area (0-100)
         */
        public double getTargetArea() {
            return targetArea;
        }

        /**
         * Gets the current tx in pixels from the crosshair
         *
         * @return horizontal angular offset
         */
        public double getTargetXPixels() {
            return targetXPixels;
        }

        /**
         * Gets the current ty in pixels from the crosshair
         *
         * @return vertical angular offset
         */
        public double getTargetYPixels() {
            return targetYPixels;
        }

        /**
         * Gets the current tx in degrees from the crosshair
         *
         * @return horizontal angular offset
         */
        public double getTargetXDegrees() {
            return targetXDegrees;
        }

        /**
         * Gets the current ty in degrees from the crosshair
         *
         * @return vertical angular offset
         */
        public double getTargetYDegrees() {
            return targetYDegrees;
        }

        /**
         * Gets the current tx in degrees from the principal pixel
         *
         * @return horizontal angular offset
         */
        public double getTargetXDegreesNoCrosshair() {
            return targetXDegreesNoCrosshair;
        }

        /**
         * Gets the current ty in degrees from the principal pixel
         *
         * @return vertical angular offset
         */
        public double getTargetYDegreesNoCrosshair() {
            return targetYDegreesNoCrosshair;
        }
    }

    /**
     * Represents a Fiducial/AprilTag pipeline result. A fiducial/apriltag pipeline may generate multiple valid results.
     */
    public static class FiducialResult {
        private int fiducialId;
        private String family;
        private double skew;
        private Pose3D cameraPoseTargetSpace;
        private Pose3D robotPoseFieldSpace;
        private Pose3D robotPoseTargetSpace;
        private Pose3D targetPoseCameraSpace;
        private Pose3D targetPoseRobotSpace;
        private double targetArea;
        private double targetXPixels;
        private double targetYPixels;
        private double targetXDegrees;
        private double targetYDegrees;
        private double targetXDegreesNoCrosshair;
        private double targetYDegreesNoCrosshair;
        private List<List<Double>> targetCorners;


        /**
         * Constructs a FiducialResult from JSON data.
         *
         * @param data The JSON object containing fiducial data.
         */
        protected FiducialResult(JSONObject data) {
            this.fiducialId = data.optInt("fID", 0);
            this.family = data.optString("fam", "");
            this.skew = data.optDouble("skew", 0.0);
            this.cameraPoseTargetSpace = LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6c_ts")));
            this.robotPoseFieldSpace = LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6r_fs")));
            this.robotPoseTargetSpace = LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6r_ts")));
            this.targetPoseCameraSpace = LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6t_cs")));
            this.targetPoseRobotSpace =  LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6t_rs")));
            this.targetArea = data.optDouble("ta", 0.0);
            this.targetXPixels = data.optDouble("txp", 0.0);
            this.targetYPixels = data.optDouble("typ", 0.0);
            this.targetXDegrees = data.optDouble("tx", 0.0);
            this.targetYDegrees = data.optDouble("ty", 0.0);
            this.targetXDegreesNoCrosshair = data.optDouble("tx_nocross", 0.0);
            this.targetYDegreesNoCrosshair = data.optDouble("ty_nocross", 0.0);
            this.targetCorners = LLResultTypes.parsePoints(data.optJSONArray("pts"));
        }

        /**
         * Gets the ID of the fiducial.
         *
         * @return The fiducial ID.
         */
        public int getFiducialId() {
            return fiducialId;
        }

        /**
         * Gets the family of the fiducial (eg 36h11).
         *
         * @return The fiducial family.
         */
        public String getFamily() {
            return family;
        }

        /**
         * Gets the four corner points of the detected fiducial/apriltag.
         *
         * @return A list of corner points, each point represented as a list of two doubles [x, y].
         */
        public List<List<Double>> getTargetCorners() {
            return targetCorners;
        }

        /**
         * Gets the skew of the detected fiducial. Not recommended.
         *
         * @return The skew value.
         */
        public double getSkew() {
            return skew;
        }

        /**
         * Gets the camera pose in target space.
         *
         * @return An Pose3D representing the camera pose.
         */
        public Pose3D getCameraPoseTargetSpace() {
            return cameraPoseTargetSpace;
        }

        /**
         * Gets the robot pose in field based on this fiducial/apriltag alone.
         *
         * @return An Pose3D representing the robot pose.
         */
        public Pose3D getRobotPoseFieldSpace() {
            return robotPoseFieldSpace;
        }

        /**
         * Gets the robot pose in target space.
         *
         * @return An Pose3D representing the robot pose.
         */
        public Pose3D getRobotPoseTargetSpace() {
            return robotPoseTargetSpace;
        }

        /**
         * Gets the target pose in camera space.
         *
         * @return An Pose3D representing the target pose.
         */
        public Pose3D getTargetPoseCameraSpace() {
            return targetPoseCameraSpace;
        }

        /**
         * Gets the target pose in robot space.
         *
         * @return An Pose3D representing the target pose.
         */
        public Pose3D getTargetPoseRobotSpace() {
            return targetPoseRobotSpace;
        }

        /**
         * Gets the area of the detected fiducial as a percentage of the image.
         *
         * @return The target area (0-100).
         */
        public double getTargetArea() {
            return targetArea;
        }

        /**
         * Gets the current tx in pixels from the crosshair
         *
         * @return horizontal angular offset
         */
        public double getTargetXPixels() {
            return targetXPixels;
        }

        /**
         * Gets the current ty in pixels from the crosshair
         *
         * @return vertical angular offset
         */
        public double getTargetYPixels() {
            return targetYPixels;
        }

        /**
         * Gets the current tx in degrees from the crosshair
         *
         * @return horizontal angular offset
         */
        public double getTargetXDegrees() {
            return targetXDegrees;
        }

        /**
         * Gets the current ty in degrees from the crosshair
         *
         * @return vertical angular offset
         */
        public double getTargetYDegrees() {
            return targetYDegrees;
        }

        /**
         * Gets the current tx in degrees from the principal pixel
         *
         * @return horizontal angular offset
         */
        public double getTargetXDegreesNoCrosshair() {
            return targetXDegreesNoCrosshair;
        }

        /**
         * Gets the current ty in degrees from the principal pixel
         *
         * @return vertical angular offset
         */
        public double getTargetYDegreesNoCrosshair() {
            return targetYDegreesNoCrosshair;
        }
    }

    /**
     * Represents a color pipeline result. A color pipeline may generate multiple valid results.
     */
    public static class ColorResult {
        private Pose3D cameraPoseTargetSpace;
        private Pose3D robotPoseFieldSpace;
        private Pose3D robotPoseTargetSpace;
        private Pose3D targetPoseCameraSpace;
        private Pose3D targetPoseRobotSpace;
        private double targetArea;
        private double targetXPixels;
        private double targetYPixels;
        private double targetXDegrees;
        private double targetYDegrees;
        private double targetXDegreesNoCrosshair;
        private double targetYDegreesNoCrosshair;
        private List<List<Double>> targetCorners;

        /**
         * Constructs a Color/ColorResult from JSON data.
         *
         * @param data The JSON object containing color data.
         */
        protected ColorResult(JSONObject data) {
            this.cameraPoseTargetSpace = LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6c_ts")));
            this.robotPoseFieldSpace =  LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6r_fs")));
            this.robotPoseTargetSpace =  LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6r_ts")));
            this.targetPoseCameraSpace = LLResult.createPose3DRobot(LLResultTypes.parsePoseArray(data.optJSONArray("t6t_cs")));
            this.targetPoseRobotSpace = LLResult.createPose3DRobot( LLResultTypes.parsePoseArray(data.optJSONArray("t6t_rs")));
            this.targetArea = data.optDouble("ta", 0.0);
            this.targetXPixels = data.optDouble("txp", 0.0);
            this.targetYPixels = data.optDouble("typ", 0.0);
            this.targetXDegrees = data.optDouble("tx", 0.0);
            this.targetYDegrees = data.optDouble("ty", 0.0);
            this.targetXDegreesNoCrosshair = data.optDouble("tx_nocross", 0.0);
            this.targetYDegreesNoCrosshair = data.optDouble("ty_nocross", 0.0);
            this.targetCorners = LLResultTypes.parsePoints(data.optJSONArray("pts"));
        }

        /**
         * Gets the corner points of the detected target. The number of corners is not fixed.
         *
         * @return A list of corner points, each point represented as a list of two doubles [x, y].
         */
        public List<List<Double>> getTargetCorners() {
            return targetCorners;
        }

        /**
         * Gets the camera pose in target space.
         *
         * @return An Pose3D representing the camera pose.
         */
        public Pose3D getCameraPoseTargetSpace() {
            return cameraPoseTargetSpace;
        }

        /**
         * Gets the robot pose in field space based on this color target.
         *
         * @return An Pose3D representing the robot pose.
         */
        public Pose3D getRobotPoseFieldSpace() {
            return robotPoseFieldSpace;
        }

        /**
         * Gets the robot pose in target space.
         *
         * @return An Pose3D representing the robot pose.
         */
        public Pose3D getRobotPoseTargetSpace() {
            return robotPoseTargetSpace;
        }

        /**
         * Gets the target pose in camera space.
         *
         * @return An Pose3D representing the target pose.
         */
        public Pose3D getTargetPoseCameraSpace() {
            return targetPoseCameraSpace;
        }

        /**
         * Gets the target pose in robot space.
         *
         * @return An Pose3D representing the target pose.
         */
        public Pose3D getTargetPoseRobotSpace() {
            return targetPoseRobotSpace;
        }

        /**
         * Gets the area of the detected color target as a percentage of the image.
         *
         * @return The target area (0-100).
         */
        public double getTargetArea() {
            return targetArea;
        }

        /**
         * Gets the current tx in pixels from the crosshair
         *
         * @return horizontal angular offset
         */
        public double getTargetXPixels() {
            return targetXPixels;
        }

        /**
         * Gets the current ty in pixels from the crosshair
         *
         * @return vertical angular offset
         */
        public double getTargetYPixels() {
            return targetYPixels;
        }

        /**
         * Gets the current tx in degrees from the crosshair
         *
         * @return horizontal angular offset
         */
        public double getTargetXDegrees() {
            return targetXDegrees;
        }

        /**
         * Gets the current ty in degrees from the crosshair
         *
         * @return vertical angular offset
         */
        public double getTargetYDegrees() {
            return targetYDegrees;
        }

        /**
         * Gets the current tx in degrees from the principal pixel
         *
         * @return horizontal angular offset
         */
        public double getTargetXDegreesNoCrosshair() {
            return targetXDegreesNoCrosshair;
        }

        /**
         * Gets the current ty in degrees from the principal pixel
         *
         * @return vertical angular offset
         */
        public double getTargetYDegreesNoCrosshair() {
            return targetYDegreesNoCrosshair;
        }
    }


    /**
     * Represents a calibration result. Calibration results are generated by the user in the UI's calibration tab.
     */
    public static class CalibrationResult {
        private String displayName;
        private double resX;
        private double resY;
        private double reprojectionError;
        private double[] camMatVector;
        private double[] distortionCoefficients;
        private boolean valid;

        private static final double DEFAULT_REPORT_VAL = 0.0;

        /**
         * Constructs a CalibrationResult with default values.
         */
        public CalibrationResult() {
            this("", DEFAULT_REPORT_VAL, DEFAULT_REPORT_VAL, DEFAULT_REPORT_VAL, 
                new double[9], new double[5]);
        }

        /**
         * Constructs a CalibrationResult with specified values.
         *
         * @param displayName The display name for the calibration.
         * @param resX The X resolution of the calibration.
         * @param resY The Y resolution of the calibration.
         * @param reprojectionError The reprojection error of the calibration.
         * @param camMatVector The camera matrix vector (9 elements).
         * @param distortionCoefficients The distortion coefficients (5 elements).
         */
        public CalibrationResult(String displayName, double resX, double resY, double reprojectionError,
                                double[] camMatVector, double[] distortionCoefficients) {
            this.displayName = displayName;
            this.resX = resX;
            this.resY = resY;
            this.reprojectionError = reprojectionError;
            this.camMatVector = camMatVector.clone();
            this.distortionCoefficients = distortionCoefficients.clone();
            this.valid = true;
        }

        /**
         * Constructs a CalibrationResult from JSON data. (Package-private)
         *
         * @param json The JSON object containing calibration result data.
         */
        protected CalibrationResult(JSONObject json) {
            //default value construction
            this();
            //parse json
            if(json!=null)
            {
                try{
                    this.displayName = json.optString("DISPLAY_NAME", "");
                    this.reprojectionError = json.optDouble("REPROJECTION_ERROR", DEFAULT_REPORT_VAL);
                    this.resX = json.optDouble("RES_X", DEFAULT_REPORT_VAL);
                    this.resY = json.optDouble("RES_Y", DEFAULT_REPORT_VAL);

                    JSONArray intrinsicsMatrix = json.optJSONArray("INTRINSICS_MATRIX");
                    if (intrinsicsMatrix != null && intrinsicsMatrix.length() == 9) {
                        for (int i = 0; i < 9; i++) {
                            this.camMatVector[i] = intrinsicsMatrix.optDouble(i, 0.0);
                        }
                    } else {
                        this.valid = false;
                    }

                    JSONArray distortionCoeffs = json.optJSONArray("DISTORTION_COEFFICIENTS");
                    if (distortionCoeffs != null && distortionCoeffs.length() == 5) {
                        for (int i = 0; i < 5; i++) {
                            this.distortionCoefficients[i] = distortionCoeffs.optDouble(i, 0.0);
                        }
                    } else {
                        this.valid = false;
                    }

                    if (this.resX == DEFAULT_REPORT_VAL || this.resY == DEFAULT_REPORT_VAL) {
                        this.valid = false;
                    }
                 } catch (Exception e){ }
            }
        }

        /**
         * Checks if the calibration result is valid.
         *
         * @return true if the calibration result is valid, false otherwise.
         */
        public boolean isValid() {
            return valid && resX != 0 && resY != 0 && camMatVector[0] != 0;
        }

        /**
         * Gets the display name of the calibration.
         *
         * @return The display name.
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Gets the X resolution of the calibration.
         *
         * @return The X resolution.
         */
        public double getResX() {
            return resX;
        }

        /**
         * Gets the Y resolution of the calibration.
         *
         * @return The Y resolution.
         */
        public double getResY() {
            return resY;
        }

        /**
         * Gets the reprojection error of the calibration.
         *
         * @return The reprojection error.
         */
        public double getReprojectionError() {
            return reprojectionError;
        }

        /**
         * Gets the camera matrix vector.
         *
         * @return A copy of the camera matrix vector.
         */
        public double[] getCamMatVector() {
            return camMatVector.clone();
        }

        /**
         * Gets the distortion coefficients.
         *
         * @return A copy of the distortion coefficients.
         */
        public double[] getDistortionCoefficients() {
            return distortionCoefficients.clone();
        }

        /**
         * Converts a CalibrationResult to a JSONObject.
         *
         * @return A JSONObject representation of the CalibrationResult.
         */
        protected JSONObject toJson() {
            JSONObject json = new JSONObject();
            try {
                json.put("DISPLAY_NAME", this.getDisplayName());
                json.put("REPROJECTION_ERROR", this.getReprojectionError());
                json.put("RES_X", this.getResX());
                json.put("RES_Y", this.getResY());
                json.put("INTRINSICS_MATRIX", new JSONArray(this.getCamMatVector()));
                json.put("DISTORTION_COEFFICIENTS", new JSONArray(this.getDistortionCoefficients()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }
    }
}