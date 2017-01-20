package models.dashboard;

import com.google.gson.annotations.SerializedName;

/**
 * Created by unexpected_err on 20/01/2017.
 */

public class TopPhotos {

    @SerializedName("_source")
    Source source;

    public Source getSource() {
        return source;
    }
}
