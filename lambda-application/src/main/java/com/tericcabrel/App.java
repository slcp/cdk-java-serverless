package com.tericcabrel;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        // logger.info(input.getBody());

        // Gson gson = new Gson();
        // RequestInput bodyInput = gson.fromJson(input.getBody(), RequestInput.class);

        // double result = calculateBodyMassIndex(bodyInput.getHeight(),
        // bodyInput.getWeight());
        System.out.println(input);
        String output = String.format("{ \"result\": %s }", "hello world");

        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(responseHeaders);

        return response
                .withStatusCode(200)
                .withBody(output)
                .withIsBase64Encoded(false);
    }
}