#!/bin/bash

for IMG in $(docker images | grep adalrsjr1 | awk '{print $1}')
do
  echo ">>> $IMG pushing to Docker HUB"
  docker push $IMG
done
