FROM gradle:8.5-jdk17 AS build
WORKDIR /home/gradle/src
COPY build.gradle settings.gradle ./
COPY src ./src

# Download the OpenTelemetry Java Agent during the build phase
RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar

RUN gradle bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080

# Copy both the application jar and the agent jar from the build stage
COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar app.jar
COPY --from=build /home/gradle/src/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

ENV TZ=UTC
# Added the -javaagent flag pointing to the copied jar file
ENTRYPOINT ["java", "-javaagent:/opentelemetry-javaagent.jar", "-Duser.timezone=UTC", "-jar", "/app.jar"]