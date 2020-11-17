FROM adoptopenjdk/openjdk11-openj9:alpine-jre

ADD build/libs/application.jar application.jar
ENTRYPOINT ["java","-jar","/application.jar"]
