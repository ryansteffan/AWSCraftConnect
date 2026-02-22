package com.ryansteffan.awscraftconnect.responses;

import com.google.gson.annotations.SerializedName;

public class ListInstancesResponse extends Response {
    @SerializedName("instances")
    private Instance[] instances;

    public Instance[] getInstances() {
        return instances;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListInstancesResponse{")
                .append("success='").append(success).append('\'')
                .append(", message='").append(message).append('\'')
                .append(", instances=[");
        if (instances != null) {
            for (Instance instance : instances) {
                sb.append(instance.toString()).append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}