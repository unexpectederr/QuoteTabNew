package models.authors;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Spaja on 09-Jan-17.
 */
public class AuthorFieldsFromQuote implements Serializable {


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

    @SerializedName("quotesCount")
    private int quotesCount;

    public int getQuotesCount() {
        return quotesCount;
    }

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

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setBornDay(String bornDay) {
        this.bornDay = bornDay;
    }

    public void setBornMonth(String bornMonth) {
        this.bornMonth = bornMonth;
    }

    public void setBornYear(String bornYear) {
        this.bornYear = bornYear;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setQuotesCount(int quotesCount) {
        this.quotesCount = quotesCount;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }
}
