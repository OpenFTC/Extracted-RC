package org.firstinspires.ftc.onbotjava.handlers.file;

import com.qualcomm.robotcore.util.ReadWriteFile;
import com.qualcomm.robotcore.util.RobotLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.firstinspires.ftc.onbotjava.OnBotJavaFileSystemUtils.templatesDir;

public class TemplateFile {
    private static final String TAG = TemplateFile.class.getSimpleName();
    private final File file;
    private final Pattern DISABLED_PATTERN = Pattern.compile("^\\s*@Disabled.*$", Pattern.MULTILINE);
    private final Pattern AUTONOMOUS_PATTERN = Pattern.compile("^\\s*@Autonomous(|\\([^)]+\\))(.*)$", Pattern.MULTILINE);
    private final Pattern TELEOP_PATTERN = Pattern.compile("^\\s*@TeleOp(|\\([^)]+\\))(.*)$", Pattern.MULTILINE);
    private String templateData;

    public enum OpModeType {
        AUTONOMOUS,
        TELEOP
    }

    public TemplateFile(File file) {
        this.file = file;
    }

    private String readTemplateData() {
        if (templateData == null) {
            templateData = ReadWriteFile.readFile(file);
        }

        return templateData;
    }

    public boolean isDisabled() {
        return DISABLED_PATTERN.matcher(readTemplateData()).find();
    }

    public Optional<String> getDisabled() {
        Matcher matcher = DISABLED_PATTERN.matcher(readTemplateData());
        if (matcher.find()) {
            return Optional.of(matcher.group());
        }

        return Optional.empty();
    }

    public boolean isAutonomous() {
        return AUTONOMOUS_PATTERN.matcher(readTemplateData()).find();
    }

    public Optional<String> getAutonomous() {
        Matcher matcher = AUTONOMOUS_PATTERN.matcher(readTemplateData());
        if (matcher.find()) {
            return Optional.of(matcher.group());
        }

        return Optional.empty();
    }

    public boolean isTeleOp() {
        return TELEOP_PATTERN.matcher(readTemplateData()).find();
    }

    public Optional<String> getTeleOp() {
        Matcher matcher = TELEOP_PATTERN.matcher(readTemplateData());
        if (matcher.find()) {
            return Optional.of(matcher.group());
        }

        return Optional.empty();
    }

    public List<OpModeType> getOpModeType() {
        List<OpModeType> types = new ArrayList<>();
        if (isAutonomous()) {
            types.add(OpModeType.AUTONOMOUS);
        } else if (isTeleOp()) {
            types.add(OpModeType.TELEOP);
        }

        return types;
    }

    public void setOpModeType(OpModeType type) {
        if (isAutonomous() && type == OpModeType.AUTONOMOUS) {
            if (isTeleOp()) {
                templateData = TELEOP_PATTERN.matcher(readTemplateData()).replaceAll("");
            }
            return;
        } else if (isTeleOp() && type == OpModeType.TELEOP) {
            if (isAutonomous()) {
                templateData = AUTONOMOUS_PATTERN.matcher(readTemplateData()).replaceAll("");
            }
            return;
        }

        if (isAutonomous() && type == OpModeType.TELEOP) {
            templateData = AUTONOMOUS_PATTERN.matcher(readTemplateData()).replaceAll("@TeleOp$1$2");
        } else if (isTeleOp() && type == OpModeType.AUTONOMOUS) {
            templateData = TELEOP_PATTERN.matcher(readTemplateData()).replaceAll("@Autonomous$1$2");
        }
    }

    public void expandTemplateVariables(Map<String, String> valueMap) {
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            RobotLog.dd(TAG, "Processing template tag '%s'", entry.getKey());
            templateData = templateData.replaceAll("\\{\\{ \\+" + entry.getKey() + " \\}\\}", Matcher.quoteReplacement(entry.getValue()));
        }
    }


    public boolean isPureTemplate() {
        return Pattern.compile("\\{\\{ \\+\\w+ \\}\\}", Pattern.MULTILINE).matcher(readTemplateData()).find();
    }

    public boolean isExample() {
        return !isPureTemplate();
    }

    public File getFile() {
        return file;
    }
}
