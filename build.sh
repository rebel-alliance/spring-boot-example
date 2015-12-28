#!/bin/bash

mvn package

REG=registry.crawford.localnet
APP=spring-boot-example
VER=1.0.0

TAG=$REG/$APP:$VER

docker build -t $TAG .
docker push $TAG

docker stop spring-boot-example
docker rm spring-boot-example

docker run -d --name spring-boot-example -p 8080:8080 $TAG

# docker logs -f spring-boot-example
