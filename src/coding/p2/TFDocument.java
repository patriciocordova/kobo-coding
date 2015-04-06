/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coding.p2;

/**
 * Represents the relation between a term and a document. Holds the document ID
 * where the term appeared, the frequency or number of times where the term 
 * appeared in the document, and the TF*IDFScore of the term regarding the
 * document.
 * 
 * @author Patricio
 */
public class TFDocument {
    int documentId;
    int frequency;
    Double TFIDFScore;

    public TFDocument(int documentKey) {
        this.documentId = documentKey;
        frequency = 1;
    }

    public int getDocumentId() {
        return documentId;
    }

    /**
    * [?] I couldn't find the term frequency criteria that you used for the example.
    * Although the document mentions the following:
    *  - "If the same term appears multiple times in a query or a book record, only count it once"
    * which would mean "boolean frequencies", my results weren't the same.
    * 
    * However, I used "boolean frequencies": tf(t,d) = 1 if t occurs in d and 0 otherwise
    * 
    * @see http://en.wikipedia.org/wiki/Tf%E2%80%93idf
    */
    public int getTermFrequency() {
        if(frequency > 0){
            return 1;
        }
        return 0;
    }
    
    public double getTFIDFScore(double IDFScore) {
        if(TFIDFScore!=null){
            return TFIDFScore;
        }
        calculateTFIDfScore(IDFScore);
        return IDFScore;
    }

    public Double getTFIDFScore() {
        return TFIDFScore;
    }
    
    public void increaseFrequencyByOne(){
        frequency = frequency + 1;
    }
    
    public void calculateTFIDfScore(double IDFScore) {
        TFIDFScore = getTermFrequency() * IDFScore;
    }
}