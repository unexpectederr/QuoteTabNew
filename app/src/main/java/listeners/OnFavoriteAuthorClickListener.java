package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import adapters.AuthorsAdapter;
import activities.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.authors.AuthorDetails;

/**
 * Created by Spaja on 30-Jan-17.
 */

public class OnFavoriteAuthorClickListener implements View.OnClickListener {

    private Context context;
    private AuthorDetails author;
    private ImageView favoriteIcon;
    private AuthorsAdapter adapter;
    private ArrayList<AuthorDetails> favoriteAuthors;
    private boolean isFavorites;

    public OnFavoriteAuthorClickListener(Context context, AuthorDetails author, ArrayList<AuthorDetails>
            favoriteAuthors, ImageView favoriteIcon, AuthorsAdapter adapter, boolean isFavorites) {

        this.context = context;
        this.author = author;
        this.favoriteIcon = favoriteIcon;
        this.adapter = adapter;
        this.favoriteAuthors = favoriteAuthors;
        this.isFavorites = isFavorites;
    }

    @Override
    public void onClick(View view) {

        int position = 0;

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (author.getId().equals(favoriteAuthors.get(i).getId())) {

                position = i;
                break;
            }

        }

        if (author.isFavorite()) {

            favoriteIcon.setImageResource(R.drawable.ic_author_empty);
            author.setFavorite(false);

            if (isFavorites) {

                favoriteAuthors.remove(position);
                adapter.notifyItemRemoved(position);
            }

            ReadAndWriteToFile.removeAuthorFromFavorites(context, author.getId());

        } else {

            favoriteIcon.setImageResource(R.drawable.ic_author);
            author.setFavorite(true);
            ReadAndWriteToFile.addAuthorToFavorites(context, author);
        }

    }
}
