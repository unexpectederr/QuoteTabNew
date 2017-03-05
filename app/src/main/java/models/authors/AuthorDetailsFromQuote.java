package models.authors;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Spaja on 09-Jan-17.
 */
public class AuthorDetailsFromQuote implements Serializable {

    @SerializedName("quoteId")
    private String quoteId;

    @SerializedName("text")
    private String quoteText;

    @SerializedName("author")
    private AuthorFieldsFromQuote author;

    @SerializedName("_source")
    private AuthorFieldsFromQuote authorFieldsFromQuote;

    public String getQuoteId() {
        return quoteId;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setAuthor(AuthorFieldsFromQuote author) {
        this.author = author;
    }

    public AuthorFieldsFromQuote getAuthor() {
        return author;
    }

    public AuthorFieldsFromQuote getAuthorFieldsFromQuote() {
        return authorFieldsFromQuote;
    }

}
