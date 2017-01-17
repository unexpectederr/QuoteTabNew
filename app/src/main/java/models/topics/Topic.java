package models.topics;

import com.google.gson.annotations.SerializedName;

import java.util.Random;

import helpers.main.Constants;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class Topic {

    int topicId = new Random().nextInt(Constants.NUMBER_OF_COVERS);

    @SerializedName("_source")
    private Source source;

    public Source getSource() {
        return source;
    }

    public int getTopicId() {
        return topicId;
    }
}
