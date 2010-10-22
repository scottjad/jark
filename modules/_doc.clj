(ns jark._doc
  (:refer-clojure :exclude [bytes])
  (:use jark.core)
  (:use clojure.contrib.json)
  (:use clojure.contrib.http.agent))

(defn- pp-examples [js]
  (let [ex (:examples js)]
    (doseq [i ex]
      (println "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
      (println (:body i)))))

(defn- pp-search [res]
  (let [p (mapcat #(vector (:name %) (:ns %)) res)]
    (pp-plist p)))

(defn- pp-comments [res]
  res)

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

(defn comments
  ([sym]
     (pp-comments (read-json
                   (string (http-agent (str "http://api.clojuredocs.org/comments/clojure.core/" sym))))))

  ([sym namespace]
     (pp-comments (read-json
                   (string (http-agent (str "http://api.clojuredocs.org/comments/" namespace "/" sym)))))))
