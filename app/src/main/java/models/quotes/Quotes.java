package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import models.authors.AuthorDetailsFromQuote;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class Quotes {

    @SerializedName("quotes")
    private ArrayList<Quote> quotes;

    @SerializedName("authorDetails")
    private AuthorDetailsFromQuote authorDetailsFromQuote;

    public AuthorDetailsFromQuote getAuthorDetailsFromQuote() {
        return authorDetailsFromQuote;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
}
