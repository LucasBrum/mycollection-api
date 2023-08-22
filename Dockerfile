#
# Build stage
#
FROM maven:3.6.3-openjdk-17 AS build
COPY . .
RUN mvn clean package


#
# Package stage
#
FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/my-collection-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]