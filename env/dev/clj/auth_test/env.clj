(ns auth-test.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [auth-test.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[auth-test started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[auth-test has shut down successfully]=-"))
   :middleware wrap-dev})
