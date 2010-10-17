(ns jark.core
  (:gen-class)
  (:use clojure.contrib.ns-utils)
  (:use clojure.contrib.pprint)
  (:import (java.io File FileReader PushbackReader FileWriter BufferedReader InputStreamReader))
  (:import (java.io FileNotFoundException)))

(defn jark-load
  "Loads the given clj file, and adds relative classpath"
  [file]
  ;; FIXME: check for file or dir existence
  ;; Do ns introspection and add classpath
  (load-file file))

(defn jark-compile
  [compile-path namespace]
  (binding [*compile-path* compile-path]
    (compile (symbol namespace))))

(defn pp-plist [p]
  (cl-format true "~{~10A - ~A~%~}" p))

(defn pp-map [m]
  (let [p (mapcat #(vector (key %) (val %)) m)]
    (pp-plist p)))

(defn require-module [module]
  (require (symbol module)))

(defn command? [command]
  (instance? clojure.lang.IFn command))

(defn commands [module]
  (require-module module)
  (let [namespace (symbol module)
        vars      (vec (ns-vars namespace))
        fns       (filter #(command? %) vars)]
    (sort fns)))

(defn command-args [module command]
  (nthnext
   (flatten (:arglists (meta (eval (read-string (format "#'%s/%s" module command)))))) 0))

(defn doc-string [module command]
  (:doc (meta (eval (read-string (format "#'%s/%s" module command))))))

(defn usage [module command]
  (str "USAGE: " command " " (command-args module command)))

(defn help
  ([module]
     (require-module module)
     (let [p (mapcat #(vector % (doc-string module %)) (commands module))]
       (pp-plist p)))
  
  ([module command]
     (do
       (println (doc-string module command))
       (println (usage module command)))))

(defn about
  ([module]
     (require-module module)
     (println (let [p (into [] (commands module))]
                (cl-format true "~{~A | ~}" p)))))

(defn explicit-help [module command]
  (if (= command "help")
    (help module)
    (help module command)))

(defn cmd [p] (.. Runtime getRuntime (exec (str p))))

(defn cmdout [o]
  (let [r (BufferedReader.
           (InputStreamReader.
            (.getInputStream o)))]
    (dorun (map println (line-seq r)))))

(defn -main
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
