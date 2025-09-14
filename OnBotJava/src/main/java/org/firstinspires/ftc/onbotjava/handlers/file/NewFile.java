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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.blocks.ftcrobotcontroller.hardware.HardwareItem;
import com.google.blocks.ftcrobotcontroller.hardware.HardwareItemMap;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.onbotjava.JavaSourceFile;
import org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils;
import org.firstinspires.ftc.onbotjava.OnBotJavaManager;
import org.firstinspires.ftc.onbotjava.OnBotJavaProgrammingMode;
import org.firstinspires.ftc.onbotjava.OnBotJavaSecurityManager;
import org.firstinspires.ftc.onbotjava.OnBotJavaWebInterfaceManager;
import org.firstinspires.ftc.onbotjava.RegisterWebHandler;
import org.firstinspires.ftc.onbotjava.RequestConditions;
import org.firstinspires.ftc.onbotjava.StandardResponses;
import org.firstinspires.ftc.robotcore.internal.webserver.WebHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.iki.elonen.NanoHTTPD;

import static org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils.EXT_JAVA_FILE;
import static org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils.PATH_SEPARATOR;

/**
 * <ul>
 * <li>New
 * <p>
 * Requires a "new" entry in data map. If url ends in "/", a new folder will be created,
 * otherwise a new file will be created.
 * <p>
 * If a file will be created, the user can use a template provided in the "template"
 * entry in the data map, which should be inside the {@link OnBotJavaFileSystemUtils#templatesDir} folder. If
 * the user specifies no template, a default template will be used based on the file
 * extension.
 * </p>
 * <p>
 * See {@link #buildTemplateKeyMap(String, Map, File)} for details regarding additional
 * data entries for template use
 * </p>
 * </li>
 * </ul>
 */
@RegisterWebHandler(uri = OnBotJavaProgrammingMode.URI_FILE_NEW)
public class NewFile implements WebHandler {
    private static final String TAG = "OnBotJavaNewFile";
    private static final List<Class<? extends HardwareDevice>> HARDWARE_TYPES_PREVENTED_FROM_HARDWARE_SETUP = Arrays.asList(DcMotorController.class,ServoController.class,VoltageSensor.class);

    private static class RecursiveCopyOperation {
        private final File src;
        private final File dest;

