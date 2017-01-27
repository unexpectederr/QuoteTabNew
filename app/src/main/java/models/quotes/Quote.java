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
//   // private int quoteIndex;
//
//  //  public int getQuoteIndex() {
//        return quoteIndex;
//    }
//
//    public void setQuoteIndex(int quoteIndex) {
//        this.quoteIndex = quoteIndex;
//    }

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
