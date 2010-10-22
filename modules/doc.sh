DOC="clojoredocs module"

help() {
    echo -e "example fn"
}

example() {
    $JARK _doc example $* | less
}

fn() {
    $JARK _doc fn $*
}
