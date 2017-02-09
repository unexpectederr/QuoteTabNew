package models.dashboard;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by unexpected_err on 20/01/2017.
 */

public class TopPhotos implements Serializable {

    @SerializedName("_source")
    private Source source;

    private boolean favorite;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Source getSource() {
        return source;
    }
}
