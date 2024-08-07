FROM azul/zulu-openjdk:17-latest

ARG PROFILE
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENV PROFILE=${PROFILE}

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${PROFILE}","/app.jar"]