#!/bin/bash

CONNECTOR_ROOT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

CONNECTOR_JAR="$CONNECTOR_ROOT/target/api-connector-1.0-SNAPSHOT.jar"

if [ ! -f "$CONNECTOR_JAR" ]; then
    echo Application is not built && exit 1
fi

java -jar "$CONNECTOR_JAR" "$@"