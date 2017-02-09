package listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import activities.authors.AuthorWikipedia;

/**
 * Created by Spaja on 09-Feb-17.
 */

public class OnWikipediaButtonClickListener implements View.OnClickListener {

    private Context context;
    private String wikipediaUrl;
    private String authorName;

    public OnWikipediaButtonClickListener(Context context, String wikipediaUrl, String authorName) {
        this.context = context;
        this.wikipediaUrl = wikipediaUrl;
        this.authorName = authorName;
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(context, AuthorWikipedia.class);
        i.putExtra("URL", wikipediaUrl);
        i.putExtra("AUTHOR_NAME", authorName);
        context.startActivity(i);
    }
}
