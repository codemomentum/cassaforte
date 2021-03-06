(ns clojurewerkz.cassaforte.iterate-world-test
  (:require [clojurewerkz.cassaforte.test-helper :as th]
            [clojurewerkz.cassaforte.cql :refer :all]
            [clojure.test :refer :all]
            [clojurewerkz.cassaforte.query :refer :all]))

(use-fixtures :each th/initialize!)

(deftest iterate-world-test
  (th/test-combinations
   (dotimes [i 100]
     (insert :users {:name (str "name_" i) :city (str "city" i) :age (int i)})))

  (let [res (group-by :name
                      (take 100 (iterate-world :users :name 10)))]
    (dotimes [i 100]
      (let [item (first (get res (str "name_" i)))]
        (is (= {:name (str "name_" i) :city (str "city" i) :age (int i)}
               item))))))
