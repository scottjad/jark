(defproject jark "1.0"
  :description "JARK is a tool to manage classpaths for clojure programs on a persistent JVM"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [com.stuartsierra/classpath-manager "1.1.0"]
                 [swank-clojure "1.2.0"]
                 [leiningen/leiningen "1.1.0"]
                 [cljr "1.0.0-SNAPSHOT"]]

  :repositories  {"stuartsierra" "http://stuartsierra.com/maven2"}

  :namespaces [jark.core])
