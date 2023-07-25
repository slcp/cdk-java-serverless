package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.constructs.Construct;

public class JavaCdkStack extends Stack {
    public JavaCdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public JavaCdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        Function functionOne = new Function(this, "FunctionOne", FunctionProps.builder()
                .runtime(Runtime.JAVA_17)
                .code(Code.fromAsset("../lambda-application/target/lambda-application-1.0-SNAPSHOT.jar"))
                .handler("com.tericcabrel.App")
                .memorySize(128)
                .timeout(Duration.seconds(30))
                .logRetention(RetentionDays.ONE_WEEK)
                .build());

        LambdaRestApi.Builder.create(this, "myapi")
                .handler(functionOne)
                .build();
    }
}
