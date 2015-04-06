package coding.p2;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holds all the documents where the term appeared as well as the term's IDF score. 
 * Both scores, TF*IDFs and IDF, are calculated on demand. This means that they are
 * calculated only when the user finds the word and only the first time.
 * 
 * @author Patricio
 */
public class Term {
    Map<Integer,TFDocument> relatedDocuments;
    Double IDFScore;
    boolean TFIDFCalculated;
    
    public Term() {
        relatedDocuments = new TreeMap<>();
        TFIDFCalculated = false;
    }
  
    public Term(Document document) {
        relatedDocuments = new TreeMap<>();
        TFIDFCalculated = false;
        addDocumentId(document.getId());
    }
    
    private void addDocumentId(int id){
        TFDocument termDocument = new TFDocument(id);
        relatedDocuments.put(id, termDocument);
    }
    
    /**
    * Adds the document to the list of documents where the word has been found or
    * increment the times when the word was found in the document.
    */
    public void increaseTermFrequency(Document document){
        int documentId = document.getId();
        TFDocument termDocument = relatedDocuments.get(documentId);
        if(termDocument == null){
            this.addDocumentId(documentId);
        }else{
            termDocument.increaseFrequencyByOne();
        }
    }
    
    /**
    * Calculates the TF*IDF scores of all the documents where the term has been found.
    *
    * @return   all the documents related to the term carrying their updated TF*IDF scores
    */
    public Map<Integer,TFDocument> getTFIDFScoredDocuments(double totalDocuments) {
        if(TFIDFCalculated == true) return relatedDocuments;
        // Calculate TF*IDF scores for all documents related to term.
        double IDF = getIDFScore(totalDocuments);
        for(Map.Entry<Integer,TFDocument> entry : relatedDocuments.entrySet()){
            entry.getValue().calculateTFIDfScore(IDF);
        }
        TFIDFCalculated = true;
        return relatedDocuments;
    }
    
    /**
    * Calculates the IDF score of the term.
    */
    public double getIDFScore(double documentsNumber){
        if(IDFScore != null){
            return IDFScore;
        }
        IDFScore = Math.log10(documentsNumber/(double)relatedDocuments.size());
        return IDFScore;
    }
}
