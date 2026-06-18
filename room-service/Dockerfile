FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY common-events/pom.xml common-events/
COPY discovery-server/pom.xml discovery-server/
COPY config-server/pom.xml config-server/
COPY api-gateway/pom.xml api-gateway/
COPY guest-service/pom.xml guest-service/
COPY hotel-service/pom.xml hotel-service/
COPY room-service/pom.xml room-service/
COPY reservation-service/pom.xml reservation-service/
COPY payment-service/pom.xml payment-service/
COPY notification-service/pom.xml notification-service/
RUN mvn dependency:go-offline -pl room-service -am -B -q || true
COPY . .
RUN mvn package -pl room-service -am -DskipTests -B -q

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/room-service/target/room-service-*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
