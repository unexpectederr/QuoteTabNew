package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class QuoteFields implements Serializable{


    @SerializedName("author.authorId")
    private ArrayList<String> authorId;

    @SerializedName("author.imageUrl")
    private ArrayList<String> authorImageUrl;

    @SerializedName("author.name")
    private ArrayList<String> authorName;

    @SerializedName("author.country")
    private ArrayList<String> authorCountry;

    @SerializedName("author.nationality.name")
    private ArrayList<String> authorNationality;

    @SerializedName("author.profession.name")
    private ArrayList<String> authorProfession;

    @SerializedName("imageUrl")
    private ArrayList<String> quoteImageUrl;

    @SerializedName("text")
    private ArrayList<String> quoteText;

    @SerializedName("photoId")
    private String photoId;

    @SerializedName("categories")
    private ArrayList<String> categories;

    private String thumbnailUrl;

    @SerializedName("quoteId")
    private ArrayList<String> quoteId;

    public void setAuthorCountry(String authorCountry) {
        this.authorCountry = new ArrayList<>();
        this.authorCountry.add(authorCountry);
    }

    public void setAuthorId(String authorId) {
        this.authorId = new ArrayList<>();
        this.authorId.add(authorId);
    }

    public void setAuthorName(String authorName) {
        this.authorName = new ArrayList<>();
        this.authorName.add(authorName);
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = new ArrayList<>();
        this.quoteId.add(quoteId);
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = new ArrayList<>();
        this.quoteText.add(quoteText);
    }

    public String getQuoteId() {
         return quoteId.get(0);
    }

    public String getAuthorId() {
        return authorId.get(0);
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getCategories() {
        return categories.get(0);
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getAuthorCountry() {
        return authorCountry.get(0);
    }

    public String getAuthorImageUrl() {
        return authorImageUrl.get(0);
    }

    public String getAuthorName() {
        return authorName.get(0);
    }

    public String getAuthorNationality() {
        return authorNationality.get(0);
    }

    public String getAuthorProfession() {
        return authorProfession.get(0);
    }

    public String getQuoteImageUrl() {
        if (quoteImageUrl != null)
        return quoteImageUrl.get(0);
        return "";
    }

    public String getQuoteText() {
        return quoteText.get(0);
    }
}
