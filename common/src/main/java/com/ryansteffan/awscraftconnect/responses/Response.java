package com.ryansteffan.awscraftconnect.responses;

import com.google.gson.annotations.SerializedName;

public abstract class Response {
    @SerializedName("success")
    protected boolean success;
    @SerializedName("message")
    protected String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
