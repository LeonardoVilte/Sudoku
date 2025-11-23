# Etapa 1: Compilar el proyecto usando Maven
FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Compilamos el WAR
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la app con Jetty embebido (del plugin que ya tenés en el pom)
FROM eclipse-temurin:11-jre
WORKDIR /app

# Copiamos todo el código nuevamente (necesario porque Jetty se ejecuta desde el pom)
COPY . .

# Exponemos el puerto donde Jetty arranca
EXPOSE 8080

# Comando para ejecutar Jetty desde Maven
CMD ["mvn", "jetty:run"]
