FROM openjdk:17
VOLUME /tmp
EXPOSE 8888
COPY target/zoo-ticket-0.0.1-SNAPSHOT.jar ./zoo-ticket.jar
ENTRYPOINT ["java","-jar","/zoo-ticket.jar"]
