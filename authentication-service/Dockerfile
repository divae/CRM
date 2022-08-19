FROM openjdk:8-jdk-alpine
LABEL maintainer="eurekatech"
VOLUME /authentication-service
ADD build/libs/authentication-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]