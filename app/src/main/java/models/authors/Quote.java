package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 05-Jan-17.
 */
public class Quote {

    @SerializedName("fields")
    QuoteFields quoteDetails;

    public QuoteFields getQuoteDetails() {
        return quoteDetails;
    }
}
