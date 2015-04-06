package coding.p2;

/**
 * Represents a Kobo's Author and Title row.
 * 
 * @author Patricio
 */
public class Document {
    int id;
    String title;
    String author;
    int numberWords;

    public Document(int id, String title, String author) {
        this.id = id;
        this.title = title.trim();
        this.author = author.trim();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    
    public String getRecord(){
        return this.title + " " + this.author;
    }

    public int getNumberWords() {
        return numberWords;
    }

    public void setNumberWords(int numberWords) {
        this.numberWords = numberWords;
    }
}
