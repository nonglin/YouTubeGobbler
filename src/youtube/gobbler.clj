(ns youtube.gobbler
  (:require [clj-http.client :as client])
  (:require [clojure.contrib.json :as json])
  (:gen-class))

(defn fetch-videos
  ([user] (let [video-sublists (map #(fetch-videos user % 10)
                                    (iterate #(+ 10 %) 1))]
            (distinct (apply concat (take-while not-empty video-sublists)))))
  ([user start num]
     (try
       (let [response (json/read-json
                       (:body (client/get "http://gdata.youtube.com/feeds/api/videos"
                                          {:query-params {"v" 2
                                                          "author" user
                                                          "alt" "json"
                                                          "start-index" start
                                                          "max-results" num}})))
             feed (:feed response)
             total-results (:openSearch$totalResults feed)
             videos (:entry feed)]
         (map (fn [video] {:id (last (.split (first (vals (:id video)))
                                             ":"))
                           :title (first (vals (:title video)))})
              videos))
       (catch Exception ex []))))

(defn -main [& args]
  (if (empty? args) (println "Need to indicate user as command line argument.")
      (doseq [video (fetch-videos (first args))]
        (println (:id video) "          " (:title video)))))
