package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Spaja on 02-Feb-17.
 */

public class Authors {

    @SerializedName("authors")
    ArrayList<AuthorDetails> authors;

    public ArrayList<AuthorDetails> getAuthors() {
        return authors;
    }
}
