build:
	cd lambda-application && mvn clean install

deploy: build-application
	cd infra && cdklocal deploy	