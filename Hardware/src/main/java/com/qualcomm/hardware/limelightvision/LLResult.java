/*
Copyright (c) 2024 Limelight Vision

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Limelight Vision nor the names of its contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.qualcomm.hardware.limelightvision;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import java.util.ArrayList;
import java.util.List;

import com.qualcomm.hardware.limelightvision.LLResultTypes.*;

/**
 * Represents the result of a Limelight Pipeline. This class parses JSON data from a Limelight
 * in the constructor and provides easy access to the results data.
 */
public class LLResult {
    private JSONObject jsonData;
    private List<BarcodeResult> barcodeResults;
    private List<ClassifierResult> classifierResults;
    private List<DetectorResult> detectorResults;
    private List<FiducialResult> fiducialResults;
    private List<ColorResult> colorResults;
    private double parseLatency;
    private long controlHubTimeStamp;

    /**
     * Constructs an LLResult object from a JSONObject.
     *
     * @param json The JSONObject containing Limelight vision data.
     * @throws JSONException If there's an error parsing the JSON string.
     */
    protected LLResult(JSONObject json) throws JSONException {
        this.jsonData = json;
        this.barcodeResults = new ArrayList<>();
        this.classifierResults = new ArrayList<>();
        this.detectorResults = new ArrayList<>();
        this.fiducialResults = new ArrayList<>();
        this.colorResults = new ArrayList<>();
        this.parseLatency = 0.0;
        setControlHubTimeStamp(System.currentTimeMillis());
        parseResults();
    }

    /**
     * Sets the timestamp from the control hub in milliseconds.
     *
     * @param timestamp The timestamp to set in milliseconds.
     */
    void setControlHubTimeStamp(long timestamp) {
        this.controlHubTimeStamp = timestamp;
    }

    /**
     * Gets the control hub timestamp in milliseconds.
     *
     * @return The control hub timestamp in milliseconds.
     */
    public long getControlHubTimeStamp() {
        return this.controlHubTimeStamp;
    }

    /**
     * Gets the control hub timestamp in nanoseconds.
     *
     * @return The control hub timestamp in nanoseconds.
     */
    public long getControlHubTimeStampNanos() {
        return this.controlHubTimeStamp * 1000000;
    }


    /**
     * Calculates the staleness of the data.
     *
     * @return The staleness in milliseconds.
     */
    public long getStaleness() {
        return System.currentTimeMillis() - this.controlHubTimeStamp;
    }

    /**
     * Parses the JSON data into result objects.
     *
     * @throws JSONException If there's an error parsing the JSON data.
     */
    private void parseResults() throws JSONException {
        long startTime = System.nanoTime();

        JSONArray barcodeArray = jsonData.optJSONArray("Barcode");
        if (barcodeArray != null) {
            for (int i = 0; i < barcodeArray.length(); i++) {
                barcodeResults.add(new BarcodeResult(barcodeArray.getJSONObject(i)));
            }
        }

        JSONArray classifierArray = jsonData.optJSONArray("Classifier");
        if (classifierArray != null) {
            for (int i = 0; i < classifierArray.length(); i++) {
                classifierResults.add(new ClassifierResult(classifierArray.getJSONObject(i)));
            }
        }

        JSONArray detectorArray = jsonData.optJSONArray("Detector");
        if (detectorArray != null) {
            for (int i = 0; i < detectorArray.length(); i++) {
                detectorResults.add(new DetectorResult(detectorArray.getJSONObject(i)));
            }
        }

        JSONArray fiducialArray = jsonData.optJSONArray("Fiducial");
        if (fiducialArray != null) {
            for (int i = 0; i < fiducialArray.length(); i++) {
                fiducialResults.add(new FiducialResult(fiducialArray.getJSONObject(i)));
            }
        }

        JSONArray colorArray = jsonData.optJSONArray("Retro");
        if (colorArray != null) {
            for (int i = 0; i < colorArray.length(); i++) {
                colorResults.add(new ColorResult(colorArray.getJSONObject(i)));
            }
        }

        long endTime = System.nanoTime();
        this.parseLatency = (endTime - startTime) / 1e6; // Convert to milliseconds
    }

    /**
     * Gets the list of barcode results.
     *
     * @return A list of BarcodeResult objects.
     */
    public List<BarcodeResult> getBarcodeResults() {
        return barcodeResults;
    }

    /**
     * Gets the list of classifier results.
     *
     * @return A list of ClassifierResult objects.
     */
    public List<ClassifierResult> getClassifierResults() {
        return classifierResults;
    }

