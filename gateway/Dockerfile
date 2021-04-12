FROM openjdk:11.0.10-jdk
ADD build/libs/gateway-0.0.1-SNAPSHOT.jar app.jar
RUN ./gradlew clean
RUN ./gradlew build
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]