package models.quotes;

import java.io.Serializable;

import models.authors.Author;

/**
 * Created by unexpected_err on 03/03/2017.
 */

public class Quote implements Serializable {

    private String quoteId;
    private String quoteText;
    private String categories;
    private int imageId;
    private boolean favorite;
    private Author author;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getCategories() {
        if (categories != null) {
            if (!categories.equals("")) {
                return categories;
            } else {
                return "inspirational";
            }
        } else {
            return "inspirational";
        }
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
