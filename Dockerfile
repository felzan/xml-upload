FROM amazoncorretto:17
COPY build/libs/iApps-0.0.1-SNAPSHOT.jar app.jar
COPY build/resources/main/ ./
ENTRYPOINT ["java", "-jar", "/app.jar"]