package com.example.awslocal.s3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

    static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    @Autowired S3Properties s3Properties;
    @Autowired AmazonS3 amazonS3Client;


    public List<S3ObjectSummary> listObject(){
        LOGGER.debug("Calling listObject()" );

        LOGGER.debug("s3Properties.getNameBucket1 = {}" , s3Properties.getNameBucket1());

        return amazonS3Client
                .listObjectsV2(s3Properties.getNameBucket1())
                .getObjectSummaries();
    }


    public ResponseEntity<Resource> getResourceByKey(String keyFileS3){

        LOGGER.debug("Calling getResourceByKey() : keyFileS3 = {}" , keyFileS3);

        S3Object s3object =  amazonS3Client.getObject(s3Properties.getNameBucket1(), keyFileS3);

        LOGGER.debug("s3object = {}" , s3object);

        InputStreamResource stream = new InputStreamResource(s3object.getObjectContent());
        
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + s3object.getKey());
		return ResponseEntity.ok()
                .headers(headers)
				.contentType(MediaType.parseMediaType("application/octet-stream;charset=UTF-8"))
                .body(stream);
    }

    public String getContentByKey(String keyFileS3) throws IOException{

        LOGGER.debug("Calling getContentByKey() : keyFileS3 = {}" , keyFileS3);

        try (S3ObjectInputStream inputStream = amazonS3Client.getObject(s3Properties.getNameBucket1(), keyFileS3).getObjectContent()) {
            
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        
        }

    }

    public void deleteObject(String keyFileS3){

        LOGGER.debug("Calling deleteObject() : keyFileS3 = {}" , keyFileS3);
        amazonS3Client.deleteObject(s3Properties.getNameBucket1(), keyFileS3);

    }

    public PutObjectResult putContent(MultipartFile file) throws IOException  {

        LOGGER.debug("Calling putContent() : file.originalName = {}" , file == null ? "null" : file.getOriginalFilename());

        Objects.requireNonNull(file, "file is required");

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.addUserMetadata("Content-Type", file.getContentType());
        objectMetadata.addUserMetadata("Content-Length", String.valueOf(file.getSize()));

        LOGGER.debug("file.contentType = {}" , file.getContentType());

        return  amazonS3Client.putObject(s3Properties.getNameBucket1(), file.getOriginalFilename(), file.getInputStream(), objectMetadata);
    }


    public PutObjectResult putContent(String keyFileS3, String content)  {

        LOGGER.debug("Calling putContent() : keyFileS3 = {} , content = {}" , keyFileS3, content);


        return  amazonS3Client.putObject(s3Properties.getNameBucket1(), keyFileS3, content);
    }


    
    
}
