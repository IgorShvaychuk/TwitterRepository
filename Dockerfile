# Stage 1: Build the application
FROM gradle:7.3.3-jdk11 AS build
WORKDIR /home/gradle/project
COPY . .
RUN gradle build --no-daemon

# Stage 2: Run the application
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/twitter-clone-0.0.1-SNAPSHOT.jar twitter-clone.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "twitter-clone.jar"]