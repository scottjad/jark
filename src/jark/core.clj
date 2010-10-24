(ns jark.core
  (:gen-class)
  (:use clojure.contrib.ns-utils)
  (:use clojure.contrib.pprint)
  (:import (java.io File FileReader PushbackReader FileWriter BufferedReader InputStreamReader))
  (:import (java.io FileNotFoundException)))

(defn jark-load
  "Loads the given clj file, and adds relative classpath"
  [file]
  (load-file file))

(defn jark-compile
  [compile-path namespace]
  (binding [*compile-path* compile-path]
    (compile (symbol namespace))))

(defn pp-plist [p]
  (cl-format true "~{~30A - ~A~%~}" p))

(defn pp-map [m]
  (let [p (mapcat #(vector (key %) (val %)) m)]
    (pp-plist p)))

(defn require-ns [n]
  (require (symbol n)))

(defn fun? [f]
  (instance? clojure.lang.IFn f))

(defn fns [n]
  (require-ns n)
  (let [namespace (symbol n)
        vars      (vec (ns-vars namespace))
        fns-list  (filter #(fun? %) vars)]
    (sort fns-list)))

(defn fn-args [n f]
  (nthnext
   (flatten (:arglists (meta (eval (read-string (format "#'%s/%s" n f)))))) 0))

(defn fn-doc [n f]
  (:doc (meta (eval (read-string (format "#'%s/%s" n f))))))

(defn fn-usage [n f]
  (str "USAGE: " f " " (fn-args n f)))

(defn help
  ([n]
     (require-ns n)
     (let [p (mapcat #(vector % (fn-doc n %)) (fns n))]
       (pp-plist p)))
  
  ([n f]
     (do
       (println (fn-doc n f))
       (println (fn-usage n f)))))

(defn about
  [n]
  (require-ns n)
  (println (let [p (into [] (fns n))]
                (cl-format true "~{~A ~}" p))))

(defn explicit-help [n f]
  (if (= f "help")
    (help n)
    (help n f)))

(defn cmd [p]
  (.. Runtime getRuntime (exec (str p))))

(defn cmdout [o]
  (let [r (BufferedReader.
           (InputStreamReader.
            (.getInputStream o)))]
    (dorun (map println (line-seq r)))))

(defn apply-fn [n f & args]
  (apply (resolve (symbol (str n "/" f))) args))

(defn dispatch-ns
  [n]
  (try
    (require-ns n)
    (help n)
    (catch FileNotFoundException e (println "No such module" e))))

 (defn -main
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
