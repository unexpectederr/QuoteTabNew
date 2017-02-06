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
