package com.example.awslocal.dynamo;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoConfig {

    @Bean
    public AmazonDynamoDB amazonDynamoClient(@Autowired DynamoProperties dynamoProperties) {

        AmazonDynamoDBClientBuilder dynamoClientBuilder =  AmazonDynamoDBClientBuilder.standard();
        dynamoClientBuilder.withCredentials(new EnvironmentVariableCredentialsProvider());
        dynamoClientBuilder.setEndpointConfiguration(new EndpointConfiguration(dynamoProperties.getEndpoint(), dynamoProperties.getRegion()));
  
        return dynamoClientBuilder.build();   
    }
 

}
