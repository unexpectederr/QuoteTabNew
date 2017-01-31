package models.authors;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Spaja on 26-Dec-16.
 */
public class AuthorDetails implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("fields")
    private AuthorFields authorFields;

    private boolean favorite;

    public AuthorFields getAuthorFields() {
        return authorFields;
    }

    public String getId() {
        return id;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
