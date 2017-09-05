(ns user
  (:require [mount.core :as mount]
            the-lantern.core))

(defn start []
  (mount/start-without #'the-lantern.core/repl-server))

(defn stop []
  (mount/stop-except #'the-lantern.core/repl-server))

(defn restart []
  (stop)
  (start))


