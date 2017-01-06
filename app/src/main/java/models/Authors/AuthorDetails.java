package models.authors;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Spaja on 26-Dec-16.
 */
public class AuthorDetails {

    @SerializedName("_id")
    private String id;

    @SerializedName("fields")
    private AuthorFields authorFields;

    public AuthorFields getAuthorFields() {
        return authorFields;
    }

    public String getId() {
        return id;
    }
}
