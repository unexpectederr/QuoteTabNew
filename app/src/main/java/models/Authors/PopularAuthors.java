package models.Authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class PopularAuthors {

    @SerializedName("popularAuthors")
    private ArrayList<Author> popularAuthors;

    public ArrayList<Author> getPopularAuthors() {
        return popularAuthors;
    }

}
