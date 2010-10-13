(ns jark.vm
  (:gen-class)
  (:use clojure.contrib.pprint)
  (:use jark.core)
  (:import (java.lang.management RuntimeMXBean ManagementFactory))
  (:import (java.util Date)))

(defn- ns-doc [] "JVM statistics")

(defn stat
  "Display statistics of the JVM"
  []
  (let [mx    (ManagementFactory/getRuntimeMXBean)
        props {"port" 2113
               "swank port" 4005
               "args" (.toString (.getInputArguments mx)) 
               "start time" (.toString (Date. (.getStartTime mx)))
               "uptime" (str (.toString (.getUptime mx)) "ms")}
        p     (mapcat #(vector (key %) (val %)) props)]
    (pp-plist p)))

