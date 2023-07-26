package com.tericcabrel;

import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

// Immutable entity class
@Builder
@Getter
@DynamoDbImmutable(builder = EntityXAsDynamoBeanORM.EntityXAsDynamoBeanORMBuilder.class)
public class EntityXAsDynamoBeanORM {
    // onMethod_ options will applied as annotations to the created method
    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String id;
    private String name;

    private static final DynamoDbEnhancedClient DDB_ENHANCED_CLIENT = DynamoDbEnhancedClient.create();
    private static final String TABLE_NAME = System.getenv("TABLE_NAME");
    // This can be used to interact with the store for this entity - it does not
    // have to live within this class but for now why not
    // Generating a table schema is a relatively expensive operation
    public static final DynamoDbTable<EntityXAsDynamoBeanORM> CUSTOMER_TABLE = DDB_ENHANCED_CLIENT.table(TABLE_NAME,
            TableSchema.fromImmutableClass(EntityXAsDynamoBeanORM.class));

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
