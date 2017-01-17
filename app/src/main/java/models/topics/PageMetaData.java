package models.topics;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class PageMetaData {

    @SerializedName("relNext")
    private String relNext;

    public String getRelNext() {
        return relNext;
    }
}
