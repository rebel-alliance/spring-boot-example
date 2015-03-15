#!/bin/sh

BASEDIR=$(dirname $0)

REG=registry.crawford.localnet
APP=spring-boot-example
VER=1.0.0

TAG=$REG/$APP:$VER


docker build -t $TAG $BASEDIR
docker push $TAG


