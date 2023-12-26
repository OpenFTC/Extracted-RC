package org.firstinspires.ftc.ftccommon.internal.manualcontrol.exceptions;

import org.firstinspires.ftc.robotcore.internal.webserver.websockets.WebSocketCommandException;

public class ImuNotFoundException extends WebSocketCommandException {
    public ImuNotFoundException(String message) {
        super("ImuNotFound", message);
    }
}
