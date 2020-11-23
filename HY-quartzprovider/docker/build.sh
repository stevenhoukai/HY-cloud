#!/bin/sh
pwd
cd yyicbc-quartzprovider/docker/
pwd
cp -f ../build/libs/icbc-ass-quartz*.jar ./icbc-ass-quartz.jar

v_date=$(date +%Y%m%d%H%M)
echo docker build -t dockerhub.icbc:5000/icbc/ass-quartzsvc-dev:$v_date .
docker build -t dockerhub.icbc:5000/icbc/ass-quartzsvc-dev:$v_date .

echo docker push dockerhub.icbc:5000/icbc/ass-quartzsvc-dev:$v_date
docker push dockerhub.icbc:5000/icbc/ass-quartzsvc-dev:$v_date

docker rmi dockerhub.icbc:5000/icbc/ass-quartzsvc-dev:$v_date