    /**
     * Gets the list of detector results.
     *
     * @return A list of DetectorResult objects.
     */
    public List<DetectorResult> getDetectorResults() {
        return detectorResults;
    }

    /**
     * Gets the list of fiducial/apriltag results.
     *
     * @return A list of FiducialResult objects.
     */
    public List<FiducialResult> getFiducialResults() {
        return fiducialResults;
    }

    /**
     * Gets the list of color results.
     *
     * @return A list of ColorResult objects.
     */
    public List<ColorResult> getColorResults() {
        return colorResults;
    }

    /**
     * Gets the focus metric of the image. This is only valid if the focus pipeline is enabled
     *
     * @return The focus metric value.
     */
    public double getFocusMetric() {
        return jsonData.optDouble("focus_metric", 0);
    }

    /**
     * Gets the 3D botpose.
     *
     * @return An Pose3D representing the bot pose.
     */
    public Pose3D getBotpose() {
        return createPose3DRobot(getDoubleArray("botpose", 6));
    }

    /**
     * Gets the 3D botpose using the WPILIB Blue Alliance Coordinate System.
     *
     * @return An Pose3D representing the bot pose.
     */
    private Pose3D getBotposeWpiblue() {
        return createPose3DRobot(getDoubleArray("botpose_wpiblue", 6));
    }


    /**
     * Gets the 3D botpose using the WPILIB Red Alliance Coordinate System.
     *
     * @return An Pose3D representing the bot pose.
     */
    private Pose3D getBotposeWpired() {
        return createPose3DRobot(getDoubleArray("botpose_wpired", 6));
    }

    /**
     * Gets the 3D botpose using MegaTag2. You must set the orientation of the robot with your imu for this to work.
     *
     * @return An Pose3D representing the MT2 botpose
     */
    public Pose3D getBotpose_MT2() {
        return createPose3DRobot(getDoubleArray("botpose_orb", 6));
    }

    /**
     * Gets the 3D botpose using MegaTag2 and the WPILIB Blue Alliance Coordinate System. You must set the orientation of the robot with your imu for this to work.
     *
     * @return An Pose3D representing the MT2 botpose
     */
    private Pose3D getBotposeWpiblue_MT2() {
        return createPose3DRobot(getDoubleArray("botpose_orb_wpiblue", 6));
    }

    /**
     * Gets the 3D botpose using MegaTag2 and the WPILIB Red Alliance Coordinate System. You must set the orientation of the robot with your imu for this to work.
     *
     * @return An Pose3D representing the MT2 botpose
     */
    private Pose3D getBotposeOrbWpired_MT2() {
        return createPose3DRobot(getDoubleArray("botpose_orb_wpired", 6));
    }


    /**
     * Gets the standard deviation metrics for MegaTag1 (x,y,z,roll,pitch,yaw)
     *
     * @return MegaTag1 STDevs
     */
    public double[] getStddevMt1() {
        return getDoubleArray("stdev_mt1", 6);
    }

    /**
     * Gets the standard deviation metrics for MegaTag2 (x,y,z,roll,pitch,yaw)
     *
     * @return MegaTag2 STDevs
     */
    public double[] getStddevMt2() {
        return getDoubleArray("stdev_mt2", 6);
    }

    /**
     * Gets the number of tags used in the botpose calculation.
     *
     * @return The number of tags used in the botpose calculation.
     */
    public int getBotposeTagCount() {
        return jsonData.optInt("botpose_tagcount", 0);
    }

    /**
     * Gets the span of tags used in the botpose calculation in meters.
     *
     * @return The span of tags used in the botpose calculation.
     */
    public double getBotposeSpan() {
        return jsonData.optDouble("botpose_span", 0);
    }

    /**
     * Gets the average distance of tags used in the botpose calculation in meters.
     *
     * @return The average distance of tags used in the botpose calculation.
     */
    public double getBotposeAvgDist() {
        return jsonData.optDouble("botpose_avgdist", 0);
    }

    /**
     * Gets the average area of tags used in the botpose calculation.
     *
     * @return The average area of tags used in the botpose calculation.
     */
    public double getBotposeAvgArea() {
        return jsonData.optDouble("botpose_avgarea", 0);
    }

    /**
     * Gets the user-specified python snapscript output data
     *
     * @return An array of values from the python snapscript pipeline. Returns an array of size 32.
    */
    public double[] getPythonOutput() {
        double[] output = getDoubleArray("PythonOut", 0);

        // Create a new array of size 32
        double[] result = new double[32];

        // How many elements do we need to copy to the final result array?
        int lengthToCopy = Math.min(output.length, 32);
        
        // Copy from the python result array to the result array.
        System.arraycopy(output, 0, result, 0, lengthToCopy);
        return result;
    }

