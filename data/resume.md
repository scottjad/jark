
## Why Jark ?

Startup time of the Java Virtual Machine(JVM) is too slow and thereby command-line applications on the JVM are sluggish and very painful to use. And there is no existing simple way for multiple clients to access the same instance of the JVM. Jark is an attempt to run the JVM as a daemon and support connections from various clients like emacs, vim and even the command line. It also provides easy command-line interface to manage classpath and namespaces when developing or deploying clojure applications.
 
## Install
 
    git clone git://github.com/icylisper/jark.git
    ./build
    ./jark self install
    
Copy jark script to `PATH`.

## Getting started

     jark vm start [--port]

     jark vm connect [--host --port]

In the above, host defaults to localhost and port to 2113. Any number of JVM instances can be started.

## Namespace utilities

    (ns factorial
      (:refer-clojure :exclude [get]))
    
    (defn get [n] 
      (let [n (Integer. n)]    
      (apply * (take n (iterate inc 1)))))

    $ jark ns load ./factorial.clj

Running it is really simple:
    $ jark if cli factorial get 10

or simply 
    $ jark factorial get 10

Let us now define a fact-map function.

    $jark ns repl factorial

to get to the repl for the 'factorial' namespace. 

    (defn fact-map [n] 
       (let [n (Integer. n)]
             (zipmap (take n (iterate inc 1))
                     (map factorial/get (take n (iterate inc 1))))))

The interface(if) module provides cli and cli-json commands. The cli-json spits out JSON.
<pre class="terminal"><code>$ jark if cli-json factorial fact-map 3
 => {"3" : 6, "2" : 2, "1" : 1}
</code></pre>


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

* Currently works only on GNU/Linux, someday port it to windows.
* Support Vim 
* Integrate nrepl. Use that as the communication protocol
* Scripting with jark  `#!/usr/bin/env jark` should startup jark and run the clojure script.
* Have a ~/.jarkrc file that has sections corresponding to named-connections

      [a]
      host   = 192.168.1.2
      port   = 2113
      init   = swank start, vim start

      [b]
      host  = 192.168.1.3
      port  = 2114
      init  = h2.db load

      jark vm connect --name a 
      <do something>, and then
      jark vm connect --name b

