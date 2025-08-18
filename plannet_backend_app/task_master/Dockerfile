FROM openjdk:21-jdk-slim AS build

# Исправление временных сетевых сбоев
RUN echo "nameserver 8.8.8.8" > /etc/resolv.conf && \
    echo "nameserver 8.8.4.4" >> /etc/resolv.conf && \
    set -eux; \
    for i in {1..5}; do \
        apt-get update && break; \
        sleep 5; \
    done && \
    apt-get install -y maven

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl task_master -am -DskipTests

FROM openjdk:21-jdk-slim

# Копируем только результат сборки (JAR-файл)
COPY --from=build /app/task_master/target/task_master-1.0.0.jar /app/task_master-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/task_master-1.0.0.jar"]
