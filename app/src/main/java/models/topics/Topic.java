package models.topics;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class Topic {

    @SerializedName("_source")
    private Source source;

    public Source getSource() {
        return source;
    }
}
