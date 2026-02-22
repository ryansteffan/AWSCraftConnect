package com.ryansteffan.awscraftconnect.requests;

import java.util.HashMap;

public abstract class Request {
    private final HashMap<String, String> headers;

    public Request(HashMap<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        this.headers = headers;
    }

    public Request() {
        this.headers = new HashMap<>();
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }
}
