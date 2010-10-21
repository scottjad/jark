
DOC="Module to manage jark itself"

help() {
    echo -e "install uninstall version about"
}

version() {
    echo "jark version: $VERSION"
}

uninstall() {
    echo "uninstalling ..."
    $JARK vm stop
    rm -rf ${CLJ_CP}/jark*.jar
    rm -rf ${CLJ_CP}/jark.jar
    rm -rf ${CLJ_CP}/jark
}
