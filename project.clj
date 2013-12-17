(defproject zeitgeist "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main zeitgeist.core
  :global-vars {*warn-on-reflection* true}
  :profiles {:uberjar {:aot :all}}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http            "0.7.8"]
                 [io.curtis/boilerpipe-clj "0.3.0"]])
