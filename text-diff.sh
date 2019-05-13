#!/bin/sh

################################################
# Command line interface to start/stop diff app
################################################

# Usage
function _usage {
  echo "usage: $0 [start|stop] <docker|standalone>"
  exit -1
}

# Build all services
function _build_services {
    for dir in $(find . -type d -maxdepth 1 | grep -v "^[.]$" -v | grep -v "gradle"); do
      ${dir}/gradlew -b ${dir}/build.gradle clean build
    done
}

# Build all Docker containers
function _build_containers {
    for dir in $(find . -type d -maxdepth 1 | grep -v "^[.]$" -v | grep -v "gradle"); do
      CONTAINER_NAME=$(echo $dir | tr -d "[./]")
      docker build -t "${CONTAINER_NAME}:latest" ${dir}/
    done
}

# Start all services with docker
function _docker_start {
   # Check if docker is installed
   if [ ! -x "$(command -v docker)" ]; then
      echo "Docker is not installed. Please install it before running the app."
      exit -1
   fi
   # Build service
   _build_services
   # Build docker containersf
   _build_containers
   # Run containers
   for dir in $(find . -type d -maxdepth 1 | grep -v "^[.]$" -v | grep -v "gradle"); do
      SERVICE_NAME=$(echo $dir | tr -d "[./]")
      echo "Starting container for ${SERVICE_NAME}"
      if [ "$SERVICE_NAME" == "registry" ]; then
            docker container run -d -p 8080:8080 "${SERVICE_NAME}:latest"
      elif [ "$SERVICE_NAME" == "gateway" ]; then
            docker container run -d -p 8761:8761 "${SERVICE_NAME}:latest"
      else 
            docker container run -d "${SERVICE_NAME}:latest"
      fi
    done
}

# Start all services in standalone mode
function _standalone_start {
   # Build service
   for dir in $(find . -type d -maxdepth 1 | grep -v "^[.]$" -v | grep -v "gradle"); do
      SERVICE_NAME=$(echo $dir | tr -d "[./]")
      echo "Starting ${SERVICE_NAME}"
      nohup ${dir}/gradlew -b ${dir}/build.gradle bootRun >/dev/null 2>&1 &
      sleep 5
   done
}

# Stop all services with docker
function _docker_stop {
   docker kill $(docker ps -q)
   echo "All containers have been stopped"
}

# Stop all services in standalone mode
function _standalone_stop {
   for pid in $(pgrep -f bootRun); do
      echo "Sending stop signal to pid ${pid}"
      kill -9 ${pid}
      sleep 5
   done
   echo "All services have been stopped"
}

############################
# Script starts here
############################
if [ $# -eq 0 ]; then
   _usage
fi

if [ "$1" == "start" ]; then
   if [ "$2" == "docker" ]; then
      _docker_start
   else
      _standalone_start
   fi
elif [ "$1" == "stop" ]; then
   if [ "$2" == "docker" ]; then
      _docker_stop
   else
      _standalone_stop
   fi
else 
   _usage   
fi
