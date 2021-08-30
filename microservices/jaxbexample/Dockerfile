FROM openjdk:11.0.10-jre-slim AS deployment
ENV SERVER_PORT 8080
COPY target/jaxbexample-1.0.0.jar  /jaxbexample.jar
EXPOSE ${SERVER_PORT:-8080}
ENTRYPOINT ["java","-jar","/jaxbexample.jar"]