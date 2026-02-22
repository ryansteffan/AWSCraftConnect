package com.ryansteffan.awscraftconnect;

public class AWSCraftConfig {
    private String baseURL;
    private String apiKey;

    public AWSCraftConfig(String baseURL, String apiKey) {
        this.baseURL = baseURL;
        this.apiKey = apiKey;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
