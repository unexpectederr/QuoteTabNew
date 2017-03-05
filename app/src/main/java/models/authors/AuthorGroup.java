package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import helpers.main.Mapper;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class AuthorGroup {

    @SerializedName("results")
    private ArrayList<AuthorDetails> authors;

    @SerializedName("references")
    private References references;

    public References getReferences() {
        return references;
    }

    public ArrayList<Author> getAuthors() {
        return Mapper.mapAuthors(authors);
    }

    public void setAuthors(ArrayList<AuthorDetails> authors) {
        this.authors = authors;
    }

    public void setReferences(References references) {
        this.references = references;
    }
}
