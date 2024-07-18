/*
 * Copyright (c) 2022 Michael Hoogasian
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of Michael Hoogasian nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcore.internal.camera.calibration;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import androidx.annotation.XmlRes;

import com.qualcomm.robotcore.R;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraCalibrationHelper
{
    public static final String TAG = "CameraCalibrationHelper";

    protected static CameraCalibrationHelper instance;

    /**
     * The resources (if any, may be empty or null) used to provide additional camera calibration data for
     * webcams used with Vuforia. Using calibrated camera helps increase the accuracy of
     * Vuforia image target tracking. Calibration for several cameras is built into the FTC SDK;
     * the optional resources here provides a means for teams to provide calibrations beyond this
     * built-in set.
     *
     * The format required of the XML resource is indicated in the teamwebcamcalibrations
     * resource provided.
     *
     * @see #webcamCalibrationFiles
     */
    protected @XmlRes int[] webcamCalibrationResources = new int[] { R.xml.teamwebcamcalibrations };

    /**
     * Camera calibrations resident in files instead of resources.
     * @see #webcamCalibrationResources
     */
    protected File[] webcamCalibrationFiles = new File[] {};

    protected final CameraCalibrationManager calibrationManager;

    protected CameraCalibrationHelper()
    {
        List<XmlPullParser> parsers = new ArrayList<>();
        List<FileInputStream> inputStreams = new ArrayList<>();
        try {
            // Get XmlPullParsers for everything
            for (@XmlRes int calibrationsResource : webcamCalibrationResources)
            {
                Resources resources = AppUtil.getDefContext().getResources();
                try {
                    XmlResourceParser parser = resources.getXml(calibrationsResource);
                    parsers.add(parser);
                }
                catch (RuntimeException e)
                {
                    RobotLog.ee(TAG, e, "exception opening XML resource %d; ignoring", calibrationsResource);
                }
            }
            for (File file : webcamCalibrationFiles)
            {
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    inputStreams.add(inputStream);
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(inputStream, null);
                    parsers.add(parser);
                }
                catch (FileNotFoundException | XmlPullParserException |RuntimeException e)
                {
                    RobotLog.ee(TAG, e, "exception opening XML file %s; ignoring", file);
                }
            }

            // Build calibrations using the parsers
            this.calibrationManager = new CameraCalibrationManager(parsers);
        }
        finally
        {
            // Close down all the parsers
            for (XmlPullParser parser : parsers)
            {
                if (parser instanceof XmlResourceParser)
                {
                    ((XmlResourceParser)parser).close();
                }
            }
            for (FileInputStream fileInputStream : inputStreams)
            {
                try {fileInputStream.close(); } catch (IOException e) {/*ignore*/}
            }
        }
    }

    /**
     * Get the singleton instance of CameraCalibrationHelper
     * @return the singleton instance of CameraCalibrationHelper
     */
    public static synchronized CameraCalibrationHelper getInstance()
    {
        if (instance == null)
        {
            instance = new CameraCalibrationHelper();
        }

        return instance;
    }

    /**
     * Get a {@link CameraCalibration} for a given identity and size
     * @param identity the {@link CameraCalibrationIdentity} for the camera in question
     * @param width resolution width
     * @param height resolution height
     * @return {@link CameraCalibration}, may be null
     */
    public CameraCalibration getCalibration(CameraCalibrationIdentity identity, int width, int height)
    {
        if (identity == null)
        {
            return null;
        }

        return calibrationManager.getCalibration(identity, new Size(width, height));
    }

    /**
     * Get all calibrated resolutions for a given identity
     * @param ident identity of camera in question
     * @return all calibrated resolutions
     */
    public List<CameraCalibration> getCalibrations(CameraCalibrationIdentity ident)
    {
        return calibrationManager.getCalibrations(ident);
    }
}
