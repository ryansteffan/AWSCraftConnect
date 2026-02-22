package com.ryansteffan.awscraftconnect.responses;

import com.google.gson.annotations.SerializedName;

public final class Instance {
    @SerializedName("instanceId")
    private String instanceId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("status")
    private String status;
    @SerializedName("ipAddress")
    private String ipAddress;
    @SerializedName("publicDNS")
    private String publicDomainName;

    public String getInstanceId() {
        return instanceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getPublicDomainName() {
        return publicDomainName;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "instanceId='" + instanceId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", publicDomainName='" + publicDomainName + '\'' +
                '}';
    }
}