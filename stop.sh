#!/bin/bash

# stop previous containers
docker stop java-challenge

# remove previous containers
docker rm java-challenge

# log the status of the services
last_command_status=$(echo $?)

if [ "$last_command_status" -eq 0 ]
then
    echo "Application java-challenge has stopped!"
else
    echo "An error occurred whiled stopping the application"
fi
