package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 26-Dec-16.
 */
public class AuthorFields {

    private ArrayList<String> name;
    private ArrayList<String> authorId;
    private ArrayList<Integer> quotesCount;
    private ArrayList<String> imageUrl;


    @SerializedName("profession.name")
    private ArrayList<String> professionName;

    public String getAuthorId() {
        return authorId.get(0);
    }

    public String getImageUrl() {
        if (imageUrl != null)
            return imageUrl.get(0);
        else
            return "";
    }

    public String getName() {
        return name.get(0);
    }

    public String getProfessionName() {
        if (professionName != null) {
            return professionName.get(0);
        } else {
            return "Unknown";
        }
    }

    public int getQuotesCount() {
        return quotesCount.get(0);
    }
}

