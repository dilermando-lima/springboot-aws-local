package com.example.awslocal.sqs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.local.sqs")
public class SqsProperties {

    private String endpoint;
    private String region;
    private Boolean enableListenerQueue;
    private Boolean enableAcknowledgeMode;
    private String nameQueue1;
    private String nameQueue2;

    public String getEndpoint() {
        return endpoint;
    }
    public Boolean getEnableAcknowledgeMode() {
        return enableAcknowledgeMode;
    }
    public void setEnableAcknowledgeMode(Boolean enableAcknowledgeMode) {
        this.enableAcknowledgeMode = enableAcknowledgeMode;
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
    public String getNameQueue1() {
        return nameQueue1;
    }
    public void setNameQueue1(String nameQueue1) {
        this.nameQueue1 = nameQueue1;
    }
    public String getNameQueue2() {
        return nameQueue2;
    }
    public void setNameQueue2(String nameQueue2) {
        this.nameQueue2 = nameQueue2;
    }
    public Boolean getEnableListenerQueue() {
        return enableListenerQueue;
    }
    public void setEnableListenerQueue(Boolean enableListenerQueue) {
        this.enableListenerQueue = enableListenerQueue;
    }
    
}

