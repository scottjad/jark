
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
    echo -e "install uninstall versions describe search"
}

install() {
    $NG cljr.App install $*
}

uninstall() {
    $NG cljr.App uninstall $*
}

versions() {
    $NG cljr.App versions $*
}

describe() {
    $NG cljr.App search $*
}


search() {
    $NG cljr.App search $*
}

run() {
    $NG cljr.App $*
}
