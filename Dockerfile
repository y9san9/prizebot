FROM openjdk:8-jre-alpine3.9

CMD ["./gradlew", "fatJar"]

COPY build/libs/app.jar /app.jar

CMD ["java", "-jar", "/app.jar"]
