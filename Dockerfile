# Multi-stage build for Sigma Finance

# --- Builder stage ---
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy pom and sources
COPY pom.xml ./
COPY src ./src

# Build application (skip tests for faster CI/CD image build)
RUN mvn -B -DskipTests=true clean package

# --- Runtime stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create non-root user for security
RUN useradd -u 1001 -m appuser

# Copy fat jar from builder
COPY --from=builder /app/target/*.jar /app/app.jar

# Expose application port (can be mapped via compose)
EXPOSE 8080

# Run as non-root
USER appuser

# Optional JVM opts via JAVA_OPTS
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

