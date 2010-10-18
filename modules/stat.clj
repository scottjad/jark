(ns jark.stat
  (:gen-class)
  (:use clojure.contrib.pprint)
  (:use jark.core)
  (:import (java.lang.management RuntimeMXBean ManagementFactory))
  (:import (jark.util SystemThreadList))
  (:import (java.util Date)))

(defn- ns-doc [] "JVM statistics")

(defn resource
  "Display current statistics of the JVM"
  []
  (let [mx    (ManagementFactory/getRuntimeMXBean)
        props {"args" (.toString (.getInputArguments mx)) 
               "start time" (.toString (Date. (.getStartTime mx)))}
        p     (mapcat #(vector (key %) (val %)) props)]
    (pp-plist p)))

(defn services
  "Display current statistics of the JVM"
  []
  (let [mx    (ManagementFactory/getRuntimeMXBean)
        props {"nailgun port" 2113
               "swank port" 4005
               "start time" (.toString (Date. (.getStartTime mx)))
               "uptime" (str (.toString (.getUptime mx)) "ms")}
        p     (mapcat #(vector (key %) (val %)) props)]
    (pp-plist p)))

(defn threads
  "Display all running threads"
  []
  (let [stl (SystemThreadList.)]
    (map #(str (.toString %) "\n") (.getAllThreads stl))))
