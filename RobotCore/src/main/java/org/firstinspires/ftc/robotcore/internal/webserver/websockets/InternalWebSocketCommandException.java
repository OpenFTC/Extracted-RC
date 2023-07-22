package org.firstinspires.ftc.robotcore.internal.webserver.websockets;

public class InternalWebSocketCommandException extends WebSocketCommandException {

    public InternalWebSocketCommandException(String message, Throwable cause) {
        super("InternalError", message);
        initCause(cause);
    }
}
