package listeners;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import activities.dashboard.Dashboard;
import activities.dashboard.DashboardFragment;
import adapters.AuthorsAdapter;
import adapters.DashboardPagerAdapter;
import digitalbath.quotetab.R;
import helpers.main.AppHelper;
import helpers.main.ReadAndWriteToFile;
import models.authors.Author;
import models.authors.AuthorDetails;

/**
 * Created by Spaja on 30-Jan-17.
 */

public class OnFavoriteAuthorClickListener implements View.OnClickListener {

    private Context context;
    private Author author;
    private ImageView favoriteIcon;
    private AuthorsAdapter adapter;
    private ArrayList<Author> favoriteAuthors;
    private boolean isFavorites;
    private RecyclerView recyclerView;
    private RelativeLayout emptyList;
//    private DashboardPagerAdapter pagerAdapter;

    public OnFavoriteAuthorClickListener(Context context, Author author, ArrayList<Author> favoriteAuthors,
                                         ImageView favoriteIcon, AuthorsAdapter adapter, boolean isFavorites,
                                         RecyclerView recyclerView, RelativeLayout emptyList) {

        this.context = context;
        this.author = author;
        this.favoriteIcon = favoriteIcon;
        this.adapter = adapter;
        this.favoriteAuthors = favoriteAuthors;
        this.isFavorites = isFavorites;
        this.recyclerView = recyclerView;
        this.emptyList = emptyList;
        //this.pagerAdapter = pagerAdapter;
    }

    @Override
    public void onClick(View view) {

        int position = 0;

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (author.getAuthorId().equals(favoriteAuthors.get(i).getAuthorId())) {

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
                    adapter.expandToolbar();
                    recyclerView.setVisibility(View.GONE);
                    emptyList.setVisibility(View.VISIBLE);
                    emptyList.startAnimation(AppHelper.getAnimationUp(context));

                }
            }

            ReadAndWriteToFile.removeAuthorFromFavorites(context, author.getAuthorId());

            if (context instanceof Dashboard) {

                ((Dashboard) context).refreshFragmentData(ReadAndWriteToFile.getFavoriteAuthors(context),
                        ReadAndWriteToFile.getFavoriteQuotes(context));
            }

        } else {

            favoriteIcon.setImageResource(R.drawable.ic_author);
            author.setFavorite(true);
            ReadAndWriteToFile.addAuthorToFavorites(context, author);

            if (context instanceof Dashboard) {

                ((Dashboard) context).refreshFragmentData(ReadAndWriteToFile.getFavoriteAuthors(context),
                        ReadAndWriteToFile.getFavoriteQuotes(context));
            }
        }
    }
}
