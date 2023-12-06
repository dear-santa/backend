FROM tomcat:9.0.64-jdk11-openjdk-slim-bullseye
COPY /target/ROOT.war /usr/local/tomcat/webapps/