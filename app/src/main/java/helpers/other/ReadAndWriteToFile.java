package helpers.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import helpers.main.Constants;
import models.quotes.Quote;

/**
 * Created by Spaja on 23-Jan-17.
 */

public class ReadAndWriteToFile {


    public static void addFavoriteQuotes(Context context, Quote quote, int position, RecyclerView.Adapter adapter) {

        ArrayList<Quote> quotes = getFavoriteQuotes(context);

        if (quotes.size() != 0) {

            if (quote.isFavorite()) {
                quotes.add(quote);
                adapter.notifyItemInserted(position);
            } else {
                quotes.remove(position);
                adapter.notifyItemRemoved(position);
            }
        } else {
            quotes.add(quote);
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
