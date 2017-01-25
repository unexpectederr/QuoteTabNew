package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Random;

import helpers.main.Constants;

/**
 * Created by Spaja on 05-Jan-17.
 */
public class Quote implements Serializable {

    private int imageId;
    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

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
