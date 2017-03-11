package activities.authors;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import activities.quotes.QuotesByTag;
import digitalbath.quotetab.R;
import adapters.AuthorsAdapter;
import helpers.main.AppHelper;
import helpers.main.Mapper;
import helpers.main.ReadAndWriteToFile;
import models.authors.Author;
import models.authors.AuthorDetails;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Authors extends AppCompatActivity {

    RecyclerView authorsRecyclerView;
    int page = 1, visibleItemCount, totalItemCount, pastVisibleItems;
    TextView screenTitle;
    private AuthorsAdapter adapter;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        initializeToolbar();

        initializeContent();

    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void initializeContent() {

        authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        authorsRecyclerView.setLayoutManager(manager);

        screenTitle = (TextView) findViewById(R.id.screen_title);
        ArrayList<Author> authors = ReadAndWriteToFile.getFavoriteAuthors(this);

        boolean isByLetter = getIntent().getBooleanExtra("IS_BY_LETTER", false);
        final String letter = getIntent().getStringExtra("LETTER");

        RelativeLayout emptyList = (RelativeLayout) findViewById(R.id.empty_list_favorites);

        if (isByLetter) {

            screenTitle.setText("All '" + letter.toUpperCase() + "' Authors");
            adapter = new AuthorsAdapter(this, new ArrayList<Author>(), true, null, emptyList, null);
            authorsRecyclerView.setAdapter(adapter);

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
                                page++;
                                loadAuthors(letter, page);
                                findViewById(R.id.smooth_progress_bar).setVisibility(View.VISIBLE);
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

            screenTitle.setText("Favorite Authors");
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
            AuthorsAdapter adapter = new AuthorsAdapter(this, authors, false, authorsRecyclerView, emptyList, appBarLayout);
            authorsRecyclerView.setAdapter(adapter);
            TextView textView = (TextView) findViewById(R.id.favorites_text);
            textView.setText("Authors you add to favorites will appear here...");
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }

    private void loadAuthors(String letter, int page) {

        QuoteTabApi.quoteTabApi.getAuthorsByLetter(letter, page).enqueue(new Callback<models.authors.Authors>() {
            @Override
            public void onResponse(Call<models.authors.Authors> call, Response<models.authors.Authors> response) {

                adapter.addAuthors(Mapper.mapAuthors(response.body().getAuthors()));
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                loading = false;
                findViewById(R.id.smooth_progress_bar).setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<models.authors.Authors> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), Authors.this);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

                final RelativeLayout fail = (RelativeLayout) findViewById(R.id.fail_layout);
                fail.setVisibility(View.VISIBLE);

                final Button reload = (Button) findViewById(R.id.reload);
                reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        reload.startAnimation(AppHelper.getRotateAnimation(Authors.this));
                        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                        initializeContent();
                        fail.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteAuthors(this));
        returnIntent.putExtra("isFromAuthors", true);
        this.setResult(activities.authors.Authors.RESULT_OK, returnIntent);
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteAuthors(this));
        returnIntent.putExtra("isFromAuthors", true);
        this.setResult(activities.authors.Authors.RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}
