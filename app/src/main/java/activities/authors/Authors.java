package activities.authors;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;

import java.util.ArrayList;

import activities.quotes.QuotesByTag;
import activities.quotetabnew.R;
import activities.topics.Topics;
import adapters.AuthorsAdapter;
import adapters.TopicsAdapter;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import models.authors.AuthorsByLetter;
import models.authors.AuthorDetails;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Authors extends AppCompatActivity {

    private boolean isByLetter;
    RecyclerView authorsRecyclerView;
    int page = 1, visibleItemCount, totalItemCount, pastVisibleItems;
    AuthorsAdapter adapter;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_authors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initializeContent();


    }

    private void initializeContent() {
        authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        authorsRecyclerView.setLayoutManager(manager);

        ArrayList<AuthorDetails> authors = ReadAndWriteToFile.getFavoriteAuthors(this);

        isByLetter = getIntent().getBooleanExtra("IS_BY_LETTER", false);
        final String letter = getIntent().getStringExtra("LETTER");

        if (isByLetter) {

            loadAuthors(letter, page);

            authorsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) {

                        visibleItemCount = manager.getChildCount();
                        totalItemCount = manager.getItemCount();
                        pastVisibleItems = manager.findFirstVisibleItemPosition();

                        if (!loading) {

                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                loading = true;
                                loadAuthors(letter, page);
                                //adapter.notifyItemRangeInserted(adapter.getItemCount(), adapter.getItemCount());
                                adapter.notifyDataSetChanged();
                                page++;
                            }
                        }
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        } else {

            AuthorsAdapter adapter = new AuthorsAdapter(this, authors, false);
            authorsRecyclerView.setAdapter(adapter);
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

    private void loadAuthors(String letter, int page) {

        QuoteTabApi.quoteTabApi.getAuthorsByLetter(letter, page).enqueue(new Callback<AuthorsByLetter>() {
            @Override
            public void onResponse(Call<AuthorsByLetter> call, Response<AuthorsByLetter> response) {

                //ovdje svaki put pravi novi adapter
                adapter = new AuthorsAdapter(Authors.this, response.body().getAuthors(), true);
                authorsRecyclerView.setAdapter(adapter);
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                loading = false;

            }

            @Override
            public void onFailure(Call<AuthorsByLetter> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {

        if (isByLetter) {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteAuthors(this));
            this.setResult(Authors.RESULT_OK, returnIntent);
            onBackPressed();
            return true;

        } else {
            onBackPressed();
            return true;
        }
    }

    @Override
    public void onBackPressed() {

        if (isByLetter) {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteAuthors(this));
            this.setResult(Authors.RESULT_OK, returnIntent);
            super.onBackPressed();

        } else {
            super.onBackPressed();
        }
    }
}
