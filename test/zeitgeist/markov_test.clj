(ns zeitgeist.markov-test
  (:require [clojure.test :refer :all]
            [zeitgeist.markov :as markov :refer :all]))

(deftest bigramisation
  (testing "Bigram generation"
    (is (= (markov/bigrams "Hello there, how are you?")
           '(("Hello" "there") ("there" ",") ("," "how") ("how" "are") ("are" "you") ("you" "?"))))))