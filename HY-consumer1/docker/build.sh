#!/bin/sh
pwd
cd yyicbc-consumer1/docker/
pwd
cp -f ../build/libs/icbc-ass-consumer*.jar ./icbc-ass-consumer.jar

v_date=$(date +%Y%m%d%H%M)
echo docker build -t dockerhub.icbc:5000/icbc/ass-consumersvc-dev:$v_date .
docker build -t dockerhub.icbc:5000/icbc/ass-consumersvc-dev:$v_date .

echo docker push dockerhub.icbc:5000/icbc/ass-consumersvc-dev:$v_date
docker push dockerhub.icbc:5000/icbc/ass-consumersvc-dev:$v_date

docker rmi dockerhub.icbc:5000/icbc/ass-consumersvc-dev:$v_date