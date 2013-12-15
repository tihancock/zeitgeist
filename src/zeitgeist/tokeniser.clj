(ns zeitgeist.tokeniser
  (:use [clojure.string :as string :only [split]]))

(def re #"\s+|\"|\(|\)|(?=(\.|\?|!|,|:|;))|(?<=(\.|\?|!|,|:|;))")

(def sentence-enders #{"." "!" "?"})

(defn- is-ascii
  [word]
  (every? true? (map #(< 0 (int %) 127) word)))

(defn tokenise
  [text]
  (filter #(and (not (empty? %)) (is-ascii  %)) (string/split text re)))