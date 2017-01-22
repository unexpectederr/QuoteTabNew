package listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import digitalbath.quotes.QuotesByAuthor;
import helpers.main.Constants;

/**
 * Created by Spaja on 22-Jan-17.
 */

public class OnAuthorClickListener implements View.OnClickListener {

    String authorId;
    Context context;

    public OnAuthorClickListener(Context context, String authorId) {
        this.authorId = authorId;
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(context, QuotesByAuthor.class);
        i.putExtra(Constants.AUTHOR_ID, authorId);
        context.startActivity(i);
    }
}
