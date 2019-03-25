FROM openjdk:8-jdk-alpine

VOLUME /tmp
EXPOSE 8000
ARG JAR_FILE=build/libs/template-1.0.0.jar
ADD ${JAR_FILE} application.jar

ENTRYPOINT ["java","-jar","/application.jar"]
