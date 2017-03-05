package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 19-Jan-17.
 */

public class TopQuotes {

    @SerializedName("quotes")
    private ArrayList<QuoteReference> quotes;

    public ArrayList<QuoteReference> getQuotes() {
        return quotes;
    }
}
