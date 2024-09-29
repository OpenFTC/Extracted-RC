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

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.util.SerialNumber;

import org.firstinspires.ftc.robotcore.internal.usb.EthernetOverUsbSerialNumber;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.qualcomm.hardware.limelightvision.LLResultTypes.*;
import com.qualcomm.hardware.limelightvision.LLFieldMap;
import java.util.concurrent.atomic.AtomicInteger; 


/**
 * Driver for Limelight3A Vision Sensor.
 *
 * {@link Limelight3A } provides support for the Limelight Vision Limelight3A Vision Sensor.
 *
 * @see <a href="https://limelightvision.io/">Limelight</a>
 *
 * Notes on configuration:
 *
 *   The device presents itself, when plugged into a USB port on a Control Hub as an ethernet
 *   interface.  A DHCP server running on the Limelight automatically assigns the Control Hub an
 *   ip address for the new ethernet interface.
 *
 *   Since the Limelight is plugged into a USB port, it will be listed on the top level configuration
 *   activity along with the Control Hub Portal and other USB devices such as webcams.  Typically
 *   serial numbers are displayed below the device's names.  In the case of the Limelight device, the
 *   Control Hub's assigned ip address for that ethernet interface is used as the "serial number".
 *
 *   Tapping the Limelight's name, transitions to a new screen where the user can rename the Limelight
 *   and specify the Limelight's ip address.  Users should take care not to confuse the ip address of
 *   the Limelight itself, which can be configured through the Limelight settings page via a web browser,
 *   and the ip address the Limelight device assigned the Control Hub and which is displayed in small text
 *   below the name of the Limelight on the top level configuration screen.
 */
public class Limelight3A implements HardwareDevice
{
    private static final String TAG = "Limelight3A";
    private InetAddress inetAddress;
    private EthernetOverUsbSerialNumber serialNumber;
    private String name;

    private final String baseUrl;
    private volatile LLResult latestResult;
    private volatile ScheduledExecutorService executor;
    private final Object executorLock = new Object();

    private final AtomicInteger state = new AtomicInteger(0);
    private static final int STOPPED = 0, STARTING = 1, RUNNING = 2;

    private volatile boolean isExecutorInitialized = false;

    private volatile long lastUpdateTime = 0;
    private static final long ISCONNECTED_THRESHOLD = 250;
    private static final int CONNECTION_TIMEOUT = 100;
    private static final int GETREQUEST_TIMEOUT = 100;
    private static final int POSTREQUEST_TIMEOUT = 15000;
    private static final int DELETEREQUEST_TIMEOUT = 15000;

    private static final int PYTHON_INPUT_MAX_SIZE = 32;
    private long pollIntervalMs = 10; // Default poll rate = 100Hz
    private static final int MIN_POLL_RATE_HZ = 1;
    private static final int MAX_POLL_RATE_HZ = 250;


