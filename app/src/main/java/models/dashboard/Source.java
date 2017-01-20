package models.dashboard;


/**
 * Created by unexpected_err on 20/01/2017.
 */

public class Source {

    String quoteId;
    String authorId;

    public String getQuote() {

        String quote = "";

        String[] array = quoteId.split("-");

        for (int i = 0; array.length > i; i++) {

            if (i == 0)
                quote += (array[i].substring(0, 1).toUpperCase() + array[i].substring(1).toLowerCase() + " ");
            else
                quote += (array[i] + " ");
        }

        return quote;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {

        String authorName = "";

        String[] array = authorId.split("-");

        for (int i = 0; array.length > i; i++)
            authorName += (array[i].substring(0, 1).toUpperCase() + array[i].substring(1).toLowerCase() + " ");

        return authorName;
    }
}
