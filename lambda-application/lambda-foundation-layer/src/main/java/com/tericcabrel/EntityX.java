package com.tericcabrel;

import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

// Immutable entity class
@Builder
@Getter
@DynamoDbImmutable(builder = EntityX.EntityXBuilder.class)
public class EntityX {
    // onMethod_ options will applied as annotations to the created method
    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String id;
    private String name;

    // If using an immutable data class with a Builder all methods must be getters
    // otherwise an exception will be thrown
    // https://github.com/aws/aws-sdk-java-v2/tree/master/services-custom/dynamodb-enhanced#working-with-immutable-data-classes
    // // Retrieve a single customer record from the database
    // public static EntityX load(EntityX entity) {
    // return CUSTOMER_TABLE.getItem(entity);
    // }

    // // Store this customer record in the database
    // public void save() {
    // CUSTOMER_TABLE.putItem(this);
    // }
}
