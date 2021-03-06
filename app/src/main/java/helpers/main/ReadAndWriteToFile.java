package helpers.main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

import models.authors.Author;
import models.authors.AuthorDetails;
import models.quotes.Quote;
import models.quotes.QuoteReference;

/**
 * Created by Spaja on 23-Jan-17.
 */

public class ReadAndWriteToFile {

    private static int imageIndex = 0;

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
            if (quotes.get(i).getQuoteId().equals(quoteId))
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

    public static void writeQuoteImageToCache(Context context, Bitmap bm) {

        FileOutputStream stream = null;

        try {

            File cachePath = new File(context.getCacheDir(), "images");

            if (!cachePath.exists()) {
                cachePath.mkdirs();
            }

            stream = new FileOutputStream(cachePath + "/image.jpg");
            bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region AUTHORS
    public static void addAuthorToFavorites(Context context, Author author) {

        ArrayList<Author> favoriteAuthors = getFavoriteAuthors(context);
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

        ArrayList<Author> favoriteAuthors = getFavoriteAuthors(context);

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (favoriteAuthors.get(i).getAuthorId().equals(authorId))
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

    public static ArrayList<Author> getFavoriteAuthors(Context context) {

        FileInputStream fis;
        ArrayList<Author> authors = new ArrayList<>();

        try {
            fis = context.openFileInput(Constants.FILE_NAME_AUTHORS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            authors = (ArrayList<Author>) ois.readObject();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return authors;

    }

    public static void saveImage(Bitmap bitmap, final Context context) {

        File imagePath = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES) + File.separator + "QuoteTab" + File.separator);

        if (!imagePath.exists()) {
            imagePath.mkdir();
        }

        OutputStream fOut = null;
        File file = new File(imagePath, "Quote_" + System.currentTimeMillis() + ".jpg");

        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);

        try {
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Quote");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Description");
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
        values.put("_data", file.getAbsolutePath());

        ContentResolver cr = context.getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }
    //endregion
}
