FROM eclipse-temurin:21 as builder
WORKDIR /app

COPY . /app
COPY pom.xml /app
COPY .env /app/src/main/resources/

#Windows only
RUN sed -i 's/\r$//' mvnw

RUN ./mvnw clean install -DskipTests


FROM eclipse-temurin:17-jdk-alpine as prod
COPY --from=builder /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]