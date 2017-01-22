package models.quotes;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class QuoteFields {


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
