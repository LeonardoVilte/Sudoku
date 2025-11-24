# ================================
# STAGE 1: Build WAR
# ================================
FROM maven:3.9.6-eclipse-temurin-11 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# ================================
# STAGE 2: Run in Tomcat
# ================================
FROM tomcat:9.0-jdk11-temurin

# Remove default ROOT app
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy generated WAR
COPY --from=build /app/target/tallerwebi-base-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
