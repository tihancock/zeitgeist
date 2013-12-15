(ns zeitgeist.tokeniser
  (:use [clojure.string :as string]))

(def re #"\s+|\"|\(|\)|(?=(\.|\?|!|,|:|;))|(?<=(\.|\?|!|,|:|;))")

(defn tokenise
  [text]
  (filter #(not (empty? %)) (string/split text re)))