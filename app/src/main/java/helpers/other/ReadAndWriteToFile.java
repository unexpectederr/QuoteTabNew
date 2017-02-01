package helpers.other;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import helpers.main.Constants;
import models.authors.AuthorDetails;
import models.quotes.Quote;

/**
 * Created by Spaja on 23-Jan-17.
 */

public class ReadAndWriteToFile {

    //region QUOTES
    public static void addQuoteToFavorites(Context context, Quote quote) {

        ArrayList<Quote> quotes = getFavoriteQuotes(context);
        quotes.add(0, quote);

        try {
            FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME_QUOTES, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(quotes);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeQuoteFromFavorites(Context context, String quoteId) {

        ArrayList<Quote> quotes = getFavoriteQuotes(context);

        for (int i = 0; quotes.size() > i; i++) {
            if (quotes.get(i).getQuoteDetails().getQuoteId().equals(quoteId))
                quotes.remove(i);
        }

        try {
            FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME_QUOTES, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(quotes);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Quote> getFavoriteQuotes(Context context) {

        FileInputStream fis;
        ArrayList<Quote> quotes = new ArrayList<>();

        try {
            fis = context.openFileInput(Constants.FILE_NAME_QUOTES);
            ObjectInputStream ois = new ObjectInputStream(fis);
            quotes = (ArrayList<Quote>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return quotes;
    }
    //endregion

    //region AUTHORS
    public static void addAuthorToFavorites(Context context, AuthorDetails author) {

        ArrayList<AuthorDetails> favoriteAuthors = getFavoriteAuthors(context);
        favoriteAuthors.add(0, author);

        try {
            FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME_AUTHORS, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(favoriteAuthors);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeAuthorFromFavorites(Context context, String authorId) {

        ArrayList<AuthorDetails> favoriteAuthors = getFavoriteAuthors(context);

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (favoriteAuthors.get(i).getId().equals(authorId))
                favoriteAuthors.remove(i);
        }

        try {
            FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME_AUTHORS, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(favoriteAuthors);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<AuthorDetails> getFavoriteAuthors(Context context) {

        FileInputStream fis;
        ArrayList<AuthorDetails> authors = new ArrayList<>();

        try {
            fis = context.openFileInput(Constants.FILE_NAME_AUTHORS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            authors = (ArrayList<AuthorDetails>) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return authors;

    }
    //endregion

}
