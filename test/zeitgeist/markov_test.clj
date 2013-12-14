(ns zeitgeist.markov-test
  (:require [clojure.test :refer :all]
            [zeitgeist.markov :as markov :refer :all]))

(deftest tokenisation
  (testing "Basic tokenisation"
    (is (= (markov/tokenise "Hello there") '("hello" "there"))))
  (testing "With all the trimmings"
    (is (= (markov/tokenise "Hello. there , I?!?do(n't bel)ieve we've m:et")
           '("hello" "there" "i" "do" "n't" "bel" "ieve" "we've" "m" "et")))))

(deftest bigramisation
  (testing "Bigram generation"
    (is (= (markov/bigrams "Hello there, how are you?")
           '(("hello" "there") ("there" "how") ("how" "are") ("are" "you"))))))