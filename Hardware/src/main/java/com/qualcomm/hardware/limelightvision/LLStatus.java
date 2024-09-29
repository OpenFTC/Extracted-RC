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

import org.json.JSONException;
import org.json.JSONObject;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
/**
 * Represents the status of a Limelight.
 */
public class LLStatus {
    private Quaternion cameraQuat;
    private int cid;
    private double cpu;
    private double finalYaw;
    private double fps;
    private int hwType;
    private int ignoreNT;
    private int interfaceNeedsRefresh;
    private String name;
    private int pipeImgCount;
    private int pipelineIndex;
    private String pipelineType;
    private double ram;
    private int snapshotMode;
    private double temp;

    /**
     * Constructs an LLStatus object with default values.
     */
    public LLStatus() {
        this.cameraQuat = new Quaternion(1, 0, 0, 0, 0);
        this.cid = 0;
        this.cpu = 0.0;
        this.finalYaw = 0.0;
        this.fps = 0.0;
        this.hwType = 0;
        this.ignoreNT = 0;
        this.interfaceNeedsRefresh = 0;
        this.name = "";
        this.pipeImgCount = 0;
        this.pipelineIndex = 0;
        this.pipelineType = "";
        this.ram = 0.0;
        this.snapshotMode = 0;
        this.temp = 0.0;
    }

    /**
     * Constructs an LLStatus object from a JSON string.
     *
     * @param json The JSONobject containing Limelight status data.
     */
    protected LLStatus(JSONObject json) {
        this(); 
        if(json != null)
        {
            try {
                JSONObject quatObj = json.optJSONObject("cameraQuat");
                if (quatObj != null) {
                    this.cameraQuat.w = (float)quatObj.optDouble("w", 1);
                    this.cameraQuat.x = (float)quatObj.optDouble("x", 0);
                    this.cameraQuat.y = (float)quatObj.optDouble("y", 0);
                    this.cameraQuat.z = (float)quatObj.optDouble("z", 0);
                };
                this.cid = json.optInt("cid", 0);
                this.cpu = json.optDouble("cpu", 0.0);
                this.finalYaw = json.optDouble("finalYaw", 0.0);
                this.fps = json.optDouble("fps", 0.0);
                this.hwType = json.optInt("hwType", 0);
                this.ignoreNT = json.optInt("ignoreNT", 0);
                this.interfaceNeedsRefresh = json.optInt("interfaceNeedsRefresh", 0);
                this.name = json.optString("name", "");
                this.pipeImgCount = json.optInt("pipeImgCount", 0);
                this.pipelineIndex = json.optInt("pipelineIndex", 0);
                this.pipelineType = json.optString("pipelineType", "");
                this.ram = json.optDouble("ram", 0.0);
                this.snapshotMode = json.optInt("snapshotMode", 0);
                this.temp = json.optDouble("temp", 0.0);
            } catch (Exception e) {

            }
        }
    }

    /**
     * @return The camera quaternion as a double array [w, x, y, z].
     */
    public Quaternion getCameraQuat() {
        return cameraQuat;
    }

    /**
     * @return The camera sensor ID.
     */
    public int getCid() {
        return cid;
    }

    /**
     * @return The CPU usage as a percentage.
     */
    public double getCpu() {
        return cpu;
    }

    /**
     * @return The final yaw angle in degrees. 
     */
    public double getFinalYaw() {
        return finalYaw;
    }

    /**
     * @return The frames per second being processed.
     */
    public double getFps() {
        return fps;
    }

    /**
     * @return The hardware type identifier.
     */
    public int getHwType() {
        return hwType;
    }

    /**
     * @return The ignore NetworkTables flag.
     */
    private int getIgnoreNT() {
        return ignoreNT;
    }

    /**
     * @return The interface needs refresh flag.
     */
    private int getInterfaceNeedsRefresh() {
        return interfaceNeedsRefresh;
    }

    /**
     * @return The name of the Limelight.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The pipeline image count.
     */
    public int getPipeImgCount() {
        return pipeImgCount;
    }

    /**
     * @return The current pipeline index.
     */
    public int getPipelineIndex() {
        return pipelineIndex;
    }

    /**
     * @return The type of pipeline being used.
     */
    public String getPipelineType() {
        return pipelineType;
    }

    /**
     * @return The RAM usage as a percentage.
     */
    public double getRam() {
        return ram;
    }

    /**
     * @return The snapshot mode flag.
     */
    public int getSnapshotMode() {
        return snapshotMode;
    }

    /**
     * @return The temperature of the Limelight in degrees Celsius.
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Returns a string representation of the LLStatus object.
     *
     * @return A string containing all the status parameters.
     */
    @Override
    public String toString() {
        return "LLStatus{" +
                "cameraQuat=" + cameraQuat.toString() +
                ", cid=" + cid +
                ", cpu=" + cpu +
                ", finalYaw=" + finalYaw +
                ", fps=" + fps +
                ", hwType=" + hwType +
                ", ignoreNT=" + ignoreNT +
                ", interfaceNeedsRefresh=" + interfaceNeedsRefresh +
                ", name='" + name + '\'' +
                ", pipeImgCount=" + pipeImgCount +
                ", pipelineIndex=" + pipelineIndex +
                ", pipelineType='" + pipelineType + '\'' +
                ", ram=" + ram +
                ", snapshotMode=" + snapshotMode +
                ", temp=" + temp +
                '}';
    }
}