FROM adoptopenjdk/openjdk11:alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

COPY ./target/spring-boot-jpa-h2-*.jar app.jar

CMD java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar