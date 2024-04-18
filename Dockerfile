FROM eclipse-temurin:8

LABEL maintainer="muk214782@gmail.com"

WORKDIR /app

COPY target/rzyplat-0.0.1-SNAPSHOT.jar /app/rzyplat-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "rzyplat-0.0.1-SNAPSHOT.jar"]