        private RecursiveCopyOperation(File src, File dest) {
            this.src = src;
            this.dest = dest;
        }
    }

    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession session) {
        final Map<String, List<String>> data = session.getParameters();
        if (!RequestConditions.containsParameters(session, RequestConditions.REQUEST_KEY_NEW, RequestConditions.REQUEST_KEY_FILE)) {
            return StandardResponses.badRequest();
        }

        final String fileNameUri = RequestConditions.dataForParameter(session, RequestConditions.REQUEST_KEY_FILE);
        File file = new File(OnBotJavaManager.javaRoot, fileNameUri);
        if (!RequestConditions.containsParameters(session, RequestConditions.REQUEST_KEY_TEMPLATE) && fileNameUri.endsWith(PATH_SEPARATOR)) {
            file.mkdirs();
            return StandardResponses.successfulRequest(relativePathToJavaRoot(file));
        } else {
            Map<String, String> templateKeyMap = buildTemplateKeyMap(fileNameUri, data, file);
            if (!OnBotJavaSecurityManager.isValidSourceFileOrFolder(fileNameUri)) {
                RobotLog.ii(TAG, "request did not include a valid source file or folder");
                return StandardResponses.badRequest();
            }
            if (RequestConditions.containsParameters(session, RequestConditions.REQUEST_KEY_TEMPLATE)) {
                String template = RequestConditions.dataForParameter(session, RequestConditions.REQUEST_KEY_TEMPLATE);
                boolean preserveAnnotations = RequestConditions.containsParameters(session, RequestConditions.REQUEST_KEY_PRESERVE);
                if (OnBotJavaSecurityManager.isValidTemplateFile(template)) {
                    File templateFile = new File(OnBotJavaManager.javaRoot, template);
                    if (templateFile.exists() && !templateFile.isDirectory()) {
                        RobotLog.ii(TAG, "creating new folder using template file");
                        newFileFromTemplate(file, templateKeyMap, templateFile, preserveAnnotations, false);
                        return StandardResponses.successfulRequest(relativePathToJavaRoot(file));
                    } else if (templateFile.isDirectory()) {
                        RobotLog.ii(TAG, "creating new folder using template folder");
                        File dest;
                        try {
                            List<RecursiveCopyOperation> opsList = new ArrayList<>();
                            dest = generateRecursiveCopyList(templateFile, file, opsList);
                            executeRecursiveCopy(opsList, templateKeyMap, true, true);
                        } catch (IOException ex) {
                            RobotLog.ee(TAG, "cannot create vendored sample, using: " + templateFile.getAbsolutePath(), ex);
                            return StandardResponses.serverError("cannot create vendored sample");
                        }
                        return StandardResponses.successfulRequest(relativePathToJavaRoot(dest));
                    } else {
                        RobotLog.ii(TAG, "template file does not exist, looking for: " + templateFile.getAbsolutePath());
                    }
                }
                RobotLog.ii(TAG, "template file did not match a valid template file");

                return StandardResponses.badRequest();
            } else { // use default extension based template if any
                newFileFromTemplate(file, templateKeyMap, null, false, false);
                return StandardResponses.successfulRequest(relativePathToJavaRoot(file));
            }
        }
    }

    private String relativePathToJavaRoot(File file) {
        String absoluteJavaRoot = OnBotJavaManager.javaRoot.getAbsolutePath();
        return file.getAbsolutePath().substring(absoluteJavaRoot.length());
    }

    private Map<String, String> buildTemplateKeyMap(String uri, Map<String, List<String>> data, File file) {
        String name = file.getName();
        String packageName = packageNameFromUri(uri, name);
        name = name.lastIndexOf('.') == -1 ? name : name.substring(0, name.lastIndexOf('.'));
        String opModeName = data.containsKey(RequestConditions.REQUEST_KEY_OPMODE_NAME) ?
                data.get(RequestConditions.REQUEST_KEY_OPMODE_NAME).get(0) : name;

        String opModeAnnotations = data.containsKey(RequestConditions.REQUEST_KEY_OPMODE_ANNOTATIONS) ?
                data.get(RequestConditions.REQUEST_KEY_OPMODE_ANNOTATIONS).get(0) : "";
        opModeAnnotations = opModeAnnotations
                .replaceAll("\\{\\{ \\+opModeName \\}\\}", opModeName);

        HashMap<String, String> results = new HashMap<>();
        results.put("packageName", packageName);
        results.put("opModeAnnotations", opModeAnnotations);
        results.put("name", name);
        results.put("opModeName", opModeName);
        results.put("year", new SimpleDateFormat("yyyy", Locale.US).format(new Date()));
        if (data.containsKey(RequestConditions.REQUEST_KEY_TEAM_NAME)) {
            results.put("teamName", data.get("teamName").get(0));
        }

        if (data.containsKey(RequestConditions.REQUEST_KEY_SETUP_HARDWARE) && data.get(RequestConditions.REQUEST_KEY_SETUP_HARDWARE).get(0).equals("1")) {
            final HardwareItemMap hardwareItemMap = HardwareItemMap.newHardwareItemMap();
            StringBuilder rcHardwareFieldsBuilder = new StringBuilder();
            StringBuilder rcHardwareSetupBuilder = new StringBuilder();
            Set<String> knownDeviceNames = new HashSet<>();
            for (HardwareItem device : hardwareItemMap.getAllHardwareItems()) {
                String deviceName = device.deviceName;
                char[] sanitizedDeviceNameChars = deviceName.toCharArray();
                if (sanitizedDeviceNameChars.length > 0) {
                    // make the device name the right case
                    char sanitizedDeviceNameChar = Character.toLowerCase(sanitizedDeviceNameChars[0]);
                    sanitizedDeviceNameChars[0] = Character.isJavaIdentifierStart(sanitizedDeviceNameChar) ? sanitizedDeviceNameChar : '_';
                }

                for (int i = 1; i < sanitizedDeviceNameChars.length; i++) {
                    char sanitizedDeviceNameChar = sanitizedDeviceNameChars[i];
                    sanitizedDeviceNameChars[i] = Character.isJavaIdentifierPart(sanitizedDeviceNameChar) ? sanitizedDeviceNameChar : '_';
                }

                final String sanitizedDeviceName = new String(sanitizedDeviceNameChars);

                // Prevent fields with the same name from being created
                if (knownDeviceNames.contains(sanitizedDeviceName)) continue;
                knownDeviceNames.add(sanitizedDeviceName);

                Class typeClass = getHardwareTypeName(device.hardwareType.deviceType);
                if (typeClass == null)
                    typeClass = device.hardwareType.deviceType;
                if (HARDWARE_TYPES_PREVENTED_FROM_HARDWARE_SETUP.indexOf(typeClass) >= 0)
                    continue;

                String typeName = typeClass.getSimpleName();

                if (OnBotJavaWebInterfaceManager.instance().editorSettings().whitespaceSetting.get().equals("tab")) {
                    rcHardwareFieldsBuilder.append('\t');
                    rcHardwareSetupBuilder.append("\t\t");
                } else {
                    for (int i = 0; i < OnBotJavaWebInterfaceManager.instance().editorSettings().spacesToTabSetting.get(); i++) {
                        rcHardwareFieldsBuilder.append(' ');
                        // two spaces since hardware setup is two indents deep
                        rcHardwareSetupBuilder.append("  ");
                    }
                }

                rcHardwareFieldsBuilder.append(String.format(Locale.ENGLISH, "private %s %s;\n", typeName, sanitizedDeviceName));
                rcHardwareSetupBuilder.append(String.format(Locale.ENGLISH, "%s = hardwareMap.get(%s.class, \"%s\");\n", sanitizedDeviceName, typeName, deviceName));
            }

            results.put("rcHardwareFields", rcHardwareFieldsBuilder.toString());
            results.put("rcHardwareSetup", rcHardwareSetupBuilder.toString());
        }

        return results;
    }

    private File generateRecursiveCopyList(File origin, File dest, List<? super RecursiveCopyOperation> filesToCopy) throws IOException {
        dest = checkForSameNameConflicts(dest);
        if (origin.isDirectory()) {
            //if (dest.exists() && !dest.isDirectory()) throw new IOException("Cannot merge origin and destination");
            filesToCopy.add(new RecursiveCopyOperation(origin, dest));
            final String[] files = origin.list();
            for (String file : files) {
                File src = new File(origin, file);
                File destFile = new File(dest, file);
                destFile = checkForSameNameConflicts(destFile);
                // Prevent a file from being copied endlessly, if we are copying the parent folder to the inside of itself
                if (src.getAbsolutePath().equals(dest.getAbsolutePath())) {
                    continue;
                }

                if (src.isDirectory()) {
                    filesToCopy.add(new RecursiveCopyOperation(src, destFile));
                }

                generateRecursiveCopyList(src, destFile, filesToCopy);
            }
        } else {
            filesToCopy.add(new RecursiveCopyOperation(origin, dest));
        }
        return dest;
    }

    private void executeRecursiveCopy(List<? extends RecursiveCopyOperation> ops, Map<String, String> templateKeyMap, boolean preserveAnnotations, boolean stripDisabled) throws IOException {
        for (RecursiveCopyOperation op : ops) {
            File src = op.src;
            File dst = op.dest;
            if (src.isDirectory()) {
                dst.mkdirs();
            } else {
                copyFile(src, dst, templateKeyMap, preserveAnnotations, stripDisabled);
            }
        }
    }

    /**
     * Copies the given source File to the given dest File.
     */
    private void copyFile(File source, File dest, Map<String, String> templateKeyMap, boolean preserveAnnotations, boolean stripDisabled) throws IOException {
        dest = checkForSameNameConflicts(dest);

        boolean isSampleFile = !source.getName().contains(".");
        if (isSampleFile) {
            dest = new File(dest.getParentFile(), dest.getName() + ".java");
            newFileFromTemplate(dest, templateKeyMap, source, preserveAnnotations, stripDisabled);
        } else if (source.getPath().endsWith(OnBotJavaFileSystemUtils.EXT_JAVA_FILE)) {
            JavaSourceFile.forFile(source).copyTo(dest);
        } else {
            try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
                 FileChannel destChannel = new FileOutputStream(dest).getChannel()) {
                destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
            }
        }
    }

    private File checkForSameNameConflicts(File dest) {
        if (dest.exists()) {
            String originalName = dest.getName();
            String ext = "";
            if (originalName.contains(".")) {
                ext = originalName.substring(originalName.lastIndexOf('.'));
                originalName = originalName.substring(0, originalName.lastIndexOf('.'));
            }
            String suffix = "_Copy";
            if (originalName.endsWith(suffix)) {
                suffix = "";
            }
            dest = new File(dest.getParentFile(), originalName + suffix + ext);
            for (int i = 2; i < 1000 && dest.exists(); i++) {
                dest = new File(dest.getParentFile(), originalName + suffix + i + ext);
            }
        }
        return dest;
    }

    private Class getHardwareTypeName(Class typeClass) {
        if (typeClass.equals(Object.class)) return null;

        final String packageName = "com.qualcomm.robotcore.hardware";
        if (typeClass.getPackage().getName().equals(packageName)) {
            return typeClass;
        }

        for (Class<?> klazz : typeClass.getInterfaces()) {
            if (klazz.getPackage().getName().equals(packageName)) {
                return klazz;
            }
        }

        if (typeClass.getSuperclass().getPackage().getName().equals(packageName))  {
            return typeClass.getSuperclass();
        }

        return getHardwareTypeName(typeClass.getSuperclass());
    }

    @NonNull
    private String packageNameFromUri(String uri, String name) {
        String packageName = uri;
        if (packageName.indexOf("/jars") == 0) {
            packageName = packageName.substring("/jars".length());
        } else if (packageName.indexOf("/src") == 0) {
            packageName = packageName.substring("/src".length());
        }
        packageName = packageName.substring(0, packageName.lastIndexOf(name));
        if (!packageName.isEmpty()) {
            if (packageName.equals(PATH_SEPARATOR)) return "";
            packageName = packageName.indexOf(PATH_SEPARATOR) == 0 ? packageName.substring(1) : packageName;
            final int packageNameLength = packageName.length();
            packageName = packageName.lastIndexOf(PATH_SEPARATOR) == packageNameLength - 1 ?
                    packageName.substring(0, packageNameLength - 1) : packageName;
            packageName = packageName.replace(PATH_SEPARATOR, ".");
        }
        return packageName;
    }

    private void newFileFromTemplate(File file, Map<String, String> templateKeyMap, @Nullable File templateSource, boolean preserveAnnotations, boolean preserveDisabled) {
        String templateData;
        if (templateSource == null) {
            templateData = defaultTemplateForFile(file);
        } else {
            templateData = ReadWriteFile.readFile(templateSource);
        }
        templateData = parseTemplate(templateData, templateKeyMap, preserveAnnotations, preserveDisabled);

        // Since a template could possibly be just a raw sample, we need to take measures to ensure that
        // everything appears to be right to the user
        if (templateSource != null) {
            JavaSourceFile javaTemplateSourceFile = JavaSourceFile.forFile(templateSource);
            String oldClassName = javaTemplateSourceFile.className();
            // The templateData is not equal to the source in the source file
            String oldPackageName = JavaSourceFile.extractPackageNameFromContents(templateData);
            javaTemplateSourceFile.writeJavaFileFromContents(oldClassName, oldPackageName, templateData, file);
        } else {
            ReadWriteFile.writeFile(file, templateData);
        }
    }

    @NonNull
    private String defaultTemplateForFile(File uri) {
        String newFileData;
        if (uri.getAbsolutePath().endsWith(EXT_JAVA_FILE)) {
            newFileData = "package {{ +packageName }};\n" +
                    "\n" +
                    "{{ +opModeAnnotations }}\n" +
                    "public class {{ +name }} {\n" +
                    "{{ +rcHardwareFields }}\n" +
                    "\t// todo: write your code here\n" +
                    "}";
        } else {
            newFileData = "";
        }
        return newFileData;
    }

    private String parseTemplate(String templateData, Map<String, String> valueMap, boolean preserveAnnotations, boolean stripDisabled) {
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            RobotLog.dd(TAG, "Processing template tag '%s'", entry.getKey());
            templateData = templateData.replaceAll("\\{\\{ \\+" + entry.getKey() + " \\}\\}", Matcher.quoteReplacement(entry.getValue()));
        }

        // Strips the @Disabled annotation even though `preserveAnnotations` is true
        if (preserveAnnotations && stripDisabled) {
            Pattern pattern = Pattern.compile("^\\s*@Disabled.*$", Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(templateData);
            if (matcher.find()) {
                templateData = matcher.replaceAll("");
            }
        }

        if (!preserveAnnotations && valueMap.containsKey("opModeAnnotations")) {
            String opModeAnnotations = valueMap.get("opModeAnnotations");
            if (!opModeAnnotations.contains("@Disabled")) {
                Pattern pattern = Pattern.compile("^\\s*@Disabled.*$", Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(templateData);
                if (matcher.find()) {
                    templateData = matcher.replaceAll("");
                }
            }

            if (!opModeAnnotations.contains("@Autonomous")) {
                Pattern pattern = Pattern.compile("^\\s*@Autonomous(|\\([^)]+\\))(.*)$", Pattern.MULTILINE);
                if (opModeAnnotations.contains("@TeleOp")) {
                    templateData = pattern.matcher(templateData).replaceAll("@TeleOp$1$2");
                } else {
                    templateData = pattern.matcher(templateData).replaceAll("");
                }
            }

            if (!opModeAnnotations.contains("@TeleOp")) {
                Pattern pattern = Pattern.compile("^\\s*@TeleOp(|\\([^)]+\\))(.*)$", Pattern.MULTILINE);
                if (opModeAnnotations.contains("@Autonomous")) {
                    templateData = pattern.matcher(templateData).replaceAll("@Autonomous$1$2");
                } else {
                    templateData = pattern.matcher(templateData).replaceAll("");
                }
            }
        }

        templateData = templateData.replaceAll("\\{\\{ \\+\\w+ \\}\\}", "");
        return templateData;
    }
}
