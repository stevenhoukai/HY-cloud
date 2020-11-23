#!/bin/sh
source /lifecycle/get_pod_ports.sh
hport=${containerport['8080']}
echo $hport >> /tmp/hport.txt
export hport
java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar -Dserver.port=8080 -DNODE_IP=$_D_HOST_IP -DNODE_PORT=$hport -Dspring.profiles.active=$_RUN_ENV /icbc-ass-provider.jar