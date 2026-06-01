FROM gradle:8.5-jdk17 AS build
WORKDIR /home/gradle/src
COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar app.jar

ENV TZ=UTC
ENTRYPOINT ["java", "-Duser.timezone=UTC", "-jar", "/app.jar"]