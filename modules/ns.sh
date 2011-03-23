
commands() {
    echo -e "list find load run repl"
}

list() {
    if [ ! -n "${PAGER+x}" ]; then 
        $JARK _ns list $* 
    else
        $JARK _ns list $* | $PAGER
    fi
}

find() {
    $JARK _ns find $* 
}

run() {
    $JARK _ns run $* 
}

repl() {
    rlwrap --remember -c -f ${CLJR_BIN}/clj_completions $JARK _ns repl $* 
}

load() {
    f=$(readlink -f $1)
    if [ $f ]; then
        $JARK _ns load "$f"
        exit 0
    else
        echo "No such file: $1"
        exit 1
    fi
}

