FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl power_plan -am -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/power_plan/target/power_plan-1.0.0.jar /app/power_plan-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/power_plan-1.0.0.jar"]