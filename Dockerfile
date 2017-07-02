FROM ghashange/java:1.8.0
MAINTAINER Siyavash Habashi

ADD target/Barraquito.jar /home/Barraquito.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/Barraquito.jar"]