# build stage
FROM maven:3.8.5-openjdk-17-slim AS builder
WORKDIR /usr/test/src/weather-api
COPY . .
RUN mvn clean install -Dmaven.test.skip

#app package stage
FROM openjdk:17-alpine3.14
WORKDIR /app
COPY --from=builder /usr/test/src/weather-api/target/weather-api*.jar /app/weather-api.jar
CMD ["java", "-jar", "/app/weather-api.jar"]