FROM openjdk:8-jdk-alpine
EXPOSE 8007
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]