# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/mini-decoder-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 3045
ENTRYPOINT ["java", "-jar", "app.jar"]
