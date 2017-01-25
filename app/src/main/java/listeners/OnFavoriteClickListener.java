package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import digitalbath.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnFavoriteClickListener implements View.OnClickListener {

    private Context context;
    private Quote quote;
    ImageView favoriteIcon;

    public OnFavoriteClickListener(Context context, Quote quote, ImageView favoriteIcon) {

        this.context = context;
        this.quote = quote;
        this.favoriteIcon = favoriteIcon;
    }

    @Override
    public void onClick(View v) {
        try {
            if (quote.isFavorite()) {
                quote.setFavorite(false);
                favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);
            } else {
                quote.setFavorite(true);
                favoriteIcon.setImageResource(R.drawable.ic_favorite);
            }
            ReadAndWriteToFile.writeToFile(context, quote);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

