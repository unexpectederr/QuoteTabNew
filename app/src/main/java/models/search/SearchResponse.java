package models.search;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import helpers.main.Mapper;
import models.authors.Author;
import models.authors.AuthorDetails;
import models.quotes.Quote;
import models.quotes.QuoteReference;

/**
 * Created by Spaja on 22-Feb-17.
 */

public class SearchResponse {

    @SerializedName("authors")
    private ArrayList<AuthorDetails> authors;

    @SerializedName("quotes")
    private ArrayList<QuoteReference> quotes;

    @SerializedName("query")
    private String query;

    public ArrayList<Author> getAuthors() {
        return Mapper.mapAuthors(authors);
    }

    public ArrayList<Quote> getQuotes() {
        return Mapper.mapQuotes(quotes);
    }

    public String getQuery() {
        return query;
    }
}
