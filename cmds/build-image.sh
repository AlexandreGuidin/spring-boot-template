#!/usr/bin/env bash

NAME=$(./gradlew -q versionedName)
docker build -t ${NAME} .