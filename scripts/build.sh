#!/usr/bin/env sh
./gradlew clean build
docker image build -t ultimate-food-manager/main-service .