# NLP

- nlp is hard
  - NLP deals with computational processing of human languages
  - Find numeric way for representing natural language
- Tokenization
  - Break up words or not?
- Lemmatization convert variations to base form
  - student, students, student's -> student

- Word embedding:
  - Bag of word
    - just keep a map for word exist or not
  - tf-idf (term frequency)*(inverse document frequency)
    - Word appears a lot in a document is important.
    - Calculate score for each word
    - tf = word times/total length
    - Could be used in measuring document similarity
  - Word2vec
    - probablity of word appearance
  - GloVe
    - Similar relationships
- RNN recurrent neural net
- How to evaluate NLP performance
  - BLEU score?
- BERT
  - 