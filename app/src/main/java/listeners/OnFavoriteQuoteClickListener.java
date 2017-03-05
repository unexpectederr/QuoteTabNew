package listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import digitalbath.quotetab.R;
import helpers.main.ReadAndWriteToFile;
import models.quotes.Quote;
import models.quotes.QuoteReference;

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
    private RecyclerView recyclerView;
    private RelativeLayout emptyList;

    public OnFavoriteQuoteClickListener(Context context, ArrayList<Quote> favoriteQuotes, ImageView favoriteIcon,
                                        Quote quote, QuotesAdapter adapter, boolean isFavorites,
                                        RecyclerView recyclerView, RelativeLayout emptyList) {

        this.context = context;
        this.favoriteQuotes = favoriteQuotes;
        this.favoriteIcon = favoriteIcon;
        this.quote = quote;
        this.adapter = adapter;
        this.isFavorites = isFavorites;
        this.recyclerView = recyclerView;
        this.emptyList = emptyList;
    }

    @Override
    public void onClick(View v) {

        int position = 0;

        for (int i = 0; favoriteQuotes.size() > i; i++) {

            if (favoriteQuotes.get(i) != null && favoriteQuotes.get(i).getQuoteId()
                    .equals(quote.getQuoteId())) {

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
                if (adapter.getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    emptyList.setVisibility(View.VISIBLE);
                }
            }

            ReadAndWriteToFile.removeQuoteFromFavorites(context, quote.getQuoteId());

        } else {

            quote.setFavorite(true);
            favoriteIcon.setImageResource(R.drawable.ic_favorite);

            ReadAndWriteToFile.addQuoteToFavorites(context, quote);



        }
    }
}

