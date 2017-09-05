(ns the-lantern.routes.home
  (:require [the-lantern.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [the-lantern.utils.news :as news]
            [the-lantern.utils.nlp :as nlp]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]))

(defn home-page []
  (let [hls (news/get-random-headlines)
        word-bank (nlp/create-word-bank hls)
        trimmed-hls (take 5 (shuffle hls))
        bogus-headlines (pmap #(nlp/mush-string % word-bank) trimmed-hls)]
    (println bogus-headlines)
    (layout/render "home.html" {:headlines bogus-headlines})))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

