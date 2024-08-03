FROM openjdk:17-jdk-alpine
MAINTAINER richardchankiyin
RUN mkdir -p src/main/resources
COPY src/main/resources/scandi.csv.zip src/main/resources
COPY target/tradedataanalysis-1.1-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
