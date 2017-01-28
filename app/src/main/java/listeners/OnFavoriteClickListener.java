package listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import adapters.QuotesByTagAdapter;
import digitalbath.quotes.FavoriteQuotes;
import digitalbath.quotes.QuotesByTag;
import digitalbath.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnFavoriteClickListener implements View.OnClickListener {

    private Context context;
    private Quote quote;
    private ImageView favoriteIcon;
    private int position;

    public OnFavoriteClickListener(Context context, Quote quote, ImageView favoriteIcon, int position) {

        this.context = context;
        this.quote = quote;
        this.favoriteIcon = favoriteIcon;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        if (quote.isFavorite()) {
            quote.setFavorite(false);
            favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);
        } else {
            quote.setFavorite(true);
            favoriteIcon.setImageResource(R.drawable.ic_favorite);
        }
        ReadAndWriteToFile.addFavoriteQuotes(context, quote, position);
    }
}

