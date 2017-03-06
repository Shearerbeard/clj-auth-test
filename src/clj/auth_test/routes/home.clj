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
  (GET "/" request
    (str "<a href=\"/authlink\">Authorized page</a><br />"
         "<a href=\"/status\">Status</a><br />"
         "<a href=\"/logout\">Log out</a>"))
  (GET "/status" request
    (let [count (:count (:session request) 0)
          session (assoc (:session request) :count (inc count))]
      (-> (ring.util.response/response
           (str "<p>We've hit the session page " (:count session)3000
                " times.</p><p>The current session: " session "</p>"))
          (assoc :session session))))
  (GET "/authlink" request
    (friend/authorize #{::user} (home-page)))
  (GET "/about" [] (about-page)))

