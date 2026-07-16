FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/ctjar.jar ctjar.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "ctjar.jar"]