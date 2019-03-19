#!/usr/bin/env bash

NAME=template

if [[ ! "$(docker ps -a -f name=${NAME})" ]]; then
        docker run \
        --name postgres-${NAME} \
        -e POSTGRES_USER=postgres \
        -e POSTGRES_PASSWORD=postgres \
        -e POSTGRES_DB=${NAME} \
        -p 5432:5432 \
        postgres

else
    docker start -a postgres-${NAME}
fi