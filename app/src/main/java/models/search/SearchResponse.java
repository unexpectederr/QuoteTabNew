package models.search;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import models.authors.AuthorDetails;
import models.quotes.Quote;

/**
 * Created by Spaja on 22-Feb-17.
 */

public class SearchResponse {

    @SerializedName("authors")
    private ArrayList<AuthorDetails> authors;

    @SerializedName("quotes")
    private ArrayList<Quote> quotes;

    @SerializedName("query")
    private String query;

    public ArrayList<AuthorDetails> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<AuthorDetails> authors) {
        this.authors = authors;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public String getQuery() {
        return query;
    }
}
