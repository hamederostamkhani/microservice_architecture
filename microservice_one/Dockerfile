FROM openjdk:18-ea-11-jdk-alpine
RUN apk add --no-cache tzdata
ENV TZ=Asia/Tehran

WORKDIR /app
COPY target/*.jar java_app.jar
ENTRYPOINT ["java", "-jar", "java_app.jar"]
