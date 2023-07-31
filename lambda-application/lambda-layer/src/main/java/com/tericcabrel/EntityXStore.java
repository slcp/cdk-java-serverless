package com.tericcabrel;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class EntityXStore {
    private static EntityXStore instance;
    private final DynamoDbEnhancedClient DDB_ENHANCED_CLIENT = DynamoDbEnhancedClient.create();
    private final String TABLE_NAME = System.getenv("TABLE_NAME");
    // Generating a table schema is a relatively expensive operation
    public final DynamoDbTable<EntityX> table = this.DDB_ENHANCED_CLIENT.table(TABLE_NAME,
            TableSchema.fromImmutableClass(EntityX.class));

    public static EntityXStore getInstance() {
        if (instance == null) {
            instance = new EntityXStore();
        }
        return instance;
    }

    public void save(EntityX x) {
        this.table.putItem(x);
    }

    public EntityX load(String id) {
        return this.table.getItem(EntityX
                .builder()
                .id(id)
                .build());
    }
}
