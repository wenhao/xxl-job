FROM openjdk:8

MAINTAINER "wenhao@126.com"

RUN apt-get update && apt-get install tzdata && rm -rf /var/lib/apt/lists/* && \
    ln -nsf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia\Shanghai" > /etc/timezone && \

COPY target/xxl-job-git-executor-1.0.0-SNAPSHOT.jar /opt/xxl-job-git-executor-1.0.0.jar

ENTRYPOINT [ "java", "-jar", "/opt/xxl-job-git-executor-1.0.0.jar" ]
