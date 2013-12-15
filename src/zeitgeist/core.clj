(ns zeitgeist.core
  (:require [zeitgeist.scraper :as scraper]
            [zeitgeist.markov :as markov]
            [clojure.string :as string]))

(defn -main
  []
  (let [scrapings (scraper/scrape)
        m (markov/markov-map (apply str scrapings))
        map-keys (keys m)
        initial (nth map-keys (rand-int (count map-keys)))]
    (println (string/join " " (take 100 (markov/generate-chain m initial))))))