    public Limelight3A(SerialNumber serialNumber, String name, InetAddress ipAddress) {
        this.name = name;
        this.serialNumber = (EthernetOverUsbSerialNumber)serialNumber;
        this.inetAddress = ipAddress;
        this.baseUrl = "http://" + ipAddress.getHostAddress() + ":5807";
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Allows for re-initialization
     */
    private synchronized void ensureExecutorRunning() {
        synchronized (executorLock) { 
            //lock to make sure executor doesn't change right after we check here (bh)
            if (executor == null || executor.isShutdown()) {
                executor = Executors.newSingleThreadScheduledExecutor();
                isExecutorInitialized = false;
            }
        }
    }

    /**
     * Starts or resumes periodic polling of Limelight data.
     */
    public synchronized void start() {
        // handling rapid consecutive start calls with getandset bh
        if (!state.compareAndSet(STOPPED, STARTING)) {
            return;
        }

        try {
            ensureExecutorRunning();
            if (!isExecutorInitialized) {
                executor.scheduleAtFixedRate(this::updateLatestResult, 0, pollIntervalMs, TimeUnit.MILLISECONDS);
                isExecutorInitialized = true;
            }
            state.set(RUNNING);
        } catch (Exception e) {
            state.set(STOPPED);
        }
    }

    /**
     * Pauses polling of Limelight data.
     */
    public synchronized void pause() {
        if (state.get() == RUNNING) {
            state.set(STOPPED);
        }
    }


    /**
     * Stops polling of Limelight data.
     */
    public synchronized void stop() {
        state.set(STOPPED);
        isExecutorInitialized = false;
        if (!executor.isShutdown()) {
            executor.shutdownNow();
        }
    }
    
    /**
     * Checks if the polling is enabled.
     *
     * @return true if the polling is enabled
     */
    public boolean isRunning() {
        return state.get() == RUNNING;
    }
    
    /**
     * Sets the poll rate in Hertz (Hz). Must be called before start()
     * The rate is clamped between 1 and 250 Hz.
     *
     * @param rateHz The desired poll rate in Hz.
     */
    public synchronized void setPollRateHz(int rateHz) {
        if (state.get() == RUNNING)
        {
            return;  // Early return if the executor is running
        }
    
        int clampedRate = Math.max(MIN_POLL_RATE_HZ, Math.min(rateHz, MAX_POLL_RATE_HZ));
        this.pollIntervalMs = 1000 / clampedRate;
    }

    /**
     * Gets the time elapsed since the last update.
     *
     * @return The time in milliseconds since the last update.
     */
    public long getTimeSinceLastUpdate() {
        return System.currentTimeMillis() - lastUpdateTime;
    }

    /**
     * Checks if the Limelight is currently connected.
     *
     * @return true if connected, false otherwise.
     */
    public boolean isConnected() {
        return getTimeSinceLastUpdate() <= ISCONNECTED_THRESHOLD;
    }

    /**
     * Updates the latest result from the Limelight.
     */
    private void updateLatestResult() {
        if (state.get() != RUNNING) return;

        try {

            JSONObject result = sendGetRequest("/results");
            if (result != null) {
                this.latestResult = LLResult.parse(result);
                this.lastUpdateTime = System.currentTimeMillis();
            }
        }
        catch (Exception e){
            //todo
        }
    }

    /**
     * Gets the latest result from the Limelight.
     *
     * @return The latest LLResult object.
     */
    public LLResult getLatestResult() {
        return this.latestResult;
    }

    /**
     * Gets the current status of the Limelight.
     *
     * @return A populated LLStatus object, or a default status object if request fails.
     */
    public LLStatus getStatus() {
        JSONObject statusJson = sendGetRequest("/status");
        if(statusJson == null)
        {
            return new LLStatus();
        }
        return new LLStatus(statusJson);
    }

    /**
     * Gets the hardware report from the Limelight. The Hardware Report contains calibration data.
     * This is a highly advanced feature that is not necessary for FTC Teams. Marked private for now.
     * 
     * @return A JSONObject containing the hardware report.
     */
    private JSONObject getHWReport() {
        return sendGetRequest("/hwreport");
    }

    /**
     * Reloads the current Limelight pipeline.
     *
     * @return true if successful, false otherwise.
     */
    public boolean reloadPipeline() {
        return sendPostRequest("/reload-pipeline", null);
    }

    /**
     * Gets the default pipeline.
     *
     * @return A JSONObject containing the default pipeline configuration.
     */
    private JSONObject getPipelineDefault() {
        return sendGetRequest("/pipeline-default");
    }

    /**
     * Gets the pipeline at a specific index.
     *
     * @param index The index of the pipeline to retrieve.
     * @return A JSONObject containing the pipeline configuration.
     */
    private JSONObject getPipelineAtIndex(int index) {
        return sendGetRequest("/pipeline-atindex?index=" + index);
    }

    /**
     * Switches to a pipeline at the specified index.
     *
     * @param index The index of the pipeline to switch to.
     * @return true if successful, false otherwise.
     */
    public boolean pipelineSwitch(int index) {
        return sendPostRequest("/pipeline-switch?index=" + index, null);
    }

    /**
     * Gets the names of available snapscripts.
     * This method is not necessary for FTC teams. Marked as private
     *
     * @return A JSONObject containing the names of available snapscripts.
     */
    private JSONObject getSnapscriptNames() {
        return sendGetRequest("/getsnapscriptnames");
    }

    /**
     * Captures a snapshot with the given name.
     *
     * @param snapname The name to give to the captured snapshot.
     * @return true if successful, false otherwise.
     */
    public boolean captureSnapshot(String snapname) {
        return sendPostRequest("/capture-snapshot?snapname=" + snapname, null);
    }

    /**
     * Gets the manifest of available snapshots.
     * This method is not necessary for FTC teams. Marked as private
     *
     * @return A JSONObject containing the snapshot manifest.
     */
    private JSONObject snapshotManifest() {
        return sendGetRequest("/snapshotmanifest");
    }

    /**
     * Deletes all snapshots.
     *
     * @return true if successful, false otherwise.
     */
    public boolean deleteSnapshots() {
        return sendDeleteRequest("/delete-snapshots");
    }

    /**
     * Deletes a specific snapshot.
     *
     * @param snapname The name of the snapshot to delete.
     * @return true if successful, false otherwise.
     */
    public boolean deleteSnapshot(String snapname) {
        return sendDeleteRequest("/delete-snapshot?snapname=" + snapname);
    }

    /**
     * Updates the current pipeline configuration.
     *
     * @param profileJson The new pipeline configuration as a JSONObject.
     * @param flush Whether to flush the configuration to persistent storage.
     * @return true if successful, false otherwise.
     */
    private boolean updatePipeline(JSONObject profileJson, boolean flush) {
        String url = "/update-pipeline" + (flush ? "?flush=1" : "");
        return sendPostRequest(url, profileJson.toString());
    }

    /**
     * Updates the Python SnapScript inputs with 8 double values.
     *
     * @param input1 The first input value
     * @param input2 The second input value
     * @param input3 The third input value
     * @param input4 The fourth input value
     * @param input5 The fifth input value
     * @param input6 The sixth input value
     * @param input7 The seventh input value
     * @param input8 The eighth input value
     * @return True if successful, false otherwise.
     */
    public boolean updatePythonInputs(double input1, double input2, double input3, double input4,
                                      double input5, double input6, double input7, double input8) {
        double[] inputs = new double[]{input1, input2, input3, input4, input5, input6, input7, input8};
        return updatePythonInputs(inputs);
    }

    /**
     * Updates the Python SnapScript inputs.
     *
     * @param inputs A double array containing the new Python inputs.
     * @return True if successful, false otherwise. False if array is empty or contains more than 32 elements
     */
    public boolean updatePythonInputs(double[] inputs) {
        if (inputs == null || inputs.length == 0 || inputs.length > PYTHON_INPUT_MAX_SIZE) {
            return false; // Invalid input size
        }
        
        try {
            JSONArray jsonArray = new JSONArray(inputs);
            return sendPostRequest("/update-pythoninputs", jsonArray.toString());
        } catch (Exception e) {
            //
            return false;
        }
    }

    /**
     * Updates the robot orientation for MegaTag2.
     *
     * @param yaw The yaw value of the robot, aligned with the field map
     * @return true if successful, false otherwise.
     */
    public boolean updateRobotOrientation(double yaw) {
        double[] orientationData = {yaw, 0, 0, 0, 0, 0};
        try{
            JSONArray jsonArray = new JSONArray(orientationData);  
            return sendPostRequest("/update-robotorientation", jsonArray.toString());
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Uploads a pipeline to a specific slot.
     *
     * @param profileJson The new pipeline configuration as a JSONObject.
     * @param index The index at which to upload the pipeline (null for default).
     * @return true if successful, false otherwise.
     */
    private boolean uploadPipeline(JSONObject profileJson, Integer index) {
        String url = "/upload-pipeline" + (index != null ? "?index=" + index : "");
        return sendPostRequest(url, profileJson.toString());
    }

    /**
     * Uploads a new fiducial field map. Early exits if map is empty or doesn't specify a type
     *
     * @param fieldmap The new field map configuration.
     * @param index The index at which to upload the field map (null for default).
     * @return true if successful, false otherwise.
     */
    public boolean uploadFieldmap(LLFieldMap fieldmap, Integer index) {
        if(!fieldmap.isValid())
        {
            return false;
        }
        String url = "/upload-fieldmap" + (index != null ? "?index=" + index : "");
        return sendPostRequest(url, fieldmap.toJson().toString());
    }

    /**
     * Uploads new Python code.
     *
     * @param pythonString The Python code as a string.
     * @param index The index at which to upload the Python code (null for default).
     * @return true if successful, false otherwise.
     */
    public boolean uploadPython(String pythonString, Integer index) {
        String url = "/upload-python" + (index != null ? "?index=" + index : "");
        return sendPostRequest(url, pythonString);
    }

    /**
     * Gets the default calibration data.
     *
     * @return A CalibrationResult containing the default calibration data. 
     */
    public CalibrationResult getCalDefault() {
        JSONObject calData = sendGetRequest("/cal-default");
        return new CalibrationResult(calData);
    }


    /**
     * Gets calibration data from the user-generated calibration file.
     *
     * @return A CalibrationResult containing the calibration data from the file.
     */
    public CalibrationResult getCalFile() {
        JSONObject calData = sendGetRequest("/cal-file");
        return new CalibrationResult(calData);
    }

    /**
     * Gets the calibration data from the Limelight EEPROM.
     *
     * @return A CalibrationResult containing the calibration data from the EEPROM.
     */
    public CalibrationResult getCalEEPROM() {
        JSONObject calData = sendGetRequest("/cal-eeprom");
        return new CalibrationResult(calData);
    }

    /**
     * Gets the latest calibration result. This result is not necessarily used by the camera in any way
     *
     * @return A CalibrationResult containing the latest calibration result.
     */
    public CalibrationResult getCalLatest() {
        JSONObject calData = sendGetRequest("/cal-latest");
        return new CalibrationResult(calData);
    }

    /**
     * Upload calibration data to the EEPROM
     *
     * @param calResult A CalibrationResult containing the new calibration data.
     * @return true if successful, false otherwise.
     */
    private boolean updateCalEEPROM(CalibrationResult calResult) {
        return sendPostRequest("/cal-eeprom", calResult.toJson().toString());
    }

    /**
     * Updates the calibration file stored in the Limelight filesystem
     *
     * @param calResult A CalibrationResult containing the new calibration data.
     * @return true if successful, false otherwise.
     */
    private boolean updateCalFile(CalibrationResult calResult) {
        return sendPostRequest("/cal-file", calResult.toJson().toString());
    }

    /**
     * Deletes the latest calibration data.
     *
     * @return true if successful, false otherwise.
     */
    private boolean deleteCalLatest() {
        return sendDeleteRequest("/cal-latest");
    }

    /**
     * Deletes the calibration data from the EEPROM.
     *
     * @return true if successful, false otherwise.
     */
    private boolean deleteCalEEPROM() {
        return sendDeleteRequest("/cal-eeprom");
    }

    /**
     * Deletes the calibration data from the file.
     *
     * @return true if successful, false otherwise.
     */
    private boolean deleteCalFile() {
        return sendDeleteRequest("/cal-file");
    }

    /**
     * Sends a GET request to the specified endpoint.
     *
     * @param endpoint The endpoint to send the request to.
     * @return A JSONObject containing the response, or null if the request fails.
     */
    private JSONObject sendGetRequest(String endpoint) {
        HttpURLConnection connection = null;
        try {
            String urlString = baseUrl + endpoint;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(GETREQUEST_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = readResponse(connection);
                return new JSONObject(response);
            } else {
                //System.out.println("HTTP GET Error: " + responseCode);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * Reads the response from an HTTP connection.
     *
     * @param connection The HttpURLConnection to read from.
     * @return A String containing the response.
     * @throws IOException If an I/O error occurs.
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    /**
     * Sends a POST request to the specified endpoint.
     *
     * @param endpoint The endpoint to send the request to.
     * @param data The data to send in the request body.
     * @return true if successful, false otherwise.
     */
    private boolean sendPostRequest(String endpoint, String data) {
        HttpURLConnection connection = null;
        try {
            String urlString = baseUrl + endpoint;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(POSTREQUEST_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            if (data != null) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = data.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                //System.out.println("HTTP POST Error: " + responseCode);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    /**
     * Sends a DELETE request to the specified endpoint.
     *
     * @param endpoint The endpoint to send the request to.
     * @return true if successful, false otherwise.
     */
    private boolean sendDeleteRequest(String endpoint) {
        HttpURLConnection connection = null;
        try {
            String urlString = baseUrl + endpoint;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setReadTimeout(DELETEREQUEST_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                //System.out.println("HTTP DELETE Error: " + responseCode);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally{
            if (connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

    /**
     * Shuts down the Limelight connection and stops all ongoing processes.
     */
    public void shutdown() {
        stop();
    }

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.LimelightVision;
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public String getConnectionInfo() {
        return serialNumber.toString();
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void resetDeviceConfigurationForOpMode() {
    }

    @Override
    public void close() {
        stop();
    }

}
