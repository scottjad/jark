DOC="clojoredocs module"

help() {
    echo -e "search examples"
}

examples() {
    if [ ! -n "${PAGER+x}" ]; then 
        PAGER=less
    fi

    $JARK _doc examples $* | $PAGER
}

search() {
    $JARK _doc search $*
}
