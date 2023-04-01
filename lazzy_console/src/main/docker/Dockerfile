FROM openjdk:8-slim
LABEL MAINTAINER="kimchheng101@gmail.com"
ARG PROFILE=deploy
ENV PROFILE=${PROFILE}
ENV TZ=Asia/Phnom_Penh
COPY @project.build.finalName@.jar @project.build.finalName@.jar
RUN apt-get update
RUN apt-get install vim -y && apt-get install -y wget
RUN apt-get install curl -y && apt-get install dnsutils -y
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
LABEL release-date="@timestamp@"
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=$PROFILE","-Djava.security.egd=file:/dev/./urandom","/@project.build.finalName@.jar"]