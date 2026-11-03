(ns pretraga-filmova.core
  (:gen-class)
  (:require
   [clojure.string :as str]))



;;uklanja znake interpunkcije
(defn rm-punctuation [text]
  (str/replace text #"\p{Punct}" ""))


;;proba, radi
;;(rm-punctuation "Iva. Hello! i was wondering???.")

;; deli tekst na reci i uklanja whitespace sa pocetka i kraja
(defn to-words [text] 
  (str/split (str/trim (str/lower-case text)) #"\s+"))


;; proba, radi
;;(to-words " I want tense sci-fi movie, but with little humor. 
;;          I also want it to last up to 90 minutes. ")


(to-words (rm-punctuation " I want tense sci-fi movie, but with little humor. 
           I also want it to last up to 90 minutes. "))

;; zelimo iz opisa da izvucemo stop reci, jer nam one ne doprinose znacenju i pretrazi
(def stop-words #{"is" "and" "an" "a" "the" "but" "for" "or" "in" "that" "this" 
                  "then" "on" "at" "with" "which" "who" "am" "are" "if" "because"
                  "about" "other" "some" "such" "how" "why" "only" "will" "can" "just" 
                  "should" "would" "have" "has" "had" "to" "here" "where" "all" "any" 
                  "each" "too" "of" "bit" "i" "want" "maybe" "like"})

(defn rm-stopwords [processed-text]
  (remove stop-words processed-text))

;;proba, radi uklanjanje stop reci
(rm-stopwords (to-words (rm-punctuation " I want romantic comedy movie, but to have a little bit of drama")))

;; izvlacimo kljucne reci
(defn extract-key-words [text]
  (rm-stopwords (to-words (rm-punctuation text))))

(extract-key-words "I want romantic comedy movie, but to have a little bit of drama! Maybe something like Proposal!!!" )