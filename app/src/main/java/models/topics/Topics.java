package models.topics;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class Topics {

    @SerializedName("topics")
    private ArrayList<Topic> topics;

    @SerializedName("pageMetadata")
    private PageMetaData pageMetaData;

    public PageMetaData getPageMetaData() {
        return pageMetaData;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }
}
