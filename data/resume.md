
## Why Jark ?

Startup time of the Java Virtual Machine(JVM) is too slow and thereby command-line applications on the JVM are sluggish and very painful to use. And there is no existing simple way for multiple clients to access the same instance of the JVM. 
Jark is an attempt to run the JVM as a daemon and support connections from various clients like emacs, vim and even the command line. 
 
## Install
 
use a pre-built self-extracting script:
    wget https://github.com/downloads/icylisper/jark/jark.sh 
    cp jark.sh jark
    ./jark self install

or build it:
    git clone git://github.com/icylisper/jark.git
    lein jar && ./build
    ./jark self install
    
Copy jark script to `PATH`.


## Namespace utilities

    (ns factorial
      (:refer-clojure :exclude [get]))
    
    (defn get [n] 
      (let [n (Integer. n)]    
      (apply * (take n (iterate inc 1)))))

## Package management

Now, jark integrates well with exisiting clojure package management tools like cljr and lein.

    $ jark package search base64

    Libraries on Clojars.org that contain the term:  base64
    ----------------------------------------------
    clj-base64    0.0.0-SNAPSHOT
    org.clojars.s450r1/dcm4che-base64    2.0.23

    $ jark package install clj-base64
    $ jark cp add ~/.cljr/lib/clj-base64-*.jar
    $ jark cp list
    $ jark remvee.base64 encode-str "clojure rocks"
      => Y2xvanVyZSByb2Nrcw==
There is also the <i>repo</i> module to search, list, add or remove maven repositories.

## Doc search

It looks up clojuredoc.org and other resources for usage and examples of the clojure API.
     $ jark doc search map
     $ jark doc examples map

## Emacs interop

To start the swank server do <code>jark swank start</code>. The same can be added to ~/.jarkrc 
We can connect from emacs: 

      M-x slime-connect 127.0.0.1 4005

## Scripting with Jark

## 

## Available Modules

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

   
## TODO

* Works only on GNU/Linux, someday I will port it to windows.
* 
