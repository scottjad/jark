(ns jark._doc
  (:refer-clojure :exclude [bytes])
  (:use jark.core)
  (:use clojure.contrib.json)
  (:use clojure.contrib.http.agent))

(defn- pp-examples [js]
  (let [ex (:examples js)]
    (doseq [i ex]
      (println "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
      (println (:body i))
      (println)
      (println "  *** Last Updated:" (:updated_at ex)))))

(defn- pp-search [res]
  (let [p (mapcat #(vector (:name %) (:ns %)) res)]
    (pp-plist p)))

(defn search [function]
  (pp-search (read-json
              (string (http-agent (str "http://api.clojuredocs.org/search/" function))))))

(defn examples
  ([sym]
     (pp-examples (read-json
                (string (http-agent (str "http://api.clojuredocs.org/examples/clojure.core/" sym))))))

  ([sym namespace]
     (pp-examples (read-json
                (string (http-agent (str "http://api.clojuredocs.org/examples/" namespace "/" sym)))))))
