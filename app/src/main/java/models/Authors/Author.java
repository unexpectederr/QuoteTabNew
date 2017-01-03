package models.authors;

import java.util.ArrayList;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class Author {

    private ArrayList<Results> results;
    private References references;

    public References getReferences() {
        return references;
    }

    public ArrayList<Results> getResults() {
        return results;
    }
}
