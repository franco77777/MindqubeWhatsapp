FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/whatsapp-ending-test-0.0.1-SNAPSHOT.jar whatsapp-ending-test.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","whatsapp-ending-test.jar"]