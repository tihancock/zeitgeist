(ns zeitgeist.scraper
  (:require [clj-http.client :as client]
            [boilerpipe-clj.core :as boilerpipe]))

(defn- get-urls
  []
  (let [proggit-new (client/get "http://www.reddit.com/r/programming/new.json" {:as :json})
        articles    (:children (:data (:body proggit-new)))]
    (map #(:url (:data %)) articles)))

(defn- get-page-body
  [url]
  (try
    (:body (client/get url {:insecure? true :throw-exceptions false}))
    (catch Exception e "")))

(defn scrape
  []
  (let [pages (doall (map get-page-body (get-urls)))]
    (map boilerpipe/get-text pages)))