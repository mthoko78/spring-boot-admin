FROM docker.fnb.co.za/hc-integration/docker-images/ibm-semeru-runtimes:open-17.0.4.1_1-jdk-mvn-jammy as builder
WORKDIR /build
ADD ./ ./
RUN jar xf ./target/*.jar

FROM docker.fnb.co.za/hc-integration/docker-images/ibm-semeru-runtimes-with-certs:open-17.0.4.1_1-jre-jammy-with-certs

# Add user for application:

RUN addgroup spring && useradd -g spring spring
RUN mkdir /home/spring && chown -R spring /home/spring

# Add directories required by service and modes for directories:

RUN mkdir /logs && mkdir /logs/json && chmod 777 -R /logs

# Service files and configuration:

EXPOSE 8081/tcp

ENV JAVA_OPTS=""
ENV JVM_OPTS=""

ENV CONFIG_SERVER_URL=http://service-config-server:8081
ENV ADMIN_SERVER_URL=http://spring-boot-admin-service:8081/

COPY --from=builder /build/BOOT-INF/lib        /app/lib/
COPY --from=builder /build/META-INF            /app/META-INF/
COPY --from=builder /build/BOOT-INF/classes    /app/

USER spring

ENTRYPOINT ["sh", "-c", "echo 'JVM Memory Sizes:' && \
java \
${JVM_OPTS} \
-cp app:app/lib/* \
-verbose:sizes \
-noverify \
-Xtune:virtualized \
-Xquickstart \
-XX:+UseContainerSupport \
-XX:TieredStopAtLevel=1 \
-Dspring.backgroundpreinitializer.ignore=true \
-Dspring.jmx.enabled=false \
${JAVA_OPTS} \
za.co.frg.hr.intergration.admin.server.AdminServer \
${0} \
${@}"]
