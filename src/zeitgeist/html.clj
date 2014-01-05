(ns zeitgeist.html
  (:use [hiccup.core]))

(defn generate-page
  [sentences]
  (html [:ul 
         (for [s sentences]
           [:li s])]))