package listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import adapters.AuthorsAdapter;
import digitalbath.quotetab.R;
import helpers.main.ReadAndWriteToFile;
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
    private RecyclerView recyclerView;
    private RelativeLayout emptyList;

    public OnFavoriteAuthorClickListener(Context context, AuthorDetails author, ArrayList<AuthorDetails>
            favoriteAuthors, ImageView favoriteIcon, AuthorsAdapter adapter, boolean isFavorites,
                                         RecyclerView recyclerView, RelativeLayout emptyList) {

        this.context = context;
        this.author = author;
        this.favoriteIcon = favoriteIcon;
        this.adapter = adapter;
        this.favoriteAuthors = favoriteAuthors;
        this.isFavorites = isFavorites;
        this.recyclerView = recyclerView;
        this.emptyList = emptyList;
    }

    @Override
    public void onClick(View view) {

        int position = 0;

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (author.getAuthorFields().getAuthorId().equals(favoriteAuthors.get(i).getAuthorFields().getAuthorId())) {

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
                if (adapter.getItemCount() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    emptyList.setVisibility(View.VISIBLE);
                }
            }

            ReadAndWriteToFile.removeAuthorFromFavorites(context, author.getAuthorFields().getAuthorId());

        } else {

            favoriteIcon.setImageResource(R.drawable.ic_author);
            author.setFavorite(true);
            ReadAndWriteToFile.addAuthorToFavorites(context, author);
        }

    }
}
