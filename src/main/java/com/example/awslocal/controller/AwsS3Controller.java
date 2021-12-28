package com.example.awslocal.controller;

import java.io.IOException;
import java.util.List;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.awslocal.s3.S3Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/aws-s3")
public class AwsS3Controller {

    static final Logger LOGGER = LoggerFactory.getLogger(AwsS3Controller.class);

    @Autowired
    private S3Service s3Service;
    
    @GetMapping
    public ResponseEntity<List<S3ObjectSummary>> listObject(){
        
        LOGGER.info("Calling listObject()");
        
        return ResponseEntity.ok().body(s3Service.listObject() );
    }

    @GetMapping("/{keyFileS3}/download")
    public ResponseEntity<Resource> getFileByKeyFromBucket1(@PathVariable(name = "keyFileS3") String keyFileS3 ){
        
        LOGGER.info("Calling getFileByKeyFromBucket1()");
        
        return s3Service.getResourceByKey(keyFileS3);
    }

    @GetMapping("/{keyFileS3}")
    public ResponseEntity<String> getContentByKey(@PathVariable(name = "keyFileS3") String keyFileS3 ) throws IOException{
        
        LOGGER.info("Calling getContentByKey()");
        
        return  ResponseEntity.ok().body(s3Service.getContentByKey(keyFileS3));
    }

    @PostMapping
    public ResponseEntity<PutObjectResult> putContentFile(@RequestParam("file") MultipartFile file) throws IOException {

        LOGGER.info("Calling putContentFile()");
        
        return ResponseEntity.ok().body(s3Service.putContent(file));

    }


    @PostMapping("/{keyFileS3}")
    public ResponseEntity<PutObjectResult> putContentString(@PathVariable(name = "keyFileS3") String keyFileS3, @RequestBody(required = false) String content){

        LOGGER.info("Calling putContentString()");
        
        return ResponseEntity.ok().body(s3Service.putContent(keyFileS3, content));

    }


    @DeleteMapping("/{keyFileS3}")
    public ResponseEntity<Void> deleteObject(@PathVariable(name = "keyFileS3") String keyFileS3){

        LOGGER.info("Calling deleteObject()");

        s3Service.deleteObject(keyFileS3);
        
        return ResponseEntity.noContent().build();

    }

}
