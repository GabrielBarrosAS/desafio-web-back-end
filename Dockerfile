FROM openjdk:11
ARG JAR_FILE=target/desafio-web-back-end-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]