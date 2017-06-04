#!/bin/bash

#mvn clean package -DpushImage
docker run -d -p 5672:5672 --name rabbitmq rabbitmq ; docker run -d -p 27017:27017 --name mongo mongo ; mvn clean package
