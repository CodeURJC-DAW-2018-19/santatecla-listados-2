FROM openjdk:13-ea-9-jdk-alpine3.9

COPY daw-0.0.1-SNAPSHOT.jar /daw-0.0.1-SNAPSHOT.jar
COPY initApp.sh /initApp.sh

EXPOSE 8443

CMD ["/initApp.sh"]

