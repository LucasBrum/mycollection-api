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
COPY --from=build /target/my-collection-api-0.0.1-SNAPSHOT.jar my-collection-api-1.0.0.jar
# ENV PORT=7771
EXPOSE 7771
ENTRYPOINT ["java","-jar","demo.jar"]