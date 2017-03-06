(ns auth-test.oauth
  (:require
   [cemerick.friend :as friend]
   [cemerick.url :as url]
   [auth-test.config :refer [env]]
   [clojure.tools.logging :as log]
   [friend-oauth2.workflow :as oauth2]
   [friend-oauth2.util :as util]
   [mount.core :as mount]
   [clojure.tools.logging :as log]))

(def callback-url "http://localhost:3000/oauth2callback")

(defn mk-client-config [{:keys [client-id client-secret]}]
  (let [parsed-url (url/url callback-url)]
    {:client-id client-id
     :client-secret client-secret
     :callback {:domain (format "%s://%s:%s"
                                (:protocol parsed-url)
                                (:host parsed-url)
                                (:port parsed-url))
                :path (:path parsed-url)}}))


(defn mk-uri-config [{:keys [client-id client-secret]}]
  {:authentication-uri {:url "https://www.facebook.com/dialog/oauth"
                        :query {:client_id client-id
                                :redirect_uri callback-url}}
   :access-token-uri {:url "https://graph.facebook.com/oauth/access_token"
                      :query {:client_id client-id
                              :client_secret client-secret
                              :redirect_uri callback-url}}})

(defn credential-fn
  [token]
    {:identity token
     :roles #{::user}})

(defn build-oauth-config [current-env]
  {:allow-anon? true
   :workflows [(oauth2/workflow
                {:client-config (mk-client-config current-env)
                 :uri-config (mk-uri-config current-env)
                 :access-token-parsefn util/get-access-token-from-params
                 :credential-fn credential-fn})]})

(mount/defstate oauth-config
  :start (build-oauth-config env))
