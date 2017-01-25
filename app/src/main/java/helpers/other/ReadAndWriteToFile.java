package helpers.other;

import android.content.Context;

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

    public static ArrayList<Quote> quotes = new ArrayList<>();

    public static void writeToFile(Context context, Quote quote) throws IOException, ClassNotFoundException {

        quotes.addAll(readFromFile(context));
        if (quote.isFavorite())
            quotes.add(quote);
        else
            quotes.remove(quote);
        FileOutputStream fos = context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(quotes);
        oos.close();
    }

    public static ArrayList<Quote> readFromFile(Context context) {
        FileInputStream fis;
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
