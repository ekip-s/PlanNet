FROM openjdk:21-jdk-slim AS build

# Исправление проблем с сетью и DNS
RUN echo "nameserver 8.8.8.8" > /etc/resolv.conf && \
    echo "nameserver 1.1.1.1" >> /etc/resolv.conf && \
    apt-get update || true

# Установка Maven с повторными попытками
RUN for i in 1 2 3 4 5; do \
        apt-get install -y wget tar && \
        wget -O /tmp/maven.tar.gz https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz && \
        tar xzf /tmp/maven.tar.gz -C /opt && \
        ln -s /opt/apache-maven-3.9.6/bin/mvn /usr/bin/mvn && \
        break || sleep 15; \
    done

COPY plannet_backend_app/ /app
WORKDIR /app

RUN mvn clean install -pl task_master -am -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/task_master/target/task_master-1.0.0.jar /app/task_master-1.0.0.jar

ENTRYPOINT ["java", "-jar", "/app/task_master-1.0.0.jar"]