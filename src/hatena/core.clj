(ns hatena.core
  (:require [clj-http.client :as client]
            [clojure.xml :as xml]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io])
  (:gen-class))

(def content-keys #{:title :link :dc:date})

(defn create-url [category]
  (format "http://b.hatena.ne.jp/hotentry/%s.rss" category))

(defn get-items [url] (filter #(= :item (:tag %)) (get-in (xml/parse url) [:content])))

(defn filter-contents [tags]
  (let [content (:content tags)]
    (filter #(content-keys (:tag %)) content)))

(defn create-content-set [items]
  (reduce (fn [cols n] (conj cols (filter-contents n))) [] items))

(defn filter-content-from-link-set [link-set] (reduce (fn [cols n] (conj cols (map #(first (:content %)) n)) ) [] link-set))

(defn into-vec [seq] (reduce (fn [cols n] (conj cols n)) [] seq))

(defn into-double-vecs [double-seq]
  (reduce
   (fn [cols n]
     (conj cols
           (reduce (fn [cols2 n2] (conj cols2 n2)) [] n))) [] double-seq))

(defn write-into-csc [content]
  (with-open [writer (io/writer "/tmp/hoge.csv")]
    (csv/write-csv writer content)))

(defn -main
  [& args]
  (write-into-csc
   (into-double-vecs
    (filter-content-from-link-set
     (create-content-set
      (get-items
       (create-url (first args))))))))
