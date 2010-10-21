
DOC="Module for working with cljr build system"

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
    echo -e "update run"
}

update() {
    for JAR in `find ${CLJR_CP} -name "*.jar" -print`
    do
        echo "Adding $JAR .."
        $NG ng-cp $JAR
    done
    exit 0
}

add() {
    echo "Add a given lib's jar to the JVM instance ..."
}

run() {
    $NG cljr.App $*
}

