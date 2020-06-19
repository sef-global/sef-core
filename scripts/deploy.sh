#!/bin/bash
set -e
ssh-keyscan -H $IP >>~/.ssh/known_hosts
# Replace environment variables in property files
cd backend/src/main/resources
envsubst < application.properties.example > application.properties

cd -
mvn clean install
# Copy generated war files to the server
scp backend/target/core.war $USER_NAME@$IP:$DEPLOY_PATH
scp frontend/target/dashboard.war $USER_NAME@$IP:$DEPLOY_PATH
