(ns zeitgeist.core
  (:gen-class)
  (:require [zeitgeist.scraper :as scraper]
            [zeitgeist.markov :as markov]
            [zeitgeist.html :as html]
            [clojure.string :as string]))

(defn -main
  []
  (let [scrapings (scraper/scrape)
        m (markov/markov-map (apply str scrapings))]
    (spit "zeitgeist.html" (html/generate-page (take 30 (markov/get-sentences m))))))
