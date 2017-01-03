package models.authors;

import java.util.ArrayList;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class Authors {

    private ArrayList<Author> popularAuthors;
    private ArrayList<String> lettersFilters;

    public ArrayList<String> getLettersFilters() {
        return lettersFilters;
    }

    public ArrayList<Author> getPopularAuthors() {
        return popularAuthors;
    }
}
