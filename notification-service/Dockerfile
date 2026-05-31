# Multi-stage build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy parent POM and all submodule POMs
COPY pom.xml .
COPY shared-events/pom.xml shared-events/
COPY restaurant-service/pom.xml restaurant-service/
COPY reward-service/pom.xml reward-service/
COPY notification-service/pom.xml notification-service/

# Cache dependencies
RUN mvn dependency:go-offline -B

# Copy source code of required modules
COPY shared-events/src shared-events/src
COPY notification-service/src notification-service/src

# Compile and package notification-service and its dependencies
RUN mvn package -DskipTests -pl shared-events,notification-service -am

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/notification-service/target/notification-service-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
