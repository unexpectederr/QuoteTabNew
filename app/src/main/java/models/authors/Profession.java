package models.authors;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Spaja on 09-Jan-17.
 */
public class Profession implements Serializable {

    @SerializedName("name")
    private String professionName;

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName;
    }
}

