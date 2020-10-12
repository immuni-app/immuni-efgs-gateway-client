FROM adoptopenjdk:11-jre-hotspot

# Metadata
LABEL module.name="immuni-efgs-gateway-client" \
      module.version="0.0.1-SNAPSHOT"

COPY [ "target/immuni-efgs-gateway-client-0.0.1-SNAPSHOT.jar", "/app.jar" ]

RUN mkdir /config

RUN sh -c 'touch /app.jar'

VOLUME [ "/tmp" ]

ENV JAVA_OPTS="$JAVA_OPTS -Xms256M -Xmx1G"

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar --spring.config.location=file:/config/application.properties" ]
