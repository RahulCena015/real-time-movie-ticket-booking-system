FROM openjdk:21-alpine
ARG JAR_FILE=target/notification-service-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} notificationservice.jar
ENTRYPOINT ["java","-jar","/notificationservice.jar"]