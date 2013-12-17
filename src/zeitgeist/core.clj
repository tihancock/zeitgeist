(ns zeitgeist.core
  (:gen-class)
  (:require [zeitgeist.scraper :as scraper]
            [zeitgeist.markov :as markov]
            [clojure.string :as string]))

(defn -main
  []
  (let [scrapings (scraper/scrape)
        m (markov/markov-map (apply str scrapings))]
    (doall (map println (take 30 (markov/get-sentences m))))))
