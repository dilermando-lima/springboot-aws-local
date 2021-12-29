package com.example.awslocal.dynamo;

import java.time.LocalDateTime;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;


@DynamoDBTable(tableName = "table-1-dynamo")
public class ExampleEntity {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBAttribute(attributeName = "dateUpdated")
    @DynamoDBTypeConverted( converter = LocalDateTimeConverter.class )
    private LocalDateTime dateLastUpdated;


    @Override
    public String toString() {
        return "ExampleEntity [" + (dateLastUpdated != null ? "dateLastUpdated=" + dateLastUpdated + ", " : "")
                + (description != null ? "description=" + description + ", " : "")
                + (id != null ? "id=" + id + ", " : "") + (name != null ? "name=" + name : "") + "]";
    }

    public static class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

        @Override
        public String convert( final LocalDateTime time ) {
            return time.toString();
        }

        @Override
        public LocalDateTime unconvert( final String stringValue ) {
            return LocalDateTime.parse(stringValue);
        }
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(LocalDateTime dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    
}
