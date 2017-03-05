package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import models.authors.AuthorDetailsFromQuote;

/**
 * Created by Spaja on 05-Jan-17.
 */
public class QuoteReference implements Serializable {

    private int imageId;

    @SerializedName("_id")
    private String quoteId;

    @SerializedName("fields")
    private QuoteFields quoteDetails;

    @SerializedName("_source")
    private AuthorDetailsFromQuote author;

    public void setAuthor(AuthorDetailsFromQuote author) {
        this.author = author;
    }

    public QuoteReference() {
    }

    public QuoteReference(int imageId, QuoteFields quoteDetails) {
        this.imageId = imageId;
        this.quoteDetails = quoteDetails;
    }

    public AuthorDetailsFromQuote getAuthor() {
        return author;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public QuoteFields getQuoteDetails() {
        return quoteDetails;
    }

}
