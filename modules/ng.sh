
DOC="Nailgun module to run the JVM as a daemon"

help() {
    echo -e "start | stop"
}

get_pid() {
    if [ -e /tmp/ng.pid ]; then
        p=$(cat /tmp/ng.pid)
        if [ $p ]; then
            echo $p
        else
            echo 0
        fi
    else
        echo 0
    fi
}

is_app_running() {
    PID=`get_pid`
    if [ $PID == 0 ]; then
        echo "no"
    else
        search=$(ps --pid $PID -o comm | grep -v COMMAND)
        if [ "$search" ]; then
            echo "yes"
        else
            echo "no"
        fi
    fi
}

running=`is_app_running`

ng_server_start() {
    java -cp ${JARK_CP}:${JARK_JAR} -server com.martiansoftware.nailgun.NGServer <&- & 
    pid=$!
    echo ${pid} > /tmp/ng.pid
}

start() {
    echo "Starting nailgun server on port 2113..."
    ng_server_start 2&> /dev/null
    sleep 2
    $JARK swank.swank start-repl
    exit 0
}

stop() {
    if [ $running == "no" ]; then
        echo "app server has already been stopped"
        exit 0
    fi
    echo "Stopping app server with pid `cat /tmp/ng.pid`"
    $NG ng-stop
    exit 0
}

alias() {
    if [ -z $3 ] || [ -z $4 ]; then
        echo "USAGE ng alias NICK MODULE/CLASS"
        exit 1
    fi
    $NG ng-alias $3 $4
    exit 0 
}
