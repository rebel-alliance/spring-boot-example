#!/bin/sh

scp spring-boot-example.service c10:/tmp

ssh c10 fleetctl stop spring-boot-example

ssh c10 fleetctl destroy spring-boot-example

ssh c10 fleetctl start /tmp/spring-boot-example.service
