
DOC="Module to manage jark itself"

commands() {
    echo -e "install uninstall version"
}

version() {
    $JARK version
}

uninstall() {
    echo "uninstalling ..."
    $JARK vm stop
    rm -rf ${CLJR_CP}/jark*.jar
    rm -rf ${CLJR_CP}/jark.jar
    rm -rf ${CLJR_CP}/jark
}
