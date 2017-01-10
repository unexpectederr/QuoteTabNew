package models.authors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 09-Jan-17.
 */
public class AuthorDetailsFromQuote {

    @SerializedName("_source")
    private AuthorFieldsFromQuote authorFieldsFromQuote;

    public AuthorFieldsFromQuote getAuthorFieldsFromQuote() {
        return authorFieldsFromQuote;
    }
}
