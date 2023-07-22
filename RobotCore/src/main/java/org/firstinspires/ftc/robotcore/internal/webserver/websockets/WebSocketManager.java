package org.firstinspires.ftc.robotcore.internal.webserver.websockets;

import androidx.annotation.NonNull;

public interface WebSocketManager {
    String SYSTEM_NAMESPACE = "system";

    /**
     * Version of the WebSocket core implementation.
     * This value should be incremented any time a
     * breaking change is made to the core functionality
     * of our websockets (commands and messages). Clients
     * will read this value from rcInfo.json to determine
     * if they are compatible with WebSocket commands and
     * messages.
     */
    int WEBSOCKET_API_VERSION = 1;

    /**
     * Register a namespace handler. All received messages with a namespace matching
     * handler.getNamespace() will be forwarded to the handler.
     *
     * @param handler The class that will handle notifications for messages, subscriptions, and unsubscriptions
     * @throws IllegalArgumentException if the namespace is invalid, reserved, or already registered with a handler
     */
    void registerNamespaceHandler(@NonNull WebSocketNamespaceHandler handler);

    /**
     * Register a namespace as broadcast-only. You can broadcast to it from Java, and js clients can
     * subscribe to it, but any messages that the client sends will be logged and ignored.
     *
     * @param namespace The namespace to register
     * @throws IllegalArgumentException if the namespace is invalid, reserved, or already registered with a handler
     */
    void registerNamespaceAsBroadcastOnly(@NonNull String namespace);

    /**
     * Send a message to all WebSockets subscribed to a particular namespace
     *
     * @param namespace The namespace to broadcast to
     * @param message The message to broadcast
     * @return the number of WebSocket connections that was broadcasted to
     * @throws IllegalArgumentException if the specified namespace doesn't match the message's namespace
     * @throws IllegalStateException if the namespace has not been registered
     */
    int broadcastToNamespace(@NonNull String namespace, @NonNull FtcWebSocketMessage message);

    /**
     * Determine if a particular WebSocket is subscribed to a given namespace
     *
     * @param namespace The namespace to check
     * @param webSocket The WebSocket to check
     * @return {@code true} if the WebSocket is subscribed to the namespace
     * @throws IllegalStateException if the namespace has not been registered
     */
    boolean webSocketIsSubscribedToNamespace(@NonNull String namespace, @NonNull FtcWebSocket webSocket);
}
