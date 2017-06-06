#!/bin/bash

echo "building Fluentd"
echo ""

docker build --no-cache --rm -t adalrsjr1/fluentd -f deployment/fluentd-vanilla/Dockerfile ./deployment/fluentd-vanilla

echo "building Fluentd-ELK"
echo ""
docker build --no-cache --rm -t adalrsjr1/fluentd-efk -f deployment/fluentd-efk/Dockerfile ./deployment/fluentd-efk


for IMG in $(docker images | grep adalrsjr1 | awk '{print $1}')
do
  echo ">>> $IMG pushing to Docker HUB"
  echo ""
  docker push $IMG
done


