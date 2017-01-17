package models.topics;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class Source {

    @SerializedName("name")
    private String topicName;

    @SerializedName("quotesCount")
    private int quotesCount;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuotesCount() {
        return quotesCount;
    }

    public String getTopicName() {
        return topicName;
    }
}
