FROM quancheng/runtime-image:1.1

ADD app/target/achilles.app-1.0.0-SNAPSHOT.jar /root/app.jar
ADD app/bin /root/
RUN chmod +x /root/*.sh;mkdir /root/logs
ENV JAVA_OPTS ""

WORKDIR /root
CMD ["./start.sh"]

