DOC="clojoredocs module"

help() {
    echo -e "search examples"
}

examples() {
    if [ ! -n "${PAGER+x}" ]; then 
        $JARK _doc examples $* 
    else
        $JARK _doc examples $* | $PAGER
    fi
}

search() {
    if [ ! -n "${PAGER+x}" ]; then 
        $JARK _doc search $* 
    else
        $JARK _doc search $* | $PAGER
    fi
}
