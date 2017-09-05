(ns the-lantern.utils.nlp
  (:require [postagga.tagger :refer [viterbi]]
            [postagga.en-fn-v-model :refer [en-model]]))

(def replacement-rate 0.5)

(defn dbg [x] (do (clojure.pprint/pprint x) (println "--") x))

(defn tag-string 
  "Takes a single string (a sentence) and returns a list of pairs like [word pos]"
  [string]
  (let [words (clojure.string/split string #" ")]
    (map vector 
         (viterbi en-model (clojure.string/lower-case words))
         words)))

(defn ^:private map-values [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn create-word-bank [strings]
    (->> (pmap tag-string strings)
         (reduce into [])
         (group-by first)))

(defn mush-string [string word-bank]
  (let [tags (tag-string (dbg string))
        words (clojure.string/split string #" ")]
    (->> words
         (map-indexed
           (fn [i n]
             (if (> (rand) replacement-rate)
                 (get words i)
                 (second (rand-nth (get word-bank (first (nth tags i))))))))
         (clojure.string/join " ")))) 
