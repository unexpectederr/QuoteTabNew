package models.dashboard;

import java.io.Serializable;
import java.util.Random;

import helpers.main.Constants;


public class DashboardItem implements Serializable {

    int dashItemId = new Random().nextInt(Constants.NUMBER_OF_COVERS);

    private String quote;
    private String author;
    private String authorId;

    public DashboardItem(String quote, String author, String authorId) {
        this.quote = quote;
        this.author = author;
        this.authorId = authorId;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public int getDashItemId() {
        return dashItemId;
    }
}
