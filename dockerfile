FROM openjdk:11
VOLUME /tmp
COPY target/desastres.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
