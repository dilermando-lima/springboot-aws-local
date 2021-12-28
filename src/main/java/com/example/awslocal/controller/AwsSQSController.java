package com.example.awslocal.controller;

import java.util.Map;

import com.example.awslocal.sqs.SqsPublisheService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aws-sqs")
public class AwsSQSController {

    static final Logger LOGGER = LoggerFactory.getLogger(AwsSQSController.class);

    @Autowired
    private SqsPublisheService sqsPublisheService;
    
    @PostMapping("/publish-queue-1")
    public ResponseEntity<Void> publishQueue1(
                                    @RequestParam(required = false) Map<String, Object> headers,
                                    @RequestBody(required = false) String message ){
        
        LOGGER.info("Calling publishQueue1()");
        
        sqsPublisheService.publishQueue1(message, headers);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/publish-queue-2")
    public ResponseEntity<Void> publishQueue2(
                                    @RequestParam(required = false) Map<String, Object> headers,
                                    @RequestBody(required = false) String message ){
        
        LOGGER.info("Calling publishQueue2()");
        
        sqsPublisheService.publishQueue2(message, headers);
        return ResponseEntity.noContent().build();
    }
}
