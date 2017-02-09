package models.authors;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class PopularAuthors {

    @SerializedName("popularAuthors")
    private ArrayList<AuthorGroup> authorGroup;

    public ArrayList<AuthorGroup> getAuthorGroup() {
        return authorGroup;
    }

    public void setAuthorGroup(ArrayList<AuthorGroup> authorGroup) {
        this.authorGroup = authorGroup;
    }
}
