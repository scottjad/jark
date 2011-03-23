
DOC="Module to manage the JVM server"

. ${CLJR_BIN}/shflags

commands() {
    echo -e "start stop connect threads stat uptime"
}

ng_server_start() {
    local port="$0"
    echo $port
    java -cp ${JARK_CP}:${JARK_JAR} -server com.martiansoftware.nailgun.NGServer $port <&- & 
    pid=$!
    echo ${pid} > /tmp/ng.pid
}

start() {
    DEFINE_string 'port' '2113' 'remote jark port' 'p'
    FLAGS "$@" || exit 1
    eval set -- "${FLAGS_ARGV}"
    port=${FLAGS_port}

    rm -f /tmp/jark.client

    java -cp ${JARK_CP}:${JARK_JAR} -server com.martiansoftware.nailgun.NGServer $port <&- & 2&> /dev/null 
    pid=$!
    echo ${pid} > /tmp/jark.pid
    echo ${port} > /tmp/jark.port
    echo "${CLJR_BIN}/ng --nailgun-port $port" > /tmp/jark.client

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
    echo "Started JVM server on port $port"
    exit 0
}

stop() {
    if [ $running == "no" ]; then
        echo "JVM server has already been stopped"
        exit 0
    fi
    echo "Stopping JVM server with pid `cat /tmp/jark.pid`"
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

connect() {
    DEFINE_string 'port' '2113' 'remote jark port' 'p'
    DEFINE_string 'host' 'localhost' 'remote host' 'r'

    FLAGS "$@" || exit 1
    eval set -- "${FLAGS_ARGV}"
    echo "${CLJR_BIN}/ng --nailgun-server ${FLAGS_host} --nailgun-port ${FLAGS_port}" > /tmp/jark.client
}
