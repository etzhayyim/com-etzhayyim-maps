(require '[clojure.edn :as edn]
         '[clojure.java.io :as io]
         '[clojure.string :as str])

(def files (filter #(.isFile %) (file-seq (io/file "."))))
(defn path [f] (str/replace-first (str f) #"^\./" ""))
(doseq [f files :when (str/ends-with? (.getName f) ".edn")]
  (edn/read-string (slurp f)))
(let [lex-edn (set (map #(str/replace % #"\.edn$" "")
                        (map #(.substring (path %) (count "data/lex/"))
                             (filter #(str/starts-with? (path %) "data/lex/") files))))
      lex-json (set (map #(str/replace % #"\.json$" "")
                         (map #(.substring (path %) (count "wire/lex/"))
                              (filter #(str/starts-with? (path %) "wire/lex/") files))))
      forbidden (filter #(re-find #"(?i)(\.go|\.py|run_tests\.sh|publish\.bb)$" (path %)) files)
      misplaced (filter #(and (re-find #"\.(json|jsonld|bpmn)$" (path %))
                              (not (str/starts-with? (path %) "wire/"))
                              (not (= (path %) ".well-known/did.json"))) files)]
  (assert (= lex-edn lex-json) "canonical EDN and wire lexicon sets differ")
  (assert (empty? forbidden) (str "deprecated language/shell files: " forbidden))
  (assert (empty? misplaced) (str "external formats outside wire/: " misplaced)))
(println "audit: ok")
