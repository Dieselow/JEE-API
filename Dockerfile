FROM maven:3-jdk-11 as BUILD
COPY . /usr/src/app
RUN mvn --batch-mode -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:11-jre-slim
ENV PORT 3001
EXPOSE 3001
COPY --from=BUILD /usr/src/app/target /opt/target
WORKDIR /opt/target

CMD ["/bin/bash", "-c", "find -type f -name '*.jar' | xargs java -jar -Djdk.tls.client.protocols=TLSv1.2"]