package coding.p2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Problem 2 Solution.
 * 
 * Definitions
 * tf*idf ​– ​term frequency times inverse document frequency – read more here
 * http://en.wikipedia.org/wiki/Tf%E2%80%93idf
 * query ​­ a search query which can have multiple words / terms
 * term ​– a single word in a query
 * 
 * Requirements
 * - Given a set of Kobo’s Author and Title data, implement a tf*idf index in memory which does the following:
 * - Build an index from the given data file
 * - For a given query, output the top 10 results ranked by their tf*idf scores
 * - Case (capitalization) should be ignored
 * - You should take the union of the title and author fields when indexing the book records
 * - If the same term appears multiple times in a query or a book record, only count it once
 * - If multiple books have the same score, return sort them by id (lowest id first)
 * 
 * @author Patricio
 */
public class MainP2 {
    
    // Indexes if terms and douments.
    public static HashMap<String,Term> terms;
    public static SortedMap<Integer,Document> documents;
    
    public static void main(String[] args) {
        try {
            if(args.length < 1){
                System.err.println("Please, enter a filename."); return;
            }
            
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            String input;
            int documentCount = 0;
            terms = new HashMap<>();
            documents = new TreeMap<>();
            System.err.println("[DEBUG] Indexing documents:"); //DEBUG
            
            while ((input = br.readLine()) != null) {
                if(input.length() == 0) continue;
                indexDocument(turnIntoDocument(input, documentCount++));
                if(documentCount%100000 == 0) System.err.println("[DEBUG] "+ documentCount+ " documents indexed"); //DEBUG
            }
            
            System.err.println("[DEBUG] Indexed finished."); //DEBUG    
            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            while ((input = br2.readLine()) != null) {
                // Program finishes with an empty query
                if(input.length() == 0) break; 
                query(input);
            }
        } catch (IOException|IndexOutOfBoundsException e) {
            e.printStackTrace(System.err);
        }
    }
    
    
    /**
    * Takes a line received as an input and turns it into a Document with the
    * fields: id, title and author.
    *
    * @param  documentCount number of line in the input document. Used as a HACK for incomplete records.
    * @see         coding.p2.Document
    */
    public static Document turnIntoDocument(String line, int documentCount) throws IndexOutOfBoundsException{
        String[] columnValues = line.split("\t");
        // HACK: title_author.tab has a special strange character (ï»¿1) at the beginning so I had to manually set the document ID in the first line.
        int documentIndex;
        try{
            documentIndex = Integer.parseInt(columnValues[0]);
        }catch(NumberFormatException e){
            documentIndex = documentCount+1;
            System.err.println("Error while trying to cast ID of document: "+documentCount);
        }
        // HACK: Not all the documents had 3 fields, some only had 2.
        Document document = (columnValues.length < 3) 
                ? new Document(documentIndex, columnValues[1], "") 
                : new Document(documentIndex, columnValues[1], columnValues[2]);
        return document;
    }
    
    /**
    * Indexes a document itself and all its words.
    */
    public static void indexDocument(Document document) {
        /** - "You should take the union of the title and author fields"
         * Indexing a document as the union of title and author fields was too slow, used document ID instead.
         * If ID is a mutable attribute, the system could add its own numeric ID.
         */
        documents.put(document.getId(), document);
        // Assumed that the queries are focused only on words and numbers. Remove everything that is not a letter/number.
        String[] documentWords = document.getRecord().toLowerCase().replaceAll("[^a-z0-9]", " ").split("\\s+");
        // Index document words together with the ID of the document where they appeared.
        for (String word : documentWords) {  
            if(!terms.containsKey(word)){
                Term term = new Term(document);
                terms.put(word, term);
            }else{
                Term term = terms.get(word);
                term.increaseTermFrequency(document);
            }
        }
    }
    
    /**
    * Perform a query against the terms/documents indices.
    */
    public static void query(String query) {
        Map<Integer,ScoredDocument> queryResults = new HashMap<>();
        // Remove repeated words from query.
        String[] uniqueQueries = new HashSet<>(Arrays.asList(
                query.toLowerCase().trim().replaceAll("[^a-z0-9]", " ").split("\\s+"))
        ).toArray(new String[0]);
        // Get the documents TF*IDF scores.
        for(String queryTerm : uniqueQueries){
            Term term = terms.get(queryTerm);
            if(term != null){
                queryResults = updateQueryResults(term, queryResults);
            }
        }
        List<ScoredDocument> weightedDocuments = new ArrayList<>(queryResults.values());
        // Sort scored documents by score and id.
        Collections.sort(weightedDocuments);
        for (int i = 0; i < weightedDocuments.size() && i < 10; i++) {
            System.out.println(
                    weightedDocuments.get(i).getDocument().getId()
                    + " "
                    + weightedDocuments.get(i).getTFIDFScore()
                    + " title: "
                    + weightedDocuments.get(i).getDocument().getTitle()
                    + " author: "
                    + weightedDocuments.get(i).getDocument().getAuthor());
        }
    }
    
    /**
    * Supports the query function. Performs 2 tasks:
    * - Calculates all the TF*IDF indexes of the documents where the term appeared.
    * - Sums these TF*IDF scores to the ones belonging to another {w,d} pair
    * that has been found, to get the document's score regarding the query.
    * 
    * @param  term term in the query
    * @param  documentsFound HashMap that holds all the document where the words from the query have been found
    * @see         kobo.p2.Document.getTFIDFScoredDocuments()
    */
    public static Map<Integer, ScoredDocument> updateQueryResults(Term term, Map<Integer, ScoredDocument> documentsFound) {
        Map<Integer, TFDocument> documentsMap = term.getTFIDFScoredDocuments(documents.size());
        for (Map.Entry<Integer, TFDocument> document : documentsMap.entrySet()) {
            int documentId = document.getValue().getDocumentId();
            TFDocument tfDocument = document.getValue();
            if (!documentsFound.containsKey(documentId)) {
                ScoredDocument initial = new ScoredDocument(documents.get(documentId), tfDocument.getTFIDFScore());
                documentsFound.put(documentId, initial);
            } else {
                double previousScore = documentsFound.get(documentId).getTFIDFScore();
                double newScore = previousScore + tfDocument.getTFIDFScore();
                documentsFound.get(documentId).setTFIDFScore(newScore);
            }
        }
        return documentsFound;
    }
}
