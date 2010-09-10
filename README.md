
A command-line framework for clojure programs on the JVM.

## CLIN provides ...
 
1. A persistent JVM daemon that supports multiple clients
2. A thin client to run clojure programs from the command line

We refer to `CLIN` as the system and `clin` as the client.

## Quick start

### Install

From script:
1. [Download self-extracting script](http://github.com/downloads/icylisper/clin/clin)
2. Place it on your path and chmod it to be executable.

On Arch Linux from AUR repository:
    packer -S clin

### Try it out
1. clin clin.example factorial 5
2. clin clin.example props
3. From emacs: M-x slime-connect 127.0.0.1 4005

## Documentation 

### Basic usage:
    clin NAMESPACE FUNCTION [ARGS]

clin comes with a set of modules to manage the nailgun server, 
classpath and  clojure-namespaces. It also integrates nicely with
the clojure package management system [cljr](http://github.com/liebke/cljr) and lein.

### ng - Nailgun module to run the JVM as a daemon. 

* `clin ng start` Starts the nailgun server 
* `clin ng stop`  Stops the nailgun server

### cp - Classpath utilities

* `clin cp add [PATH]` Adds the given path to classpath. Path can be a directory or JAR.

* `clin cp list` List all the classpaths
* `clin cp cljr` Update the classpath with jars in the cljr repository.

### cm - Stuart Sierra's classpath-manager to run Java classes:

* `clin cm your-main-class` Runs the main function in the given class. eg. `clin cm clojure.main` 

### ns - Clojure namespace utilities

* `clin ns list [NAMESPACE PREFIX]` List all clojure namespaces in classpath. Optionally takes a prefix to filter eg. `clin ns list swank`
* `clin ns load FILE` Loads the given clojure file

### vm - JVM stat utilities

* `clin vm stat` Gives statistics for the running JVM daemon.

## Building clin
    
    lein jar && ./build
    
This will generate the self-extracting script `clin`

## LICENSE

Copyright (C) 2010 Isaac Praveen.

Licensed under the Apache License, Version 2.0 (the "License")
http://www.apache.org/licenses/LICENSE-2.0
