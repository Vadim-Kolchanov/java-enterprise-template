FROM bellsoft/liberica-openjdk-alpine:17

ENV SERVICE_NAME="ms-notification-core"
ENV JAVA_OPTS ""

RUN addgroup -S java && adduser -S -G java java -h /app -s /bin/bash

COPY ./build/libs/${SERVICE_NAME}-*.jar /app/${SERVICE_NAME}.jar
WORKDIR /app

USER java

EXPOSE 8080 8081 8082

CMD java ${JAVA_OPTS} -jar /app/${SERVICE_NAME}.jar