FROM openjdk:8
LABEL maintainer="Anjalikumari T"
ADD target/spring-boot-crud-0.0.1-SNAPSHOT.jar spring-boot-crud-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-crud-0.0.1-SNAPSHOT.jar"]