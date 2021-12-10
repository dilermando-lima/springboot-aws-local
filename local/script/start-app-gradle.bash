#!/bin/bash

# bash ./local/script/start-app-gradle.bash ./local/env/local.env


# ========= requires gradle installed
# https://github.com/dilermando-lima/java-with-gradle#2-install-gradle

# ====================== read variables file

if [ -n "$1"  ]; then
        echo "reading $1 into enviromment variables"
        set -a
        source $1
        set +a
fi

# fuser -k 8080/tcp
gradle bootRun