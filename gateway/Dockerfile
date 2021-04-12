FROM openjdk:11.0.10-jdk
RUN gateway/gradlew build
ADD build/libs/gateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]