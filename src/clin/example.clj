(ns clin.example
  (:use clojure.contrib.pprint)
  (:use clin.core)
  (:gen-class))

(defn factorial
  "Calculate a factorial of a number"
  [n]
  (let [n (Integer. n)]
    (loop [cnt n acc 1]
         (if (zero? cnt)
           acc
           (recur (dec cnt) (* acc cnt))))))

(defn props
  "Display system properties"
  []
  (let [p (mapcat 
           #(vector (key %) (val %)) 
           (sort-by key (System/getProperties)))]
    (pp-plist p)))

(defn add
  "Add two numbers"
  [a b]
  (+ (Integer. a)  (Integer. b)))

(defn diff
  "Take a difference of two numbers"
  [a b]
  (- (Integer. a)  (Integer. b)))
