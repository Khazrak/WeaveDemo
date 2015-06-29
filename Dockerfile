FROM java:openjdk-8u45-jre
MAINTAINER Karl Andersson

RUN mkdir -p /opt/apps && \
	groupadd -r demo -g 1000 && useradd -u 1000 -r -g demo -m -d /opt/apps -s /sbin/nologin -c "Demo user" demo

COPY weave-demo.jar /opt/apps/weave-demo.jar

WORKDIR /opt/apps
USER demo

CMD java -jar weave-demo.jar
