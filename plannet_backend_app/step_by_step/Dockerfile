FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl step_by_step -am -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/step_by_step/target/step_by_step-1.0.0.jar /app/step_by_step-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/step_by_step-1.0.0.jar"]