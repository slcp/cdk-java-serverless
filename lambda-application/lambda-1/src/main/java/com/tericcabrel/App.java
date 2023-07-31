package com.tericcabrel;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

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

    private void doSomeDynamoStuff() {
        String entityId = "some_id";
        var store = EntityXStore.getInstance();

        // Put something to dynamo
        store.save(EntityX
                .builder()
                .id(entityId)
                .name("some name")
                .build());

        // Get something from dynamo
        var result = store.load(entityId);
        this.logger.log("loaded entityX from store");
        this.logger.log(result.getName());
    }
}