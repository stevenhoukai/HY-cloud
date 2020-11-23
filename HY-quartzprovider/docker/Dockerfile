FROM dockerhub.icbc:5000/icbc/sles11sp4-ibmjdk-appstartup:17.1-8.0-3.12-17.4
MAINTAINER thomasfong
VOLUME /tmp
RUN rm -f /home/icbc/wlp/bin/appstartup.sh
ADD icbc-ass-quartz.jar /icbc-ass-quartz.jar
ADD appstartup.sh /appstartup.sh
RUN chmod +x /appstartup.sh
EXPOSE 8080
ENV JAVA_OPTS=""
CMD ["/appstartup.sh", "run"]