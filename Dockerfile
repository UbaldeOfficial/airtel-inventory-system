FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Install Maven inside container
RUN apt-get update && apt-get install -y maven

# Build project using Maven (NOT mvnw)
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["sh", "-c", "java -jar target/*.jar --server.port=${PORT} --spring.profiles.active=prod"]