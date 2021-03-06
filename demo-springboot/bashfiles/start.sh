#!/bin/sh
source ./define.sh

if [ ! $SPRING_PROFILES_ACTIVE ]; then
  if [ ! $RUN_CMD ]; then
    java -jar $service_name.jar
  else
    $RUN_CMD
  fi
else
  if [ ! $RUN_CMD ]; then
    java -jar $service_name.jar --spring.profiles.active=$SPRING_PROFILES_ACTIVE
  else
    $RUN_CMD
  fi
fi
