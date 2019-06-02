FROM openjdk:11-jdk-slim
VOLUME /tmp
ADD target/eureka.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8761
