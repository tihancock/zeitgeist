(ns zeitgeist.markov
  (:use [clojure.string :only [split lower-case]]
        [clojure.set :only [map-invert]]))

(def re #" |\.|,|\?|!|\"|:|\(|\)")

(defn tokenise
  [text]
  (filter #(not (empty? %)) (split (lower-case text) re)))

(defn bigrams
  [text]
  (let [words (tokenise text)]
    (partition 2 1 words)))

;; Oh my god this is horrible
(defn- get-next-word-fn
  [m]
  (let [total-count (reduce + (vals m))]
    #(let [n (rand-int total-count)]
       (loop [choices (vec m)
              count (second (first choices))]
         (if (> count n)
           (first (first choices))
           (recur (rest choices) (+ count (second (second choices)))))))))

(defn markov-map
  [text]
  "Takes a string and converts it into a map of word to next word generator 
   function"
  (let [i1 (map (fn [i] {(first i) {(second i) 1}}) (bigrams text))
        i2 (apply merge-with (fn [m1 m2] (merge-with + m1 m2)) i1)]
    (apply hash-map (interleave (keys i2) (map get-next-word-fn (vals i2))))))

(defn generate-chain
  [map initial]
  (iterate (fn [w] ((map w (fn [] "NULL")))) initial))