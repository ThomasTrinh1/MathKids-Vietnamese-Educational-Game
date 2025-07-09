#!/bin/sh 
DIRNAME="$(dirname "$0")" 
[ -z "$DIRNAME" ] && DIRNAME=. 
APP_BASE_NAME="$(basename "$0")" 
APP_HOME="$DIRNAME" 
exec java -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@" 
