(ns the-lantern.routes.home
  (:require [the-lantern.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [the-lantern.utils.news :as news]
            [clojure.tools.logging :as log]
            [clojure.java.io :as io]))

(defn home-page []
  (log/info (news/get-random-headlines))
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))

