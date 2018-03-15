FROM tomcat:latest

MAINTAINER "wenhao@126.com"

RUN apt-get update && apt-get install tzdata && rm -rf /var/lib/apt/lists/* && \
    ln -nsf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia\Shanghai" > /etc/timezone && \
    rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/xxl-job-admin-1.9.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war