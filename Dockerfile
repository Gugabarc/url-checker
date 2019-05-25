FROM openjdk:8-jdk-alpine
COPY target/*.jar url-checker-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/url-checker-0.0.1.jar"]