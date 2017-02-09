package models.authors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 09-Jan-17.
 */
public class AuthorFieldsFromQuote {

    @SerializedName("birthplace")
    private String birthplace;

    @SerializedName("country")
    private String country;

    @SerializedName("profession")
    private Profession profession;

    @SerializedName("name")
    private String authorName;

    @SerializedName("imageUrl")
    private String authorImageUrl;

    @SerializedName("description")
    private String description;

    @SerializedName("bornDay")
    private String bornDay;

    @SerializedName("bornMonth")
    private String bornMonth;

    @SerializedName("bornYear")
    private String bornYear;

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("source")
    private String wikipediaUrl;

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getBornDay() {
        return bornDay;
    }

    public String getBornMonth() {
        return bornMonth;
    }

    public String getBornYear() {
        return bornYear;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Profession getProfession() {
        return profession;
    }

    public String getCountry() {
        return country;
    }

    public String getBirthplace() {
        return birthplace;
    }


}
