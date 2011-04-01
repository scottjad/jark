DOC="nrepl module"

. ${CLJR_BIN}/shflags

commands() {
    echo -e "start stop connect send"
}

start() {
    DEFINE_string 'port' '9000' 'nrepl port' 'p'
    FLAGS "$@" || exit 1
    eval set -- "${FLAGS_ARGV}"
    $JARK _nrepl start ${FLAGS_port}
    exit 0
}

stop() {
    echo "Stopping server"
}

connect() {
    DEFINE_string 'port' '9000' 'nrepl port' 'p'
    DEFINE_string 'host' 'localhost' 'nrepl host' 'r'

    FLAGS "$@" || exit 1
    eval set -- "${FLAGS_ARGV}"
    $JARK _nrepl connect ${FLAGS_host} ${FLAGS_port}
    exit 0 
}

send() {
    DEFINE_string 'port' '9000' 'nrepl port' 'p'
    DEFINE_string 'host' 'localhost' 'nrepl host' 'r'
    DEFINE_string 'expression' '(+ 2 2)' 'expression' 'e'

    FLAGS "$@" || exit 1
    eval set -- "${FLAGS_ARGV}"
    $JARK _nrepl eval-expression ${FLAGS_host} ${FLAGS_port} "${FLAGS_expression}"
    exit 0 
}

