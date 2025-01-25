# pexel-service/Dockerfile
FROM maven:3.9.9-amazoncorretto-21-al2023 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime stage
FROM amazoncorretto:21.0.3-al2023
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8084
CMD ["java", "-jar", "app.jar"]