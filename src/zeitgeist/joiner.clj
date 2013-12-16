(ns zeitgeist.joiner
  (:require [clojure.string :as string]))

(def no-spaces #{"," "." ":" "?" "!"})

(defn- get-spaces
  [tokens]
  (let [pairs (partition 2 1 tokens)]
    (vec (map (fn [[_ t]] (if (no-spaces t) "" " ")) pairs))))

(defn join
  [tokens]
  (apply str (interleave tokens (conj (get-spaces tokens) ""))))