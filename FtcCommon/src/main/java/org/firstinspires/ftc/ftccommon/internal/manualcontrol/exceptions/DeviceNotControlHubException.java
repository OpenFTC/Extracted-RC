package org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions;

import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;

public class DeviceNotControlHubException extends WebSocketCommandException {
    public DeviceNotControlHubException() {
        super("DeviceNotControlHub", "Device is not a Control Hub");
    }
}
