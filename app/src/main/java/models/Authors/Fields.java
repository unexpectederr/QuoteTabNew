package models.Authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 26-Dec-16.
 */
public class Fields {

    private ArrayList<String> name;
    private ArrayList<String> authorId;

    @SerializedName("profession.name")
    private
    ArrayList<String> professionName;

    private ArrayList<String> imageUrl;
        private ArrayList<Integer> quotesCount;

    public ArrayList<String> getAuthorId() {
        return authorId;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<String> getProfessionName() {
        return professionName;
    }

    public ArrayList<Integer> getQuotesCount() {
        return quotesCount;
    }
}

