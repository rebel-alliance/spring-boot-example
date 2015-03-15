#!/bin/sh

mvn -U clean package

boot2docker ssh -A `pwd`/docker_build.sh

. deploy.sh
