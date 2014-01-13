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
(def ticks (atom 0))

(def pool (mk-pool))

(def day (* 1000 60 60 24))

(every day #((swap! markov-map (make-markov-map))
             (swap! ticks inc)) pool)

(def sentence-chan (chan 2000))

(thread (loop [sentences (markov/get-sentences @markov-map)
               last-tick @ticks]
          (>!! sentence-chan (first sentences))
          (let [new-tick @ticks
                new-sentences (if (> new-tick last-tick)
                                (markov/get-sentences @markov-map)
                                (rest sentences))]
           (recur new-sentences @ticks))))

(defroutes handler
  (GET "/" [] (let [sentence (<!! sentence-chan)]
                html/generate-page sentence)))