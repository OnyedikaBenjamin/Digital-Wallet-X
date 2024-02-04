#FROM maven:4.0.0-jdk-17 As build
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the application files into the container
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Create the final image with Java 17
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build environment
COPY --from=build /app/target/e-wallet-0.0.1-SNAPSHOT.jar /app/e-wallet.jar


# Expose the application port
EXPOSE 8080

# Define the startup command
CMD ["java", "-jar", "e-wallet.jar"]
