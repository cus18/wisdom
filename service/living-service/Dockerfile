FROM java:8-jre
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD ./target/living-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/living-service.jar"]

EXPOSE 7000