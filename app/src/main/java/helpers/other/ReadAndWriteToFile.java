package helpers.other;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import adapters.QuotesAdapter;
import digitalbath.quotes.FavoriteQuotes;
import helpers.main.Constants;
import models.quotes.Quote;

/**
 * Created by Spaja on 23-Jan-17.
 */

public class ReadAndWriteToFile {


    public static void addQuoteToFavorites(Context context, Quote quote) {

        ArrayList<Quote> quotes = getFavoriteQuotes(context);
        quotes.add(quote);

        try {
            FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE);
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
            FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE);
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
            fis = context.openFileInput(Constants.FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            quotes = (ArrayList<Quote>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return quotes;
    }
}
