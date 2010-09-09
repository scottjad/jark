(ns clin.ns
  (:use clojure.contrib.pprint)
  (:use clojure.contrib.ns-utils)
  (:use clojure.contrib.find-namespaces)
  (:refer-clojure :exclude [list load])
  (:use clin.core))

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

(defn load
  "Loads the given clj file, and adds relative classpath"
  [file]
  ;; FIXME: check for file or dir existence
  ;; Do ns introspection and add classpath
  (load-file file))

(defn reload
  "Reload the given namespace"
  [namespace]
  ;; FIXME: get file for given namespace
  (load-file namespace))

(defn stat
  "Display statistics pertaining to different namespaces"
  []
  (map count (map starting-with '("clojure" "swank" "clojure.contrib" ""))))
