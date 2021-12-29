package com.example.awslocal.dynamo;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamoService {

    static final Logger LOGGER = LoggerFactory.getLogger(DynamoService.class);

    @Autowired DynamoProperties dynamoProperties;
    @Autowired AmazonDynamoDB amazonDynamoClient;

   
    public void putItemByMap(Map<String,String> values){

        LOGGER.debug("Calling putItemByMap() : values = {} ", values);

        Map<String,AttributeValue> mapValuesToBePut = 
                        values.entrySet()
                            .stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, entry -> new AttributeValue(entry.getValue())));

        amazonDynamoClient.putItem(dynamoProperties.getNameTable1(), mapValuesToBePut);
    }

    public void putItemByEntity(ExampleEntity entity){

        LOGGER.debug("Calling putItemByEntity() : entity = {} ", entity);

        new DynamoDBMapper(amazonDynamoClient).save(entity);
    }


    public Map<String, Object>  loadByIdWithProjection(String id, String projectionExpretion){

        LOGGER.debug("Calling loadByIdWithProjection() : id = {} , projectionExpretion = {} ", id, projectionExpretion);

        GetItemRequest request = new GetItemRequest();
        
        request
            .withTableName(dynamoProperties.getNameTable1())
            .withProjectionExpression(projectionExpretion)
            .withKey(Map.of("id", new AttributeValue(id)));

        return amazonDynamoClient
                    .getItem(request).getItem().entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, converAttributValueToObject()));
                    
    }


    public ExampleEntity loadByIdWithMapper(String id){

        LOGGER.debug("Calling loadByIdWithMapper() : id = {} ", id);

        return new DynamoDBMapper(amazonDynamoClient).load(ExampleEntity.class, id);
    }


    public List<ExampleEntity> scanTableToPaginatedList(Integer pageSize){

        LOGGER.debug("Calling scanTableToPaginatedList() : pageSize = {}", pageSize);


        return new DynamoDBMapper(amazonDynamoClient)
                        .scanPage(ExampleEntity.class, new DynamoDBScanExpression().withLimit(pageSize)).getResults();

    }


    public List<Map<String, Object>> scanTableToListMap(Integer pageSize, String projectionExpression){

        LOGGER.debug("Calling scanTableToListMap() : pageSize = {} , projectionExpression = {} ", pageSize , projectionExpression );

        ScanRequest scanRequest = new ScanRequest()
        .withTableName(dynamoProperties.getNameTable1())
        .withProjectionExpression(projectionExpression)
        .withLimit(pageSize);
            
        return amazonDynamoClient
                        .scan(scanRequest)
                        .getItems()
                        .stream()
                        .map( 
                            item -> item.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, converAttributValueToObject() )) 
                        )
                        .collect(Collectors.toList());
                                    


    }


    private Function<Map.Entry<String,AttributeValue> ,Object>  converAttributValueToObject(){

          return entry -> {        
                            if( entry.getValue().getS() != null ) return entry.getValue().getS();
                            if( entry.getValue().getSS() != null ) return entry.getValue().getSS();
                            if( entry.getValue().getB() != null ) return entry.getValue().getB();
                            if( entry.getValue().getN() != null ) return entry.getValue().getN();
                            if( entry.getValue().getNS() != null ) return entry.getValue().getNS();
                            else return null;
                        };
    }




    
    
    
}
