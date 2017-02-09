package models.topics;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 17-Jan-17.
 */
public class Topic {

    int imageId;

    @SerializedName("_source")
    private Source source;

    public Source getSource() {
        return source;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
