package models.authors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 09-Jan-17.
 */
public class Profession {

    @SerializedName("name")
    private String professionName;

    public String getProfessionName() {
        return professionName;
    }
}

