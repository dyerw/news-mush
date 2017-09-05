(ns the-lantern.utils.news
  (:require [environ.core :refer [env]]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clj-http.client :as client]))

(def api-key (env :news-api-key))

(def api-host "https://newsapi.org")
(def sources-url (str api-host "/v1/sources"))

(def ^:private sources (atom nil))
(defn get-sources []
  (or @sources 
      (reset! sources 
              (-> sources-url
                  client/get
                  :body
                  (json/read-str :key-fn keyword)
                  :sources))))

(defn random-sources [n]
  (->> (get-sources)
      shuffle
      (take n)
      (map :id)))

