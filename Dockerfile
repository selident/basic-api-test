FROM ubuntu:18.04

RUN apt-get update && \
    apt-get install -y --no-install-recommends openjdk-11-jre-headless

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

COPY target/basic-api-test-stage-1.0-SNAPSHOT-fat-tests.jar /

CMD java -jar basic-api-test-stage-1.0-SNAPSHOT-fat-tests.jar