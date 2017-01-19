package models.quotes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Spaja on 19-Jan-17.
 */

public class TopAuthor {

    @SerializedName("name")
    private String authorName;

    @SerializedName("authorId")
    private String authorId;

    @SerializedName("imageUrl")
    private String authorImageUrl;

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }
}
