build-application:
	cd lambda-application && mvn clean install

deploy-local: build-application
	cd infra && cdklocal deploy	

deploy: build-application
	cd infra && aws-vault exec sandbox --no-session -- cdk deploy

bootstrap:
	cd infra && aws-vault exec sandbox --no-session -- cdk bootstrap	