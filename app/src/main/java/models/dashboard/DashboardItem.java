package models.dashboard;

import java.io.Serializable;


public class DashboardItem implements Serializable{

    private String quote;
    private String author;
    private String imageUrl;

    public DashboardItem(String quote, String author, String imageUrl) {
        this.quote = quote;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
