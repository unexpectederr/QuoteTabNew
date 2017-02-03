package models.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import helpers.main.Constants;


public class DashboardItem implements Serializable {

    private int dashItemId = new Random().nextInt(Constants.NUMBER_OF_COVERS);

    private String quote;
    private String author;
    private String authorId;
    private String quoteId;
    private ArrayList<String> categories;
    private boolean favorite;



    public DashboardItem(String quote, String author, String authorId, String quoteId, boolean favorite) {
        this.favorite = favorite;
        this.quote = quote;
        this.author = author;
        this.authorId = authorId;
        this.quoteId = quoteId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getQuoteId(){
        return quoteId;
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
