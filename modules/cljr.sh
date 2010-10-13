
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
    echo -e "update | remove | add PACKAGE"
}

update() {
    for JAR in `find ${CLJR_CP} -name "*.jar" -print`
    do
        echo "Adding $JAR .."
        $NG ng-cp $JAR
    done
    exit 0
}

remove() {
    echo "Remove all cljr classpaths ..."
}

add() {
    echo "Add a given lib's jar to the JVM instance ..."
}

