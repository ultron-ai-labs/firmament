FROM openjdk:8u332-jre-slim-buster
ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD admin/admin-web/target/*.jar app.jar
EXPOSE 7777
ENTRYPOINT ["sh","-c","java -jar app.jar"]