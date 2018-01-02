(ns spam-problem.view
  (:use [hiccup.core]
        [hiccup.page]
        [hiccup.element]))

(defn application [title & content]
  (html5
         [:head
          [:title title]

          [:body
           [:h1 content ]]]))
