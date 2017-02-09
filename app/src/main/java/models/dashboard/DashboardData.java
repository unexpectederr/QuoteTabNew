package models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import models.quotes.Quote;


public class DashboardData {

    @SerializedName("popularAuthorsList")
    ArrayList<PopularAuthor> popularAuthors;

    @SerializedName("topPhotos")
    ArrayList<TopPhotos> topPhotos;

    @SerializedName("quotesPartial")
    private ArrayList<Quote> quotesPartial;

    public ArrayList<Quote> getQuotesPartial() {
        return quotesPartial;
    }

    public void setQuotesPartial(ArrayList<Quote> quotesPartial) {
        this.quotesPartial = quotesPartial;
    }

    public ArrayList<PopularAuthor> getPopularAuthors() {
        return popularAuthors;
    }

    public ArrayList<TopPhotos> getTopPhotos() {
        return topPhotos;
    }
}
