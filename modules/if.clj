(ns jark.if
  (:import (java.io File FileNotFoundException))
  (:use clojure.contrib.json)
  (:use jark.core))

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

(defn http
  "Serve the given ns over HTTP. http://host:8000/ns?arg=value&"
  [namespace]
  "Enabling namespace over HTTP. . http://host:8000/ns?arg=value&")

(defn json
  "Run the cli interface for any namespace and return json"
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
             (when ret (do
                         (json-str ret))))
           (catch IllegalArgumentException e (help module command))
           (catch NullPointerException e (println "No such command")))))))
