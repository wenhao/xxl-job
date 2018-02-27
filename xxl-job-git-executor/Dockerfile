FROM openjdk:8-alpine

MAINTAINER "wenhao@126.com"

COPY target/xxl-job-git-executor-1.0.0-SNAPSHOT.jar /opt/xxl-job-git-executor-1.0.0.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/opt/xxl-job-git-executor-1.0.0.jar" ]
