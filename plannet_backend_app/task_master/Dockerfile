FROM openjdk:21-jdk-slim AS build

# Установка необходимых утилит
RUN apt-get update && apt-get install -y --no-install-recommends \
    wget \
    tar \
    gzip \
    && rm -rf /var/lib/apt/lists/*

# Установка Maven вручную
ARG MAVEN_VERSION=3.9.6
RUN wget -O /tmp/maven.tar.gz https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf /tmp/maven.tar.gz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn && \
    rm /tmp/maven.tar.gz

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl task_master -am -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/task_master/target/task_master-1.0.0.jar /app/task_master-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/task_master-1.0.0.jar"]
