package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

import helpers.main.Constants;

/**
 * Created by Spaja on 05-Jan-17.
 */
public class Quote {

    int imageId;

    @SerializedName("fields")
    private QuoteFields quoteDetails;

    public QuoteFields getQuoteDetails() {
        return quoteDetails;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
