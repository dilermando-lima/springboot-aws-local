package com.example.awslocal.dynamo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.local.dynamo")
public class DynamoProperties {

    private String endpoint;
    private String region;

    private String nameTable1;
    
    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getNameTable1() {
        return nameTable1;
    }
    public void setNameTable1(String nameTable1) {
        this.nameTable1 = nameTable1;
    }

}

