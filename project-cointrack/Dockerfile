# Usa la imagen oficial de Java 23 (temurin)
FROM eclipse-temurin:23-jdk-alpine

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR de la aplicación
COPY target/CoinTrack-0.0.1-SNAPSHOT.jar CoinTrack.jar

# Expone el puerto de la app
EXPOSE 8080

# Comando de ejecución
CMD ["java", "-jar", "CoinTrack.jar"]