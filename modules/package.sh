
DOC="Module for working with cljr build system"

commands() {
    echo -e "install uninstall versions describe deps search installed latest"
}

install() {
    $JARK_CLIENT cljr.App install $*
}

uninstall() {
    $JARK_CLIENT cljr.App uninstall $*
}

versions() {
    $JARK_CLIENT cljr.App versions $*
}

describe() {
    $JARK_CLIENT cljr.App search $*
}

search() {
    $JARK_CLIENT cljr.App search $*
}

run() {
    $JARK_CLIENT cljr.App $*
}

installed() {
    $JARK_CLIENT cljr.App list
}

deps() {
    if [ -z $2 ]; then
        ver=`$JARK cljr.clojars get-latest-version $1`
        $JARK cljr.clojars print-library-dependencies $1 $ver
    else
        $JARK cljr.clojars print-library-dependencies $1 $2
    fi
}

latest() {
    $JARK cljr.clojars get-latest-version $*
}
