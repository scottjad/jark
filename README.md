
JARK is a tool to manage classpaths for clojure programs on a persistent JVM

## JARK provides ...
 
1. A persistent JVM daemon that supports multiple clients
2. A thin client to run clojure programs from the command line

We refer to `JARK` as the system and `jark` as the client.

## Quick start

### Install

1. [Download self-extracting script](http://github.com/downloads/icylisper/jark/jark)
2. Place it on your path and chmod it to be executable.

On Arch Linux from AUR repository:
    packer -S jark

### Try it out
1. jark jark.example factorial 5
2. jark jark.example props
3. From emacs: M-x slime-connect 127.0.0.1 4005

## Documentation 

### Basic usage:
    jark NAMESPACE FUNCTION [ARGS]

jark comes with a set of modules to manage the nailgun server, 
classpath and  clojure-namespaces. It also integrates nicely with
the clojure package management system [cljr](http://github.com/liebke/cljr) and lein.

### ng - Nailgun module to run the JVM as a daemon. 

* `jark ng start` Starts the nailgun server 
* `jark ng stop`  Stops the nailgun server

### cp - Classpath utilities

* `jark cp add [PATH]` Adds the given path to classpath. Path can be a directory or JAR.

* `jark cp list` List all the classpaths
* `jark cp cljr` Update the classpath with jars in the cljr repository.

### cm - Stuart Sierra's classpath-manager to run Java classes:

* `jark cm your-main-class` Runs the main function in the given class. eg. `jark cm clojure.main` 

### ns - Clojure namespace utilities

* `jark ns list [NAMESPACE PREFIX]` List all clojure namespaces in classpath. Optionally takes a prefix to filter eg. `jark ns list swank`
* `jark ns load FILE` Loads the given clojure file

### vm - JVM stat utilities

* `jark vm stat` Gives statistics for the running JVM daemon.

## Building jark
    
    lein jar && ./build
    
This will generate the self-extracting script `jark`

## LICENSE

Copyright (C) 2010 Isaac Praveen.

Licensed under the Apache License, Version 2.0 (the "License")
http://www.apache.org/licenses/LICENSE-2.0
