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

(def words (extract-key-words "I want romantic comedy movie, but to have a little bit of drama! Maybe something like Proposal!!!, and not horror. If possible some comedy.") )

(def negations #{"not" "no" "without" "never"})
;; ne radi ako imamo vise negacija u tekstu koji saljemo!!
;;(first(rest (drop-while (complement negations) words)))


;; pravimo funkciju koja ce da prepozna negacije
(defn get-neg-words [words]
  (cond
    (empty? words)
    '()
    (negations (first words))
    (cons (second words) (get-neg-words (drop 2 words)))
    :else
    (get-neg-words (rest words))
    ))

;;zatim iz liste izbacujemo te negativne reci
(defn search-parameters [words]
   (cond
    (empty? words)
    '()
    (negations (first words))
    (search-parameters (drop 2 words))
    :else
    (cons (first words) (search-parameters (rest words)))
     ))

;;obe funkcije nam trebaju za filtriranje

;;proba
(get-neg-words words)
(search-parameters words)


