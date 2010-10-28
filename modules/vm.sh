
DOC="Module to manage the JVM server"

commands() {
    echo -e "start stop threads stat uptime"
}

ng_server_start() {
    java -cp ${JARK_CP}:${JARK_JAR} -server com.martiansoftware.nailgun.NGServer <&- & 
    pid=$!
    echo ${pid} > /tmp/ng.pid
}

start() {
    ng_server_start 2&> /dev/null
    sleep 2
    echo "Loading modules ..."
    $JARK &> /dev/null
    if [ -e $CLJR_CP/jark-deps.txt ]; then
        echo "Adding dependencies to classpath ..."
        for dep in `cat $CLJR_CP/jark-deps.txt`; do
           $JARK cp add ${CLJR_ROOT}/$dep &> /dev/null
        done;
    fi
    if [ -e `pwd`/project.clj ] && [ -d `pwd`/src ] && [ -d `pwd`/lib ]; then
        $JARK cp add `pwd`/src
        $JARK cp add `pwd`/lib
    fi
        
    if [ -e $HOME/.jarkrc ]; then
        source $HOME/.jarkrc
    fi
    echo "Started JVM server on port 2113"
    exit 0
}

stop() {
    if [ $running == "no" ]; then
        echo "JVM server has already been stopped"
        exit 0
    fi
    echo "Stopping JVM server with pid `cat /tmp/ng.pid`"
    $JARK_CLIENT ng-stop
    exit 0
}

threads() {
    $JARK _stat threads
}

stat() {
    $JARK _stat stats
}

uptime() {
    $JARK _stat uptime
}
