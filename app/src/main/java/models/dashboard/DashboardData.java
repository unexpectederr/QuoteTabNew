package models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import models.quotes.Quote;


public class DashboardData {

    @SerializedName("popularAuthorsList")
    List<PopularAuthor> popularAuthors;

    @SerializedName("topPhotos")
    List<TopPhotos> topPhotos;

    @SerializedName("quotesPartial")
    private ArrayList<Quote> quotesPartial;

    public ArrayList<Quote> getQuotesPartial() {
        return quotesPartial;
    }

    public void setQuotesPartial(ArrayList<Quote> quotesPartial) {
        this.quotesPartial = quotesPartial;
    }

    public List<PopularAuthor> getPopularAuthors() {
        return popularAuthors;
    }

    public List<TopPhotos> getTopPhotos() {
        return topPhotos;
    }
}
