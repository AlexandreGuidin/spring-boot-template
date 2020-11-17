# spring-boot-template
Template app for spring boot applications

## Functionalities
Basic features of this template
- Spring rest configs
- Spring security basic auth
- Spring data with postgres and liquibase migrations
- Integration tests
- Healthcheck
- OpenAPI
- Json log
- AWS SQS - Spring JMS ** Some bugs
- Docker ready

## Hosts
- Local: http://localhost:8080/api

## Documentation
Open API docs
- Local: http://localhost:8080/api/swagger-ui.html

## Setup
- Download Intellij: https://www.jetbrains.com/idea/download/
- Enable plugins of Spring/SpringBoot if available
- Setup JDK (openjdk11-openj9)
- Enable "EditorConfig" in -> Preferences | Editor | Code Style
- Setup docker and docker-compose for local and test environment
- Enjoy!

## Common commands
```
Tests: ./gradlew build (need docker-compose up)
Build: ./gradlew build
Docker Build: ./cmds/build-image.sh
Run docker compose: docker-compose up
Rebuild docker compose: docker-compose rm -f && docker-compose up
Run docker compose only database: docker-compose up postgres
Check code style: eclint check "src/**" (Need to install 'npm install -g eclint')
```

