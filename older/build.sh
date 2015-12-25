#!/bin/sh

mvn -U clean package

docker-machine ssh default `pwd`/docker_build.sh

# . deploy.sh
