package models.topics;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class Topics {

    @SerializedName("topics")
    private ArrayList<Topic> topics;

    public ArrayList<Topic> getTopics() {
        return topics;
    }
}
