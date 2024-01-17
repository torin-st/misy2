FROM eclipse-temurin:21-jre
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY commands.sh commands.sh
RUN ["chmod", "+x", "/commands.sh"]
ENTRYPOINT ["/commands.sh"]