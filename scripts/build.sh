#!/usr/bin/env sh
./gradlew clean build -Dorg.gradle.java.home=/usr/lib/jvm/jdk-15
docker image build -t ultimate-food-manager/main-service .