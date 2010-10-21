
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
    echo -e "list add remove search"
}


list() {
    $NG cljr.App list-repos
}

add() {
    $NG cljr.App add-repo $*
}

remove() {
    $NG cljr.App add-repo $*
}
