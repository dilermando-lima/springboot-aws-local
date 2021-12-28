package com.example.awslocal.s3;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@Configuration
public class S3Config {

    @Bean
    public AmazonS3 amazonS3(@Autowired S3Properties s3Properties) {

        AmazonS3ClientBuilder s3ClientBuilder =  AmazonS3ClientBuilder.standard();
        s3ClientBuilder.withCredentials(new EnvironmentVariableCredentialsProvider());
        s3ClientBuilder.setEndpointConfiguration(new EndpointConfiguration(s3Properties.getEndpoint(), s3Properties.getRegion()));
        s3ClientBuilder.enablePathStyleAccess();
  
        return s3ClientBuilder.build();   
    }
 

}
