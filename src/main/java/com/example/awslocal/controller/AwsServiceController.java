package com.example.awslocal.controller;

import java.util.Map;

import com.example.awslocal.sqs.SqsPublisheMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Controller
@RequestMapping("/aws-services")
public class AwsServiceController {

    static final Logger LOGGER = LoggerFactory.getLogger(AwsServiceController.class);

    @Autowired
    private SqsPublisheMessage sqsPublisheMessage;
    
    @Operation(summary = "Publish message into queue-1", tags = { "SQS" })
    @PostMapping("/sqs/publish-queue-1")
    public ResponseEntity<Void> publishQueue1(
                                    @Parameter(description = "Headers to be added on message") 
                                    @RequestParam(required = false) Map<String, Object> headers,
                                    @RequestBody String message ){
        
        LOGGER.info("Calling publishQueue1()");
        
        sqsPublisheMessage.publishQueue1(message, headers);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publish message into queue-1", tags = { "SQS" })
    @PostMapping("/sqs/publish-queue-2")
    public ResponseEntity<Void> publishQueue2(
                                    @Parameter(description = "Headers to be added on message") 
                                    @RequestParam(required = false) Map<String, Object> headers,
                                    @RequestBody String message ){
        
        LOGGER.info("Calling publishQueue2()");
        
        sqsPublisheMessage.publishQueue2(message, headers);
        return ResponseEntity.noContent().build();
    }
}
