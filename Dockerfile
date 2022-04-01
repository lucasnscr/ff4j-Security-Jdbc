FROM openjdk:17-oracle
VOLUME /tmp
ARG JAR_FILE=target/ff4j-security-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]