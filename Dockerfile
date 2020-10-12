ARG VERSION=8u151

FROM openjdk:11-jdk as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew build --stacktrace

FROM openjdk:11-jre

COPY --from=BUILD /src/build/libs/wololo-0.0.1-SNAPSHOT.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]
