(ns jark.swank
  (:gen-class)
  (:use swank.swank))

(defn start
  ([port]
     (let [port (Integer. port)]
       (start-repl port)))
  ([]
     (start-repl 4005)))


