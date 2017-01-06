package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class Quotes {

    @SerializedName("quotes")
    ArrayList<Quote> quotes;

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
}
