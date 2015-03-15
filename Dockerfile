FROM registry.crawford.localnet/java:8-jre

RUN mkdir -p /opt/app
COPY target/spring-boot-example-1.0.0.jar /opt/app/application.jar

EXPOSE 8080
CMD ["java", "-jar", "/opt/app/application.jar"]
