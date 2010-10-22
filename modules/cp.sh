
DOC="Classpath utilities"

readlink_e() {
    self="$0"
    while test -h "$self"; do
	cd "$(dirname $self)"
	self=`readlink "$self"`
    done
    cd "$(dirname $self)"
    pwd
}

help() {
    echo -e "list add run"
}

add-cljr-jars() {
    for JAR in `find ${CLJR_CP} -name "*.jar" -print`
    do
        echo "Adding $JAR .."
        $JARK_CLIENT ng-cp $JAR
    done
    exit 0
}

remove() {
     echo "command not implemented yet"
     exit 1
}

add() {
    local jar="$1"
    if [ -d $jar ]; then
        for i in `find ${jar} -name "*.jar" -print`
        do
            echo "Adding $i .."
            $JARK_CLIENT ng-cp $i
        done
        $JARK_CLIENT ng-cp ${jar}
        exit 0
    fi
    if [ -z $jar ]; then
        echo "USAGE jark cp add $jarpath"
        exit 0
    fi
    jp=$(readlink -f $jar)
    if [ $? == "0" ]; then
        $JARK_CLIENT ng-cp $jar
        $JARK_CLIENT ng-cp
        exit 0
    else
        exit 1
    fi
}

list() {
    $JARK_CLIENT ng-cp
     exit 0
}

run() {
    local mainclass="$1"
    touch classpath
    $JARK_CLIENT ng-alias cm com.stuartsierra.ClasspathManager
    $JARK_CLIENT cm $mainclass
    exit 0
}
