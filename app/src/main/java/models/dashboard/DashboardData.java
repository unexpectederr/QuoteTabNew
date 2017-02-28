package models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import models.authors.AuthorDetails;
import models.quotes.Quote;


public class DashboardData {

    @SerializedName("popularAuthorsList")
    private ArrayList<PopularAuthor> popularAuthors;

    @SerializedName("quotesPartial")
    private ArrayList<Quote> quotesPartial;

    @SerializedName("todaysBirthdays")
    private ArrayList<AuthorDetails> todaysBirthdays;

    @SerializedName("trendingAuthors")
    private ArrayList<AuthorDetails> trendingAuthors;

    public ArrayList<AuthorDetails> getTrendingAuthors() {
        return trendingAuthors;
    }

    public ArrayList<AuthorDetails> getTodaysBirthdays() {
        return todaysBirthdays;
    }

    public ArrayList<Quote> getQuotesPartial() {
        return quotesPartial;
    }

    public void setQuotesPartial(ArrayList<Quote> quotesPartial) {
        this.quotesPartial = quotesPartial;
    }

    public ArrayList<PopularAuthor> getPopularAuthors() {
        return popularAuthors;
    }
}
