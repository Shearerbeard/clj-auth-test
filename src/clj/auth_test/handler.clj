(ns auth-test.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [cemerick.friend :as friend]
            [auth-test.layout :refer [error-page]]
            [auth-test.routes.home :refer [home-routes]]
            [auth-test.oauth :refer [oauth-config]]
            [clojure.tools.logging :as log]
            [compojure.route :as route]
            [auth-test.env :refer [defaults]]
            [mount.core :as mount]
            [auth-test.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (friend/authenticate oauth-config)
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(defn app []
  (middleware/wrap-base #'app-routes))
