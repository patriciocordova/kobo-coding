package coding.p2;

/**
 * Pairs a document and its TFIDFS score regarding a query.
 * 
 * @author Patricio
 */
public class ScoredDocument implements Comparable<ScoredDocument>{
    Document document;
    Double TFIDFScore;

    public ScoredDocument(Document document, Double TFIDFScore) {
        this.document = document;
        this.TFIDFScore = TFIDFScore;
    }
    
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Double getTFIDFScore() {
        return TFIDFScore;
    }

    public void setTFIDFScore(Double tfidfScore) {
        this.TFIDFScore = tfidfScore;
    }

    /**
    * ScoredDocument class implements the Comparable interface in order
    * to sort the query results according TF*IDF scores and document ID.
    */
    @Override
    public int compareTo(ScoredDocument o) {
        // Compare TF*IDF scores.
        if(TFIDFScore < o.getTFIDFScore()){
            return 1;
        }else{
            if(TFIDFScore > o.getTFIDFScore()){
                return -1;
            }else{
                
                // Compare IDs if both documents have the same TF*IDF score.
                if(document.getId() < o.getDocument().getId()){
                    return -1;
                }else{
                    if(document.getId() > o.getDocument().getId()){
                        return 1;
                    }else{
                        return 0;
                    }
                }
            }
        }
    }
}
