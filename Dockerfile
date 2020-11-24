FROM openjdk:11
RUN apt-get update && apt-get install -y netcat;
COPY scripts/wait-for.sh /opt/wait-for.sh
ADD build/libs/main-service-0.0.1.jar mainService.jar
EXPOSE 8080:8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","mainService.jar"]