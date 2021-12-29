package com.example.awslocal.controller;

import java.util.List;
import java.util.Map;

import com.example.awslocal.dynamo.DynamoService;
import com.example.awslocal.dynamo.ExampleEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aws-dynamo")
public class AwsDynamoController {

    static final Logger LOGGER = LoggerFactory.getLogger(AwsDynamoController.class);

    @Autowired
    private DynamoService dynamoService;
    
    @PostMapping("/by-map")
    public ResponseEntity<Void> putItemByMap(@RequestBody(required = false) Map<String,String> values){
        
        LOGGER.info("Calling putItemByMap()");
        
        dynamoService.putItemByMap(values);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/by-entity")
    public ResponseEntity<Void> putItemByEntity(@RequestBody(required = false) ExampleEntity entity){
        
        LOGGER.info("Calling putItemByEntity()");
        
        dynamoService.putItemByEntity(entity);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/with-projection")
    public ResponseEntity<Map<String, Object>> loadByIdWithProjection(@PathVariable("id") String id , @RequestParam(required = false) String projectionExpretion ){
        
        LOGGER.info("Calling loadByIdWithProjection()");
        
        return ResponseEntity.ok().body(dynamoService.loadByIdWithProjection(id, projectionExpretion));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ExampleEntity> loadByIdWithMapper(@PathVariable("id") String id){
        
        LOGGER.info("Calling loadByIdWithMapper()");
        
        return ResponseEntity.ok().body(dynamoService.loadByIdWithMapper(id));
    }

    @GetMapping("/scan-entity")
    public ResponseEntity<List<ExampleEntity>> scanTableToEntity(
                        @RequestParam(required = false) Integer pageSize, 
                        @RequestParam(required = false) Integer pageNum, 
                        @RequestParam(required = false) String projectionExpression){
        
        LOGGER.info("Calling scanTableToEntity()");
        
        return ResponseEntity.ok().body(dynamoService.scanTableToPaginatedList(pageSize));
    }


    @GetMapping("/scan-map")
    public ResponseEntity<List<Map<String, Object>>> scanTableToListMap(
                        @RequestParam(required = false) Integer pageSize, 
                        @RequestParam(required = false) String projectionExpression){
        
        LOGGER.info("Calling scanTableToListMap()");
        
        return ResponseEntity.ok().body(dynamoService.scanTableToListMap(pageSize, projectionExpression));
    }





}
