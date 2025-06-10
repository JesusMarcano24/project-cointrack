FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY target/CoinTrack-0.0.1-SNAPSHOT.jar CoinTrack-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "CoinTrack-0.0.1-SNAPSHOT.jar"]