build:
	cd lambda-application && mvn clean install

deploy-local: build
	cd infra && cdklocal deploy	

deploy: build
	cd infra && aws-vault exec sandbox --no-session -- cdk deploy

synth: build
	cd infra && aws-vault exec sandbox --no-session -- cdk synth

bootstrap:
	cd infra && aws-vault exec sandbox --no-session -- cdk bootstrap