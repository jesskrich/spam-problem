; (require 'spam-problem.core :reload)
; (gen/sample (s/gen ::email-record) 5)

(ns spam-problem.core
    (:gen-class)
    (:require [clojure.spec.alpha :as s]
              [clojure.spec.gen.alpha :as gen]))

(defn message-to-user [message]
    (println message))

(defn create-final-list [sorted-spam-scores]
    (let [below (reverse (get @sorted-spam-scores :below-avg))
          above (get @sorted-spam-scores :above-avg)
          final (atom '())
          below-cnt (count below)
          above-cnt (count above)
          update-list (fn [records] (swap! final concat records))]
          (if-not (empty? above)
            (update-list (flatten (conj (take-last (mod below-cnt above-cnt) below)
                                  (interleave above (partition-all (quot below-cnt above-cnt) below)))))
            (update-list below)) @final))

(defn collect-max-num-emails-below-avg-spam-score [in-range-records]
    (let [ordered-list (sort-by :spam-score in-range-records)
          reduced-list (reductions + (map :spam-score ordered-list))
          indexed-totals (map-indexed (fn [idx itm] [idx itm]) reduced-list)
          sorted-spam-scores (atom {:above-avg '() :below-avg '()})
          sendable (take-while #(<= (/ (second %) (+ (first %) 1)) 0.05) indexed-totals)
          current-total-spam-score-cnt (fn [n] (get-in (nth ordered-list (first n)) [:spam-score]))
          update-list (fn [l n] (swap! sorted-spam-scores update-in l merge (nth ordered-list (first n))))]
          (mapv #(if (< (current-total-spam-score-cnt %) 0.05)
                    (update-list [:below-avg] %)
                    (update-list [:above-avg] %)) sendable)
         (create-final-list sorted-spam-scores)))

(defn filter-records-within-spam-score-range [unique-records]
    (let [in-range-records (filter #(<= 0 (% :spam-score) 0.3) unique-records)]
        (if-not (empty? in-range-records)
            (collect-max-num-emails-below-avg-spam-score in-range-records)
        (message-to-user "No email records with spam score below 0.3"))))

(defn purge-duplicate-records [email-rec]
    (let [unique-records (map first (vals (group-by :email-address (reverse (sort-by :email-address email-rec)))))]
        (filter-records-within-spam-score-range unique-records)))
;
(defn print-record [email-rec]
    (purge-duplicate-records email-rec))

;
; (def email-domains
;     #{"indeediot.com"
;       "monstrous.com"
;       "linkedarkpattern.com"
;       "dired.com"
;       "lice.com"
;       "careershiller.com"
;       "glassbore.com"})
;
; (s/def ::ddd email-domains)
;
; (def email-regex
;     #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
;
; (s/def ::aaa (s/and string? #(re-matches email-regex %)))
;
; (s/def ::email-address
;     (s/with-gen
;         (s/and string? #(re-matches email-regex %))
;             #(->>
;         (gen/tuple (gen/such-that not-empty (gen/string-alphanumeric))
;                             (s/gen email-domains))
;         (gen/fmap (fn [[addr domain]] (str addr "@" domain))))))
;
; (s/def ::spam-score
;     (s/double-in :min 0 :max 0.1))
;
; (s/def ::email-record
;     (s/keys :req-un [::email-address ::spam-score]))
;
;
; (defn -main
;   "I don't do a whole lot ... yet."
;   [& args]
;   (println "hello"))
