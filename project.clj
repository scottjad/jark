(defproject jark "0.1"
  :description "Tool to manage the jars on the JVM"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [com.stuartsierra/classpath-manager "1.1.0"]
                 [swank-clojure "1.2.0"]]

  :repositories  {"stuartsierra" "http://stuartsierra.com/maven2"}

  :namespaces [jark.core])
