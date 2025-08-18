FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl task_master -am -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/task_master/target/task_master-1.0.0.jar /app/task_master-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/task_master-1.0.0.jar"]
