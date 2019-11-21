FROM openjdk:8
MAINTAINER Stanislav Tretyakov <gaffstranger@gmail.com>
LABEL source="java" name="WsSim" version="1.0"  description="WebServices Example application"
ARG JAR=wssim.jar
ARG DB=d:/dock
ADD target/${JAR} app/${JAR}
ADD ${DB}/wssim.lock.db app/database/wssim.lock.db
ADD ${DB}/wssim.mv.db app/database/wssim.mv.db
WORKDIR /app
EXPOSE 9009
ENTRYPOINT ["java", "-jar", "wssim.jar"]
VOLUME /app/database
