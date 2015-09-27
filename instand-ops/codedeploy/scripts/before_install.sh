#!/bin/bash
set -e

JAVA_VER=$(java -version 2>&1 | sed 's/java version "\(.*\)\.\(.*\)\..*"/\1\2/; 1q')
if [[ "$JAVA_VER" -ge 18 ]]; then
    echo "good, Java is 8 or later"
else
    echo "java is too old, reinstalling Java 8"
    yum remove java-1.7.0-openjdk
    yum install java-1.8.0
fi
