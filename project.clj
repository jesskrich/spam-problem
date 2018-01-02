(defproject spam-problem "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/java.jdbc "0.7.5"]
                 [mysql/mysql-connector-java "5.1.45"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [hiccup "1.0.5"]
                 [selmer "1.11.3"]]
  :main ^:skip-aot spam-problem.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
            :dev {:dependencies [[org.clojure/test.check "0.9.0"]]}})
