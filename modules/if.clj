(ns jark.if
  (:import (java.io File FileNotFoundException))
  (:use clojure.contrib.json)
  (:use jark.core))

(defn cli
  ([n]
     (dispatch-ns n))
  ([n f & args]
     (if (or (= (first args) "help") (= f "help"))
       (explicit-help n f)
       (do
         (require-ns n)
         (try
           (let [ret (apply (resolve (symbol (str n "/" f))) args)]
             (when ret (println ret)))
           (catch IllegalArgumentException e (help n f))
           (catch NullPointerException e (println "No such command")))))))

(defn cli-json
  ([n]
     (dispatch-ns n))
  ([n f & args]
     (if (or (= (first args) "help") (= f "help"))
       (explicit-help n f)
       (do
         (require-ns n)
         (try
           (let [ret (apply (resolve (symbol (str n "/" f))) args)]
             (when ret (do
                         (json-str ret))))
           (catch IllegalArgumentException e (help n f))
           (catch NullPointerException e (println "No such command")))))))
