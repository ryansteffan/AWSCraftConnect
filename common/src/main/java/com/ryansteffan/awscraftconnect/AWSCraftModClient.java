package com.ryansteffan.awscraftconnect;

import com.google.gson.Gson;
import com.ryansteffan.awscraftconnect.requests.GETRequest;
import com.ryansteffan.awscraftconnect.requests.POSTRequest;
import com.ryansteffan.awscraftconnect.responses.InstanceStatusResponse;
import com.ryansteffan.awscraftconnect.responses.ListInstancesResponse;
import com.ryansteffan.awscraftconnect.responses.Response;
import com.ryansteffan.awscraftconnect.responses.StartInstanceResponse;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class AWSCraftModClient implements AWSCraftClient {
    private Gson gson;
    private AWSCraftConfig config;

    public AWSCraftModClient(@NotNull AWSCraftConfig config, @NotNull Gson gson) {
        this.config = config;
        this.gson = gson;
    }

    public AWSCraftModClient(@NotNull AWSCraftConfig config) {
        this(config, new Gson());
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public AWSCraftConfig getConfig() {
        return config;
    }

    public void setConfig(AWSCraftConfig config) {
        this.config = config;
    }

    @Override
    public ListInstancesResponse listInstances() {
        String endpoint = config.getBaseURL() + "/list-instances";
        HashMap<String, String> headers = new HashMap<>();
        if (config.getApiKey() != null) {
            headers.put("Authorization", "Bearer " + config.getApiKey());
        }
        GETRequest request = new GETRequest(null, headers);
        return GET(endpoint, request, ListInstancesResponse.class);
    }

    @Override
    public InstanceStatusResponse getInstanceStatus(String instanceId) {
        String endpoint = config.getBaseURL() + "/status";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("instanceId", instanceId);
        HashMap<String, String> headers = new HashMap<>();
        if (config.getApiKey() != null) {
            headers.put("Authorization", "Bearer " + config.getApiKey());
        }
        GETRequest request = new GETRequest(parameters, headers);
        return GET(endpoint, request, InstanceStatusResponse.class);
    }

    @Override
    public StartInstanceResponse startInstance(String instanceId) {
        String endpoint = config.getBaseURL() + "/start";
        HashMap<String, String> headers = new HashMap<>();
        if (config.getApiKey() != null) {
            headers.put("Authorization", "Bearer " + config.getApiKey());
        }
        POSTRequest<String> request = new POSTRequest<>(instanceId, headers);
        return POST(endpoint, request, StartInstanceResponse.class);
    }

    @Override
    public <T extends Response> T GET(String endpoint, GETRequest request, Class<T> responseType) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                for (String headerName : request.getHeaders().keySet()) {
                    connection.addRequestProperty(headerName, request.getHeaders().get(headerName));
                }
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    return gson.fromJson(responseBuilder.toString(), responseType);
                }
                throw new IOException("Received non-200 response code: " + responseCode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return future.join();
    }

    @Override
    public <T extends Response, K> T POST(String endpoint, POSTRequest<K> request, Class<T> responseType) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
            try {
                URL url = new URL(endpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                for (String headerName : request.getHeaders().keySet()) {
                    connection.addRequestProperty(headerName, request.getHeaders().get(headerName));
                }
                String jsonBody = gson.toJson(request.getBody());
                connection.getOutputStream().write(jsonBody.getBytes());
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    return gson.fromJson(responseBuilder.toString(), responseType);
                }
                throw new IOException("Received non-200 response code: " + responseCode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return future.join();
    }
}
