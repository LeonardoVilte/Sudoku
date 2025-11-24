# ======================
# 1) BUILD STAGE
# ======================
FROM maven:3.8.5-eclipse-temurin-11 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -e -B dependency:resolve

COPY src ./src
RUN mvn -e -B package -DskipTests

# ======================
# 2) RUNTIME STAGE
# ======================
FROM eclipse-temurin:11-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
