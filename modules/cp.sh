
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
    echo -e "list | add PATH"
}

remove() {
     echo "command not implemented yet"
     exit 1
}

add() {
    local jar="$1"
    if [ -z $jar ]; then
        echo "USAGE jark cp add $jarpath"
        exit 0
    fi
    jp=$(readlink -f $jar)
    if [ $? == "0" ]; then
        $NG ng-cp $jar
        $NG ng-cp
        exit 0
    else
        exit 1
    fi
}

list() {
    $NG ng-cp
     exit 0
}
