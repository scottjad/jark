
DOC="Swank utilities for SLIME"

help() {
    echo -e "start | stop"
}

start() {
    $JARK swank.swank start-repl
    exit 0
}

stop() {
    echo "Stopping swank daemon"
    exit 0
}
