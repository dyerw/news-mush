(ns the-lantern.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[the-lantern started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[the-lantern has shut down successfully]=-"))
   :middleware identity})
