#!/bin/bash

echo "Starting the application..."

# stop & remove previous containers
source stop.sh

# regenerate source files after recent changes
./mvnw clean package

# rebuild the docker image by ignoring the cache
docker build  --no-cache -t "java-challenge" . 

# launch the application
docker run -dp 8080:8080 --name java-challenge java-challenge

# log the status of the services
last_command_status=$(echo $?)

if [ "$last_command_status" -eq 0 ]
then
    echo "Application java-challenge has started!"
else
    echo "An error occurred whiled starting up the application"
fi
