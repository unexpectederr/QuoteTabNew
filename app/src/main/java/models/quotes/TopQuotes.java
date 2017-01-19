package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 19-Jan-17.
 */

public class TopQuotes {

    @SerializedName("quotes")
    private ArrayList<Quote> quotes;

    @SerializedName("popularAuthorsList")
    private ArrayList<TopAuthor> popularAuthors;

    public ArrayList<TopAuthor> getPopularAuthors() {
        return popularAuthors;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
}
