package models.Authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 26-Dec-16.
 */
public class Fields {

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
        return imageUrl.get(0);
    }

    public String getName() {
        return name.get(0);
    }

    public String getProfessionName() {
        return professionName.get(0);
    }

    public int getQuotesCount() {
        return quotesCount.get(0);
    }
}

