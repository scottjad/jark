(ns jark.doc
  (:refer-clojure :exclude [bytes])
  (:use clojure.contrib.json)
  (:use clojure.contrib.http.agent))

(defn- pp-fn [js]
  js)

(defn fn [function]
  (read-json (string (http-agent (str "http://api.clojuredocs.org/search/" function)))))

(defn example [sym]
  (read-json (string (http-agent (str "http://api.clojuredocs.org/examples/clojure.core/" sym)))))

  


