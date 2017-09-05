(ns the-lantern.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [the-lantern.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[the-lantern started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[the-lantern has shut down successfully]=-"))
   :middleware wrap-dev})
