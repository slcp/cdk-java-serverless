package com.tericcabrel;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import software.amazon.awssdk.enhanced.dynamodb.Key;

public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private LambdaLogger logger;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        // TODO: Put in the constructor?
        this.logger = context.getLogger();

        this.doSomeDynamoStuff();

        
        String output = String.format("{ \"result\": %s }", "hello world");
        
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(responseHeaders);
        
        return response
        .withStatusCode(200)
        .withBody(output)
        .withIsBase64Encoded(false);
    }
    
    // Extract Dynamo stuff to it's own class

    // Extract Dynamo class to another project as a shared lib
    private void doSomeDynamoStuff() {
        String entityId = "some_id";
        // Put something to dynamo
        EntityXAsDynamoBeanORM x = EntityXAsDynamoBeanORM.builder().id(entityId).name("some name").build();
        EntityXAsDynamoBeanORM.CUSTOMER_TABLE.putItem(x);
        ;

        // Get something from dynamo
        Key searchBy = Key.builder().partitionValue(entityId).build();
        EntityXAsDynamoBeanORM x2 = EntityXAsDynamoBeanORM.CUSTOMER_TABLE.getItem(searchBy);
        this.logger.log("loaded entityX from store");
        this.logger.log(x2.getName());
    }
}