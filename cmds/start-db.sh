#!/usr/bin/env bash

NAME=$(./gradlew -q projName)
IMAGE_NAME=${NAME}-mysql

echo "Stating database ${IMAGE_NAME}"

if [[  "$(docker ps -qa -f name=${IMAGE_NAME})" ]]; then
        docker start ${IMAGE_NAME}
else
 docker run \
    --name ${IMAGE_NAME} \
    -e MYSQL_DATABASE=templatedb \
    -e MYSQL_USER=mysql \
    -e MYSQL_PASSWORD=mysql \
    -e MYSQL_ROOT_PASSWORD=mysql \
    -p 3306:3306 \
    -d \
    mysql
fi
