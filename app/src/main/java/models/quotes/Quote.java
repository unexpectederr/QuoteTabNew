package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import models.authors.AuthorDetailsFromQuote;

/**
 * Created by Spaja on 05-Jan-17.
 */
public class Quote implements Serializable {

    private int imageId;
    private boolean favorite;

    @SerializedName("_id")
    private String quoteId;

    @SerializedName("fields")
    private QuoteFields quoteDetails;

    @SerializedName("_source")
    private AuthorDetailsFromQuote author;

    public void setAuthor(AuthorDetailsFromQuote author) {
        this.author = author;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public Quote() {
    }

    public Quote(boolean favorite, int imageId, QuoteFields quoteDetails) {
        this.favorite = favorite;
        this.imageId = imageId;
        this.quoteDetails = quoteDetails;
    }

    public AuthorDetailsFromQuote getAuthor() {
        return author;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public QuoteFields getQuoteDetails() {
        return quoteDetails;
    }

    public void setQuoteDetails(QuoteFields quoteDetails) {
        this.quoteDetails = quoteDetails;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (imageId != quote.imageId) return false;
        if (favorite != quote.favorite) return false;
        return quoteDetails.equals(quote.quoteDetails);

    }

    @Override
    public int hashCode() {
        int result = imageId;
        result = 31 * result + (favorite ? 1 : 0);
        result = 31 * result + quoteDetails.hashCode();
        return result;
    }
}
