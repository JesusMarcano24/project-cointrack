FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY CoinTrack-0.0.1-SNAPSHOT.jar CoinTrack-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "CoinTrack-0.0.1-SNAPSHOT.jar"]