(ns the-lantern.utils.news
  (:require [environ.core :refer [env]]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clj-http.client :as client]))

(def api-key (env :news-api-key))

(def api-host "https://newsapi.org")
(def sources-url (str api-host "/v1/sources"))
(def articles-url (str api-host "/v1/articles"))

(defn ^:private parse-json-response [response]
  (json/read-str (:body response) :key-fn keyword))

(def ^:private sources (atom nil))
(defn get-sources []
  (or @sources 
      (reset! sources 
              (-> sources-url
                  client/get
                  parse-json-response
                  :sources))))

(defn random-sources [n]
  (->> (get-sources)
      shuffle
      (take n)
      (filter #(= (:language %) "en"))
      (map :id)))

(defn get-articles [source]
  (-> articles-url
      (client/get {:query-params {"apiKey" api-key "source" source}})
      parse-json-response))

(defn get-random-headlines []
  (->> (random-sources 5)
       (map get-articles)
       (map :articles)
       flatten
       (map :title)
       (filter (complement nil?))))

