package com.ryansteffan.awscraftconnect.requests;

import java.util.HashMap;

public class GETRequest extends Request {
    private final HashMap<String, String> parameters;

   public GETRequest(HashMap<String, String> parameters, HashMap<String, String> headers) {
        super(headers);
        this.parameters = parameters;
    }

    public GETRequest(HashMap<String, String> parameters) {
        this(parameters, null);
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

}
