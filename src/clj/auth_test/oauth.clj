(ns auth-test.oauth
  (:require
   [cemerick.friend :as friend]
   [cemerick.url :as url]
   [auth-test.config :refer [env]]
   [friend-oauth2.workflow :as oauth2]
   [friend-oauth2.util :as util]))

(def callback-url "http://localhost:3000/oauth2callback")
(def parsed-url (url/url callback-url))

(def client-config
  {:client-id (:client-id env)
   :client-secret (:client-secret env)
   :callback {:domain (format "%s://%s:%s"
                              (:protocol parsed-url)
                              (:host parsed-url)
                              (:port parsed-url))
              :path (:path parsed-url)}})

(def uri-config
  {:authntication-uri {:url "https://facebook.com/dialog/oauth"
                       :query {:client_id (:client-id client-config)}
                       :client_secret (:client-secret client-config)
                       :redirect_uri callback-url}
   :access-token-uri {:url "https://graph.facebook.com/oauth/access_token"
                      :query {:client_id (:client-id client-config)
                              :client_secret (:client-secret client-config)
                              :redirect_uri callback-url}}})

(defn credential-fn
  [token]
    {:identity token
     :roles #{::user}})

(def friend-config
  {:allow-anon? true
   :workflows [()]})

