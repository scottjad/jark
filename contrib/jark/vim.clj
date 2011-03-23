;; contributed by Ambrose Bonnaire-Sergeant

(ns jark.vim
  (:gen-class)
  (:use vimclojure.nails))

(defn start
  "Start a vimclojure server"
  ([] (start-server-thread "127.0.0.1" 2044)))

(defn stop []
  "todo")

