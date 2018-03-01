FROM openjdk:8

MAINTAINER "wenhao@126.com"

RUN apt-get update && apt-get install git  && rm -rf /var/lib/apt/lists/*

COPY target/xxl-job-git-executor-1.0.0-SNAPSHOT.jar /opt/xxl-job-git-executor-1.0.0.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/opt/xxl-job-git-executor-1.0.0.jar" ]
