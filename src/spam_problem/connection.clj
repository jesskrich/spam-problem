(ns spam-problem.connection
    (:require [clojure.java.jdbc :as j]))

(def db-map {:subprotocol "mysql"
             :subname "//localhost:8889/email_records"
             :user "root"
             :password "root"})

(defn add-new-record-to-db [database]
    (j/insert-multi! database :records
        [{:email_address "is_this_working@g.com" :spam_score 0.05}
        {:email_address "this_is_working@g.com" :spam_score 0.03}]))

; (j/query db-map ["SELECT * FROM records"])
