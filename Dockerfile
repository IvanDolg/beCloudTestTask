FROM maven:3.9.7-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests=true -Dfile.encoding=UTF-8

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/deCloudTestTask-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]