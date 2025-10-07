#
# Build stage
#
FROM maven:3.9-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package


#
# Package stage
#
FROM eclipse-temurin:21-jre
COPY --from=build /target/my-collection-api-0.0.1-SNAPSHOT.jar my-collection-api-1.0.0.jar
# ENV PORT=7771
EXPOSE 7771
ENTRYPOINT ["java","-jar","my-collection-api-1.0.0.jar"]