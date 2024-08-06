FROM openjdk:17-jdk-slim
ADD /build/libs/*.jar task-server.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/task-server.jar"]