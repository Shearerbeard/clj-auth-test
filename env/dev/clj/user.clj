(ns user
  (:require [mount.core :as mount]
            auth-test.core))

(defn start []
  (mount/start-without #'auth-test.core/http-server
                       #'auth-test.core/repl-server))

(defn stop []
  (mount/stop-except #'auth-test.core/http-server
                     #'auth-test.core/repl-server))

(defn restart []
  (stop)
  (start))


