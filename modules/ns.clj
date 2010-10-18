(ns jark.ns
  (:use clojure.contrib.pprint)
  (:use clojure.contrib.ns-utils)
  (:use clojure.contrib.find-namespaces)
  (:refer-clojure :exclude [list load find alias])
  (:import (java.io File FileNotFoundException))
  (:import (com.stuartsierra ClasspathManager))
  (:use jark.core))

(defn- ns-doc [] "Namespace utilities")

(defn- namespaces []
  (find-namespaces-on-classpath))

(defn- starting-with [module]
  (if (= (count (namespaces)) 0)
    (sort (filter #(. (str %) startsWith module) (namespaces)))
    (sort (filter #(. (str %) startsWith module) (map #(ns-name %) (all-ns))))))

(defn list
  "List all namespaces in the classpath. Optionally takes a namespace prefix"
  ([]
     (let [names (namespaces)]
       (doseq [i (sort names)]
         (println " " i))))
  ([module]
     (doseq [i (starting-with module)]
       (println " " i))))

(defn find
  "Find all namespaces containing the given name"
  [module]
  (doseq [i (starting-with module)]
       (println " " i)))

(defn load
  "Loads the given clj file, and adds relative classpath"
  [file]
  (let [basename (.getParentFile (File. file))]
    (cmdout (cmd (str "/home/icylisper/.cljr/bin/ng ng-cp " (.toString basename))))
    (load-file file)))

(defn run
  "runs the given main function"
  [main-ns & args]
  (require-module main-ns)
  (apply (resolve (symbol (str main-ns "/-main"))) args))

(defn alias
  "Give a nick to the given namespace"
  [namespace nick]
  "Setting nick ...")

(defn http
  "Serve the given ns over HTTP. http://host:8000/ns?arg=value&"
  [namespace]
  "Enabling namespace over HTTP. . http://host:8000/ns?arg=value&")

(defn cli
  "Run the cli interface for any namespace"
  ([module]
     (try
       (require-module module)
       (help module)
       (catch FileNotFoundException e (println "No such module" e))))
  ([module command & args]
     (if (or (= (first args) "help") (= command "help"))
       (explicit-help module command)
       (do
         (require-module module)
         (try
           (let [ret (apply (resolve (symbol (str module "/" command))) args)]
             (when ret (println ret)))
           (catch IllegalArgumentException e (help module command))
           (catch NullPointerException e (println "No such command")))))))
