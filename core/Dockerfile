# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

# Copy gradle wrapper and configurations
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# Grant execution rights on the Gradle wrapper
RUN chmod +x gradlew

# Download dependencies (this layer will be cached unless build.gradle changes)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the application JAR
RUN ./gradlew bootJar --no-daemon

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Run as non-root user for security optimization
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /workspace/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
