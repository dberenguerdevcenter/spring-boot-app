FROM adoptopenjdk/openjdk11:alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

COPY ./target/spring-boot-jpa-h2-0.0.1-SNAPSHOT.jar app.jar

CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar



