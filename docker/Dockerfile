FROM sapmachine:21-jdk-headless-ubuntu-22.04
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]