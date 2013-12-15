(ns zeitgeist.tokeniser-test
  (:require [clojure.test :refer :all]
            [zeitgeist.tokeniser :as tokeniser :refer :all]))

(deftest tokenisation
  (testing "Basic tokenisation"
    (is (= (tokeniser/tokenise "Hello there") '("Hello" "there"))))
  (testing "With all the trimmings"
    (is (= (tokeniser/tokenise "Hello. there , I?!?do(n't bel)ieve we've m:e;t")
           '("Hello" "." "there" "," "I" "?" "!" "?" "do" "n't" "bel" "ieve" "we've" "m" ":" "e" ";" "t")))))