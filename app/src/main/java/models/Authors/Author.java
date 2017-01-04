package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class Author {

    @SerializedName("results")
    private ArrayList<AuthorDetails> authors;

    @SerializedName("references")
    private References references;

    public References getReferences() {
        return references;
    }

    public ArrayList<AuthorDetails> getAuthors() {
        return authors;
    }
}
