FROM gradle:6.8.3-jdk11 AS gradle
COPY . /home/gradle/src/
WORKDIR /home/gradle/src
RUN gradle --no-daemon build --stacktrace

FROM openjdk:11.0-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=gradle /home/gradle/src/build/libs/*.jar /app/weatherservice.jar
ENTRYPOINT ["java",  "-Djava.security.egd=file:/dev/./urandom","-jar","/app/weatherservice.jar"]