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

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/authlink" request
    (friend/authorize #{::user} "Authorized Page."))
  (GET "/oauth2callback" []
    "Got Here WITH Facebook")
  (GET "/about" [] (about-page)))

