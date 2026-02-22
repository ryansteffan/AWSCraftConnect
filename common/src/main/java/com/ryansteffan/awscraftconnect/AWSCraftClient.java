package com.ryansteffan.awscraftconnect;

import com.ryansteffan.awscraftconnect.requests.GETRequest;
import com.ryansteffan.awscraftconnect.requests.POSTRequest;
import com.ryansteffan.awscraftconnect.responses.InstanceStatusResponse;
import com.ryansteffan.awscraftconnect.responses.ListInstancesResponse;
import com.ryansteffan.awscraftconnect.responses.Response;
import com.ryansteffan.awscraftconnect.responses.StartInstanceResponse;

public interface AWSCraftClient {
    ListInstancesResponse listInstances();
    InstanceStatusResponse getInstanceStatus(String instanceId);
    StartInstanceResponse startInstance(String instanceId);
    <T extends Response> T GET(String endpoint, GETRequest request, Class<T> responseType);
    <T extends Response, K> T POST(String endpoint, POSTRequest<K> request, Class<T> responseType);
}


