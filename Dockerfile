FROM tomcat:latest

MAINTAINER "wenhao@126.com"

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY xxl-job-admin/target/xxl-job-admin-1.9.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war