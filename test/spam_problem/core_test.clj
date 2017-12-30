(ns spam-problem.core-test
  (:require [clojure.test :refer :all]
            [spam-problem.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest b-test
    (testing "FIX this"
     (is (= 2 2))))

     (with-test
         (defn my-function [x y]
           (+ x y))
       (is (= 4 (my-function 2 2)))
       (is (= 8 (my-function 4 4))))

       (deftest factorial-tests
         (testing "factorial"
           (testing "base cases"
             (is (= (factorial 1) 1))
             (is (= (factorial 2) 2))
             (is (= (factorial 0) 0)))
           (testing "general case"
             (is ( = (factorial 3) 6)))))

        (deftest message-to-user-tests
            (is (nil? (message-to-user "hi"))))

             ; (testing "whatever" is purely for readable purposes
