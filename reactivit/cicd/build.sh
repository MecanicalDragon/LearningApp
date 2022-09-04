#!/bin/bash

SCRIPT_PROFILE="local"

WAIT_USER_INPUT=true
BUILD_LOCALLY=true
RUN_CONTAINER=true
MVN_CLEAN=true

IMAGE_NAME="reactivit-bombarder-app"
DOCKER_IMAGE_TAG="latest"
DOCKERFILE="Dockerfile-bombarder-local"
CONTAINER_NAME="bombarder-app"

MVN_PROFILE="bombarder-container-build"
RUN_EXTRA_KEYS=""
PORT=34343

for arg in "$@"
do
case $arg in
  -w=*|--wait-user-input=*)
  WAIT_USER_INPUT="${$arg#*=}"
  ;;

  -t=*|--tag=*)
  DOCKER_IMAGE_TAG="${$arg#*=}"
  ;;

  -d=*|--dockerfile=*)
  DOCKERFILE="${$arg#*=}"
  ;;

  -r=*|--run=*)
  RUN_CONTAINER="${$arg#*=}"
  ;;

  -c=*|--clean=*)
  MVN_CLEAN="${$arg#*=}"
  ;;

  -b=*|--build=*)
  BUILD_LOCALLY="${$arg#*=}"
  ;;

  -m=*|--mvn-profile=*)
  MVN_PROFILE="${$arg#*=}"
  ;;

  -p=*|--port=*)
  PORT="${$arg#*=}"
  ;;

  -k=*|--run-keys=*)
  RUN_EXTRA_KEYS="${$arg#*=}"
  ;;

  *)
  echo "Unknown user input: ${arg}"
  if [ "$WAIT_USER_INPUT" = "true" ]
  then
    echo "Press any key to exit...
    "
    while [ true ] ; do
      read -t 3 -n 1
      if [ $? = 0 ] ; then
        exit ;
      fi
    done
  fi
  ;;
esac
done

echo "Stopping and removing old container..."

docker stop $CONTAINER_NAME
docker rm $CONTAINER_NAME

if [ $MVN_CLEAN = true ]
then
  echo "mvn clean..."
  cd ../
  mvn clean
  cd cicd || exit
fi

if [ $BUILD_LOCALLY = true ]
then
  echo "mvn clean package..."
  cd ../
  mvn clean package -DskipTests=true -P$MVN_PROFILE
  cd cicd || exit
fi

echo "Building docker image ${IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
echo "-----------------------------------------------------------------------------------------------------------"

docker build --build-arg MVN_PROFILE=$MVN_PROFILE -t "${IMAGE_NAME}":"${DOCKER_IMAGE_TAG}" -f $DOCKERFILE ../

if [ $RUN_CONTAINER = true ]
then
  echo "Running container..."
  docker run -d -p=$PORT:$PORT ${RUN_EXTRA_KEYS} --name $CONTAINER_NAME $IMAGE_NAME:$DOCKER_IMAGE_TAG
fi

echo "DONE"