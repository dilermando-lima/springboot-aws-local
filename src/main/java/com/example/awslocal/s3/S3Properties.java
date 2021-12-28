package com.example.awslocal.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.local.s3")
public class S3Properties {

    private String endpoint;
    private String region;

    private String nameBucket1;
    
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
    public String getNameBucket1() {
        return nameBucket1;
    }
    public void setNameBucket1(String nameBucket1) {
        this.nameBucket1 = nameBucket1;
    }
    

}

