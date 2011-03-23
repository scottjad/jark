(ns jark._ns
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
    (cmdout (cmd (str (. System getProperty "user.home")
                      "/.cljr/bin/ng ng-cp " (.toString basename))))
    (load-file file)))

(defn run
  "runs the given main function"
  [main-ns & args]
  (require-ns main-ns)
  (apply (resolve (symbol (str main-ns "/-main"))) args))

(defn repl
  "Launch a repl with given ns"
  [namespace]
  (let [fx (fn [] (in-ns (symbol namespace)))]
    (clojure.main/repl :init fx)))
