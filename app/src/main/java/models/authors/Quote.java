package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Random;

import helpers.Constants;

/**
 * Created by Spaja on 05-Jan-17.
 */
public class Quote {

    int quoteId = new Random().nextInt(Constants.NUMBER_OF_COVERS);

    @SerializedName("fields")
    private QuoteFields quoteDetails;

    public QuoteFields getQuoteDetails() {
        return quoteDetails;
    }

    public int getQuoteId() {
        return quoteId;
    }
}
