(ns jark.swank
  (:gen-class)
  (:use swank.swank))

(defn start
  "Start a swank repl"
  ([port]
     (let [port (Integer. port)]
       (start-repl port)))
  ([]
     (start-repl 4005)))

(defn stop []
  "stop a remote swank")


