(ns jark._stat
   (:gen-class)
   (:use clojure.contrib.pprint)
   (:use jark.core)
   (:import (java.lang.management RuntimeMXBean ManagementFactory))
   (:import (jark SystemThreadList))
   (:import (java.util Date)))

(defn- ns-doc [] "JVM statistics")

(defn stats
  "Display current statistics of the JVM"
  []
  (let [mx    (ManagementFactory/getRuntimeMXBean)
        props {"nailgun port" 2113
               "swank port" 4005
               "start time" (.toString (Date. (.getStartTime mx)))
               "uptime" (str (.toString (.getUptime mx)) "ms")}
        p     (mapcat #(vector (key %) (val %)) props)]
    (pp-plist p)))

(defn uptime
  "Display uptime of the JVM"
  []
  (let [mx    (ManagementFactory/getRuntimeMXBean)
        uptime (str (.toString (.getUptime mx)) "ms")]
    uptime))

(defn threads
  "Display all running threads"
  []
  (let [stl (SystemThreadList.)]
    (doseq [i (map #(.getName %) (.getAllThreads stl))]
      (println i))))
