FROM adoptopenjdk:11-jre-hotspot

# switch to root user
USER root

# Metadata
LABEL module.name="immuni-efgs-gateway-client" \
      module.version="0.0.1-SNAPSHOT"

WORKDIR /
ADD . /immuni-gateway-client
WORKDIR /immuni-gateway-client


COPY [ "target/immuni-efgs-gateway-client-0.0.1-SNAPSHOT.jar", "/immuni-gateway-client/app.jar" ]

RUN mkdir -p /immuni-gateway-client/config
RUN mkdir -p /security/sslclient
RUN mkdir -p /security/truststore

#RUN sh -c 'touch /app.jar'

#VOLUME [ "/tmp" ]

ENV JAVA_OPTS="$JAVA_OPTS -Xms256M -Xmx1G"

EXPOSE 8080

RUN useradd \
        --no-log-init \
        --home /immuni-gateway-client \
        --shell /bin/bash \
        immuni \
    && chown --recursive immuni:root /immuni-gateway-client \   
    && chmod -R g+rwx /immuni-gateway-client
USER immuni

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /immuni-gateway-client/app.jar --spring.config.location=file:/immuni-gateway-client/config/application.properties" ]
