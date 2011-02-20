
JARK is a tool to manage classpaths for clojure programs on a persistent JVM

## JARK provides ...
 
1. A persistent JVM daemon that supports multiple clients
2. A thin client to run clojure programs from the command line

We refer to `JARK` as the system and `jark` as the client.

## BUILD
    
    lein jar && ./build
    
This will generate the self-extracting script `jark` . Copy it to
`PATH`.

or use a pre-build script:

     wget https://github.com/downloads/icylisper/jark/jark.sh 
     cp jark.sh jark

## USAGE

    jark vm start

    jark MODULE COMMAND [ARGS]
   
    Available modules: 
    cljr    - reload add
    cp      - list add run
    doc     - search examples comments
    if      - cli cli-json
    ns      - find list load repl run
    package - install uninstall versions describe deps search installed latest
    repo    - list add remove
    self    - install uninstall version about
    swank   - start stop
    vm      - start stop threads stat uptime
   
## NOTE
 - Works only on GNU/Linux, currently.
