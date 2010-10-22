
DOC="Module for working with cljr build system"

help() {
    echo -e "install uninstall versions describe search cp list"
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

cp() {
    local $jar="$1"
    $JARK cp add $jar
}

run() {
    $JARK_CLIENT cljr.App $*
}

list() {
    $JARK_CLIENT cljr.App list
}
