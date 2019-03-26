FROM openjdk:8-jdk-alpine

ADD build/libs/template-1.0.0.jar application.jar

ENTRYPOINT ["java","-jar","/application.jar"]
