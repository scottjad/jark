
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

commands() {
    echo -e "list add run"
    exit 0
}

remove() {
     echo "command not implemented yet"
     exit 1
}

add() {
    local jar=`readlink -f "$1"`
    if [ -z $jar ]; then
        echo "USAGE jark cp add <jarpath>"
        exit 0
    fi
    if [ -d $jar ]; then
        for i in `find ${jar} -name "*.jar" -print`
        do
            echo "Adding $i .."
            $JARK_CLIENT ng-cp $i
        done
        $JARK_CLIENT ng-cp ${jar}
        exit 0
    fi

    jp=$(readlink -f $jar)
    if [ $? == "0" ]; then
        $JARK_CLIENT ng-cp $jp
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

ls() {
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
