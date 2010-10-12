#!/bin/bash

DOC="Classpath manager"

help() {
    echo -e "run MAIN-CLASS"
}

run() {
    local mainclass="$1"
    touch classpath
    $NG ng-alias cm com.stuartsierra.ClasspathManager
    $NG cm $mainclass
    exit 0
}
