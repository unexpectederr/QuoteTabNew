package listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import activities.quotes.FavoriteQuotes;
import activities.quotes.QuotesByTag;
import helpers.main.Constants;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnTagClickListener implements View.OnClickListener {

    private Context context;
    private String quoteTag;
    private boolean isFavorites;

    public OnTagClickListener(Context context, String quoteTag, boolean isFavorites) {
        this.context = context;
        this.quoteTag = quoteTag;
        this.isFavorites = isFavorites;
    }

    @Override
    public void onClick(View v) {
        if (isFavorites) {
            Intent i = new Intent(context, QuotesByTag.class);
            i.putExtra(Constants.QUOTE_TAG, quoteTag);
            ((FavoriteQuotes) context).startActivityForResult(i, 1);

        } else {
            Intent i = new Intent(context, QuotesByTag.class);
            i.putExtra(Constants.QUOTE_TAG, quoteTag);
            context.startActivity(i);
        }
    }
}
