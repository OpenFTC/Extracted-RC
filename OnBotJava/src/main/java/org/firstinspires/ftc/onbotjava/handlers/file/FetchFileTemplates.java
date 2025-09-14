/*
 * Copyright (c) 2018 David Sargent
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
 * Neither the name of David Sargent nor the names of its contributors may be used to
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

package org.firstinspires.ftc.onbotjava.handlers.file;

import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils;
import org.firstinspires.ftc.onbotjava.OnBotJavaProgrammingMode;
import org.firstinspires.ftc.onbotjava.OnBotJavaWebInterfaceManager;
import org.firstinspires.ftc.onbotjava.RegisterWebHandler;
import org.firstinspires.ftc.onbotjava.StandardResponses;
import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import fi.iki.elonen.NanoHTTPD;

import static org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils.templatesDir;

@RegisterWebHandler(uri = OnBotJavaProgrammingMode.URI_FILE_TEMPLATES)
public class FetchFileTemplates implements WebHandler {
    private String response;
    private boolean templatesEnsured = true;

    /**
     * <p>Called with {@link OnBotJavaProgrammingMode#URI_FILE_TEMPLATES}</p>
     * <p>Makes a listing of the available project templates for use in JSON format</p>
     */
    private NanoHTTPD.Response projectTemplates() {
        if (response == null || !templatesEnsured) {
            List<String> templates = new ArrayList<>();
            templatesEnsured = OnBotJavaFileSystemUtils.ensureTemplates();
            String templatePath = templatesDir.getAbsolutePath();
            OnBotJavaFileSystemUtils.searchForFiles(templatePath, templatesDir, templates, true);
            List<Map<String, Object>> templateObjects = templates.stream()
                    // remove temp files and plain old directories
                    .filter(template -> !template.endsWith(OnBotJavaFileSystemUtils.EXT_TEMP_FILE))
                    // Remove path seperators
                    .map(template -> template.startsWith(OnBotJavaFileSystemUtils.PATH_SEPARATOR) ? template.substring(1) : template)
                    .map(template -> {
                        TemplateFile templateFile = new TemplateFile(new File(templatesDir, template));
                        Map<String, Object> templateObject = new HashMap<>();
                        templateObject.put("name", template);
                        if (templateFile.isExampleProject()) {
                            templateObject.put("autonomous", false);
                            templateObject.put("teleop", false);
                            templateObject.put("disabled", false);
                            templateObject.put("example", true);
                            templateObject.put("exampleProject", true);
                        } else {
                            templateObject.put("autonomous", templateFile.isAutonomous());
                            templateObject.put("teleop", templateFile.isTeleOp());
                            templateObject.put("disabled", templateFile.isDisabled());
                            templateObject.put("example", templateFile.isExample());
                            templateObject.put("exampleProject", false);
                        }

                        return templateObject;
                    })
                    .sorted(Comparator.comparing(x-> (String) x.get("name")))
                    .collect(Collectors.toList());
            response = OnBotJavaWebInterfaceManager.instance().gson().toJson(templateObjects);
        }

        return StandardResponses.successfulJsonRequest(response);
    }

    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) {
        return projectTemplates();
    }
}
