# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download dependencies (if any) specified in the pom.xml
RUN mvn dependency:go-offline -B

# Copy the rest of the application source code
COPY src ./src

# Build the application and create JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create a lightweight image to run the application
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/authentication-service-0.0.1-SNAPSHOT.jar /app/authentication-service-0.0.1-SNAPSHOT.jar

# Expose the port on which the authentication service will run
EXPOSE 8000

# Run the authentication service with reduced JVM memory usage
CMD ["java", "-Xmx256m", "-jar", "authentication-service-0.0.1-SNAPSHOT.jar"]