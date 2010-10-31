(defproject youtubegobbler "0.0.0.1"
  :description "Fetch list of videos in Youtube channel"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [clj-http "0.1.1"]]
  :dev-dependencies [[lein-run "1.0.0"]]
  :aot [youtube.gobbler]
  :main youtube.gobbler)
