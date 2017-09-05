(ns the-lantern.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [the-lantern.layout :refer [error-page]]
            [the-lantern.routes.home :refer [home-routes]]
            [compojure.route :as route]
            [the-lantern.env :refer [defaults]]
            [mount.core :as mount]
            [the-lantern.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
