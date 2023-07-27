package com.myorg;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.LayerVersion;
import software.amazon.awscdk.services.lambda.LayerVersionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Tracing;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.constructs.Construct;

public class JavaCdkStack extends Stack {
        public JavaCdkStack(final Construct scope, final String id) {
                this(scope, id, null);
        }

        public JavaCdkStack(final Construct scope, final String id, final StackProps props) {
                super(scope, id, props);

                String CDK_DIR = System.getProperty("user.dir");
                String APPLICATION_DIR = "../lambda-application";

                Path layerPath = Paths.get(CDK_DIR, APPLICATION_DIR,
                                "/lambda-layer/target/out");
                LayerVersion layer = new LayerVersion(this, "layer", LayerVersionProps.builder()
                                .code(Code.fromAsset(layerPath.toAbsolutePath().toString()))
                                .compatibleRuntimes(Arrays.asList(Runtime.JAVA_17))
                                .build());

                Path functionOnePath = Paths.get(CDK_DIR, APPLICATION_DIR,
                                "/lambda-1/target/lambda-1-1.0-SNAPSHOT.jar");
                Function functionOne = new Function(this, "FunctionOne", FunctionProps.builder()
                                .runtime(Runtime.JAVA_17)
                                .code(Code.fromAsset(functionOnePath.toAbsolutePath().toString()))
                                .handler("com.tericcabrel.App")
                                .memorySize(256)
                                .timeout(Duration.seconds(30))
                                .layers(Arrays.asList(layer))
                                .logRetention(RetentionDays.ONE_WEEK)
                                .architecture(Architecture.ARM_64)
                                .tracing(Tracing.ACTIVE)
                                .build());

                LambdaRestApi.Builder.create(this, "myapi")
                                .handler(functionOne)
                                .build();

                Table table = Table.Builder.create(this, "Table")
                                .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
                                .billingMode(BillingMode.PAY_PER_REQUEST)
                                .removalPolicy(RemovalPolicy.DESTROY)
                                .build();

                functionOne.addEnvironment("TABLE_NAME", table.getTableName());
                table.grantReadWriteData(functionOne);
        }
}
