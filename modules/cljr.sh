DOC="Manage the cljr repository"

commands() {
    echo -e "reload add"
}

add() {
    for JAR in `find ${CLJR_CP} -name "*.jar" -print`
    do
        echo "Adding $JAR .."
        $JARK_CLIENT ng-cp $JAR
    done
    exit 0
}

reload() {
    $JARK_CLIENT cljr.App reload
}