    /**
     * Gets the current capture latency in milliseconds
     *
     * @return capture latency in millisecondss
     */
    public double getCaptureLatency() {
        return jsonData.optDouble("cl", 0);
    }

    /**
     * Gets the type of the current pipeline.
     *
     * @return The type of the current pipeline as a string.
     */
    public String getPipelineType() {
        return jsonData.optString("pipelineType", "");
    }

    /**
     * Gets the current tx in degrees from the crosshair
     *
     * @return horizontal angular offset of the primary target in degrees from the crosshair
     */
    public double getTx() {
        return jsonData.optDouble("tx", 0);
    }

    /**
     * Gets the current ty in degrees from the crosshair
     *
     * @return vertical angular offset of the primary target in degrees from the crosshair
     */
    public double getTy() {
        return jsonData.optDouble("ty", 0);
    }

    /**
     * Gets the current tx in degrees from the principal pixel instead of the crosshair
     *
     * @return horizontal angular offset of the primary target in degrees from the principal pixel instead of the crosshair
     */
    public double getTxNC() {
        return jsonData.optDouble("txnc", 0);
    }

    /**
     * Gets the current ty in degrees from the principal pixel instead of the crosshair
     *
     * @return vertical angular offset of the primary target in degrees from the principal pixel instead of the crosshair
     */
    public double getTyNC() {
        return jsonData.optDouble("tync", 0);
    }

    /**
     * Gets the area of the target as a percentage of the image area
     *
     * @return target area (0-100)
     */
    public double getTa() {
        return jsonData.optDouble("ta", 0);
    }

    /**
     * Gets the index of the currently active pipeline
     *
     * @return index of the currently active pipeline
     */
    public int getPipelineIndex() {
        return jsonData.optInt("pID", 0);
    }

    /**
     * Gets the 3D camera pose in robot space as configured in the UI
     *
     * @return An Pose3D representing the camera pose in the robot's coordinate system.
     */
    private Pose3D getCameraPose_RobotSpace() {
        return createPose3DRobot(getDoubleArray("t6c_rs", 6));
    }

    /**
     * Gets the targeting/pipeline latency in milliseconds.
     *
     * @return The targeting/pipeline latency.
     */
    public double getTargetingLatency() {
        return jsonData.optDouble("tl", 0);
    }

    /**
     * Gets the Limelight-local monotonic timestamp of the result.
     *
     * @return The limelight-local timestamp.
     */
    public double getTimestamp() {
        return jsonData.optDouble("ts", 0);
    }

    /**
     * Gets the validity of the result.
     *
     * @return Validity (0 or 1).
     */
    public boolean isValid() {
        int v = jsonData.optInt("v", 0);
        if(v == 1)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the json parse latency.
     *
     * @return The Control Hub json parse latency in milliseconds.
     */
    public double getParseLatency() {
        return parseLatency;
    }

    /**
     * Helper method to get a double array from the JSON data.
     *
     * @param key The key for the array in the JSON data.
     * @return A double array corresponding to the key.
     */
    private double[] getDoubleArray(String key, int defaultCount) {
        JSONArray array = jsonData.optJSONArray(key);
        if (array == null) {
            return new double[defaultCount];
        }
        double[] result = new double[array.length()];
        for (int i = 0; i < array.length(); i++) {
            result[i] = array.optDouble(i);
        }
        return result;
    }

    /**
     * Helper method to create an Pose3D instance from a pose array.
     *
     * @param pose The pose array (x, y, z, roll, pitch, yaw)
     * @return An Pose3D instance without acquisition time.
     */
    protected static Pose3D createPose3DRobot(double[] pose) {
        if (pose.length < 6) {
            return new Pose3D(new Position(), new YawPitchRollAngles(AngleUnit.DEGREES,0,0,0,0));
        }
        Position position = new Position(DistanceUnit.METER, pose[0], pose[1], pose[2],0);
        YawPitchRollAngles orientation = new YawPitchRollAngles(AngleUnit.DEGREES, pose[5], pose[4], pose[3],0);
        return new Pose3D(position, orientation);
    }

    /**
     * Parses a JSONObject into an LLResult object.
     *
     * @param json The JSON to parse.
     * @return An LLResult object, or null if parsing fails.
     */
    protected static LLResult parse(JSONObject json) {
        try {
            return new LLResult(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a string representation of the LLResult.
     *
     * @return A string representation of the JSON data.
     */
    @Override
    public String toString() {
        return jsonData.toString();
    }
}