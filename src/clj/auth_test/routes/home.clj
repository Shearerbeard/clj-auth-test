(ns auth-test.routes.home
  (:require [auth-test.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [cemerick.friend :as friend]
            [auth-test.config :refer [env]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn temp-page []
  (layout/render "home.html" {:docs
                              (str "ClientSecret: " (:client-secret env)
                                   "ClientID: " (:client-id env))}))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/authlink" []
    (friend/authorize #{::user} "Authorized Page."))
  (GET "/temp" [] (temp-page))
  (GET "/about" [] (about-page)))

