package com.ryansteffan.awscraftconnect.requests;

import java.util.HashMap;

public class POSTRequest<T> extends Request {
    private final T body;

    public POSTRequest(T body, HashMap<String, String> headers) {
        super(headers);
        this.body = body;
    }

    public POSTRequest(T body) {
        this(body, null);
    }

    public T getBody() {
        return body;
    }
}
