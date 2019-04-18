package com.example.shivangi.messaging_app;

public class ServerEvent {
    private String message;

    ServerEvent(String message) {
        this.message = message;
    }

    String getMessage() { return message; }
}
