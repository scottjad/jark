
DOC="Module for working with cljr build system"

help() {
    echo -e "list add remove"
}


list() {
    $JARK_CLIENT cljr.App list-repos
}

add() {
    $JARK_CLIENT cljr.App add-repo $*
}

remove() {
    $JARK_CLIENT cljr.App add-repo $*
}
