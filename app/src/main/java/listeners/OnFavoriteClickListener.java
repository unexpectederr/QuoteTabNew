package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import digitalbath.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnFavoriteClickListener implements View.OnClickListener {

    private Context context;
    private ArrayList<Quote> quotes;
    private ImageView favoriteIcon;
    private String quoteId;
    private QuotesAdapter adapter;
    private boolean isFavorites;

    public OnFavoriteClickListener(Context context, ArrayList<Quote> quotes, ImageView favoriteIcon,
                                   String quoteId, QuotesAdapter adapter, boolean isFavorites) {

        this.context = context;
        this.quotes = quotes;
        this.favoriteIcon = favoriteIcon;
        this.quoteId = quoteId;
        this.adapter = adapter;
        this.isFavorites = isFavorites;
    }

    @Override
    public void onClick(View v) {

        int position = 0;

        for (int i = 0; quotes.size() > i; i++) {
            if (quotes.get(i).getQuoteDetails() != null &&
                    quotes.get(i).getQuoteDetails().getQuoteId().equals(quoteId)) {
                position = i;
                break;
            }
        }

        if (quotes.get(position).isFavorite()) {

            quotes.get(position).setFavorite(false);
            favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);

            if (isFavorites) {
                quotes.remove(position);
                adapter.notifyItemRemoved(position);
            }

            ReadAndWriteToFile.removeQuoteFromFavorites(context, quoteId);

        } else {

            quotes.get(position).setFavorite(true);
            favoriteIcon.setImageResource(R.drawable.ic_favorite);

            ReadAndWriteToFile.addQuoteToFavorites(context, quotes.get(position));
        }
    }
}

