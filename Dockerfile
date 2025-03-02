FROM maven:3.9.5-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", \
            "-Dspring.datasource.url=${SPRING_DATASOURCE_URL}", \
            "-Dspring.datasource.username=${SPRING_DATASOURCE_USERNAME}", \
            "-Dspring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", \
            "-Dspring.flyway.baseline-on-migrate=true", \
            "-jar", "/app.jar"]