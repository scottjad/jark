(ns jark._doc
  (:refer-clojure :exclude [bytes])
  (:use clojure.contrib.json)
  (:use clojure.contrib.http.agent))

(defn- pp-examples [js]
  (let [ex (:examples js)]
    (doseq [i ex]
      (do (println (:body i))))))

(defn search [function]
   (read-json (string (http-agent (str "http://api.clojuredocs.org/search/" function)))))

(defn examples [sym]
  (pp-examples (read-json (string (http-agent (str "http://api.clojuredocs.org/examples/clojure.core/" sym))))))

