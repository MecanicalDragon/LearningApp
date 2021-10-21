FROM maven:3.8.1-openjdk-11-slim AS build
ENV BUILDKIT_STEP_LOG_MAX_SIZE=1073741824
ENV SERVER_PORT 8080
COPY openapiexample/src /root
COPY openapiexample/pom.xml /root
RUN mkdir -p /microservices-api
COPY microservices-api /microservices-api
RUN mvn clean install -f microservices-api
RUN mkdir -p /parent
COPY parent/pom.xml /parent
WORKDIR /root
RUN mvn clean package

FROM openjdk:11.0.10-jre-slim AS deployment
COPY --from=build root/target/**.jar  /openapiexample.jar
EXPOSE ${SERVER_PORT:-8080}
ENTRYPOINT ["java","-jar","/openapiexample.jar"]