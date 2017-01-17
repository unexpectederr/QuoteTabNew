package listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import digitalbath.quotes.QuotesByTag;
import helpers.Constants;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnTagClickListener implements View.OnClickListener {

    private Context context;
    private String quoteTag;

    public OnTagClickListener(Context context, String quoteTag) {
        this.context = context;
        this.quoteTag = quoteTag;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(context, QuotesByTag.class);
        i.putExtra(Constants.QUOTE_TAG, quoteTag);
        context.startActivity(i);
    }
}
