(defproject hatena "0.1.0-SNAPSHOT"
  :description "You can get rss feeds from hatena"
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.9.1"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.clojure/data.csv "0.1.4"]]
  :main ^:skip-aot hatena.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
