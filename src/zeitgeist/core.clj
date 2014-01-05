(ns zeitgeist.core
  (:gen-class)
  (:require [zeitgeist.scraper :as scraper]
            [zeitgeist.markov :as markov]
            [zeitgeist.html :as html]
            [clojure.string :as string]
            [compojure.route :as route]
            [clojure.core.async :as async :refer :all])
  (:use [overtone.at-at]
        [compojure.core]))

(defn- make-markov-map
  []
  (let [scrapings (scraper/scrape)]
    (markov/markov-map (apply str scrapings))))

(def markov-map (atom (make-markov-map)))

(def pool (mk-pool))

(def day (* 1000 60 60 24))

(every day #(swap! markov-map (make-markov-map)) pool)

(def sentence-chan (chan 2000))

(thread (loop [sentences (markov/get-sentences @markov-map)]
          (>!! sentence-chan (first sentences))
          (recur (rest sentences))))

(defroutes handler
  (GET "/" [] (let [sentence (<!! sentence-chan)]
                html/generate-page sentence)))