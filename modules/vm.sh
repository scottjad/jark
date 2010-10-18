
DOC="Module to manage the JVM server"

help() {
    echo -e "start | stop"
}


alias() {
    if [ -z $3 ] || [ -z $4 ]; then
        echo "USAGE ng alias NICK MODULE/CLASS"
        exit 1
    fi
    $NG ng-alias $3 $4
    exit 0 
}

ng_server_start() {
    java -cp ${JARK_CP}:${JARK_JAR} -server com.martiansoftware.nailgun.NGServer <&- & 
    pid=$!
    echo ${pid} > /tmp/ng.pid
}

start() {
    ng_server_start 2&> /dev/null
    echo "Started JVM server on port 2113..."
    sleep 2
    echo "Loading modules ..."
    $JARK &> /dev/null
    exit 0
}

stop() {
    if [ $running == "no" ]; then
        echo "JVM server has already been stopped"
        exit 0
    fi
    echo "Stopping JVM server with pid `cat /tmp/ng.pid`"
    $NG ng-stop
    exit 0
}
