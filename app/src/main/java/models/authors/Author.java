package models.authors;

import java.io.Serializable;

/**
 * Created by unexpected_err on 03/03/2017.
 */

public class Author implements Serializable {

    private String authorId;
    private String authorName;
    private String authorImageUrl;

    private int quotesCount;

    private String birthPlace;
    private String country;
    private String profession;
    private String description;
    private String bornDay;
    private String bornMonth;
    private String bornYear;
    private String wikipediaUrl;

    private boolean isFavorite;
    private boolean isLast;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public int getQuotesCount() {
        return quotesCount;
    }

    public void setQuotesCount(int quotesCount) {
        this.quotesCount = quotesCount;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBornDay() {
        return bornDay;
    }

    public void setBornDay(String bornDay) {
        this.bornDay = bornDay;
    }

    public String getBornMonth() {
        return bornMonth;
    }

    public void setBornMonth(String bornMonth) {
        this.bornMonth = bornMonth;
    }

    public String getBornYear() {
        return bornYear;
    }

    public void setBornYear(String bornYear) {
        this.bornYear = bornYear;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        this.isLast = last;
    }
}
