FROM gradle:7.6-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle
WORKDIR /home/gradle
RUN gradle build --no-daemon 

FROM openjdk:17-slim

# EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/app/build/libs/*.jar /app/backend.jar

ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
