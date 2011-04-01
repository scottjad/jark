(ns jark._nrepl
  (:require [clojure.tools.nrepl :as nrepl]))

(defn start [port]
  (nrepl/start-server (Integer. port)))

(defn stop []
  (println "Stopping nrepl server"))

(defn connect [host port]
  (nrepl/connect host (Integer. port)))

(defn eval-expression [host port exp]
  (let [connection     (connect host port)
        repl           (:send connection)
        repl-seq       (comp nrepl/response-seq repl)
        repl-receive   (comp (fn [r#] (r#)) repl)
        repl-read      (comp nrepl/read-response-value repl-receive)
        repl-value     (comp :value repl-read)]
    (println (repl-value exp))
    (:close connection)))
