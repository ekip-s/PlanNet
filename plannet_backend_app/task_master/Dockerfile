FROM openjdk:21-jdk-slim AS build

RUN set -eux; \
    sed -i 's|deb.debian.org|cdn-aws.deb.debian.org|g' /etc/apt/sources.list && \
    sleep 10; \
    for i in {1..10}; do \
        apt-get update && break || sleep 30; \
    done; \
    apt-get install -y --no-install-recommends maven

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl task_master -am -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/task_master/target/task_master-1.0.0.jar /app/task_master-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/task_master-1.0.0.jar"]
