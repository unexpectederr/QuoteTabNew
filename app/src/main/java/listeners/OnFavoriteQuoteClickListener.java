package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import activities.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnFavoriteQuoteClickListener implements View.OnClickListener {

    private Context context;
    private ArrayList<Quote> favoriteQuotes;
    private ImageView favoriteIcon;
    private QuotesAdapter adapter;
    private boolean isFavorites;
    private Quote quote;

    public OnFavoriteQuoteClickListener(Context context, ArrayList<Quote> favoriteQuotes, ImageView favoriteIcon,
                                        Quote quote, QuotesAdapter adapter, boolean isFavorites) {

        this.context = context;
        this.favoriteQuotes = favoriteQuotes;
        this.favoriteIcon = favoriteIcon;
        this.quote = quote;
        this.adapter = adapter;
        this.isFavorites = isFavorites;
    }

    @Override
    public void onClick(View v) {

        int position = 0;

        for (int i = 0; favoriteQuotes.size() > i; i++) {

            if (favoriteQuotes.get(i).getQuoteDetails() != null &&
                    favoriteQuotes.get(i).getQuoteDetails().getQuoteId().equals(quote.getQuoteDetails().getQuoteId())) {

                position = i;
                break;

            }
        }

        if (quote.isFavorite()) {

            quote.setFavorite(false);
            favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);

            if (isFavorites) {

                favoriteQuotes.remove(position);
                adapter.notifyItemRemoved(position);

            }

            ReadAndWriteToFile.removeQuoteFromFavorites(context, quote.getQuoteDetails().getQuoteId());

        } else {

            quote.setFavorite(true);
            favoriteIcon.setImageResource(R.drawable.ic_favorite);

            ReadAndWriteToFile.addQuoteToFavorites(context, quote);



        }
    }
}

