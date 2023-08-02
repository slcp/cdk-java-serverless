build:
	cd lambda-application && mvn clean install

deploy: build
	cd infra && aws-vault exec sandbox --no-session -- cdk deploy

synth: build
	cd infra && aws-vault exec sandbox --no-session -- cdk synth

bootstrap:
	cd infra && aws-vault exec sandbox --no-session -- cdk bootstrap

add-child-module:
	cd lambda-application && mvn archetype:generate -DgroupId=com.tericcabrel -DartifactId=$(artifaceId) -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false