FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/*.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/bookrating-0.0.1-SNAPSHOT.jar"]