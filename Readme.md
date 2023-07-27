# AWS CDK Java Example

The purpose of this repository is learn and surface that learning when getting started with Java in AWS using CDK and serverless infrastructure like Lambda & DynamoDB.

## Requirements

  - Latest CDK - [Install](https://docs.aws.amazon.com/cdk/v2/guide/getting_started.html#getting_started_install)
  - Java 17 - I used `brew` to [install](https://formulae.brew.sh/formula/openjdk@17) and use [`jenv`](https://www.jenv.be/) to manage multiple versions
  - An IDE that support Java development, I am using VSCode but the one you are most comfortable with is probably best
  - (optional) Docker & Docker Compose for deployments to Localstack or your own Localstack instance running
  - (optional) `cdklocal` - [Install](https://github.com/localstack/aws-cdk-local) for deploying to Localstack
  - (optional) `aws-vault` - [Install](https://github.com/99designs/aws-vault). The commands in the `Makefile` used `aws-vault` with a profile of `sandbox` to execute commands against an AWS account. If you are not using `aws-vault` find and remove all instances of `aws-vault exec sandbox --no-session -- ` in the `Makefile` and all other instructions here should work as expected.

## Goals

  - Define a Java Lambda handler
  - Define a Java Lambda in CDK
  - Connect an API Gateway to the Java Lambda
  - Deploy and interact with other services from the Lambda, e.g. DynamoDB
  - Explore approached to shared libs with Lambda = Layers?

## Deployment

In the early stages of development Localstack was used before access to an AWS account was available, for local deployments this is still supported but deployment to an actual AWS account is now the preferred method of proving out the application.

### Localstack

  - Run `make deploy-local`

### AWS

  - Run `make deploy`