package activities.quotes;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import adapters.QuotesAdapter;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.main.Mapper;
import helpers.main.ReadAndWriteToFile;
import models.quotes.Quote;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopQuotes extends AppCompatActivity {

    private int page = 2;
    private boolean loading = false;
    private int visibleItemCount, totalItemCount, pastVisibleItems;
    private QuotesAdapter adapter;
    private SmoothProgressBar progressBar;
    private ArrayList<Quote> favoriteQuotes;
    private LinearLayoutManager manager;
    private RecyclerView topQuotesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top_quotes);

        initializeToolbar();

        initializeRecyclerView();

        loadTopQuotes(topQuotesRecycler);

    }

    private void initializeRecyclerView() {

        topQuotesRecycler = (RecyclerView) findViewById(R.id.top_quotes_recycler);
        manager = new LinearLayoutManager(this);
        topQuotesRecycler.setLayoutManager(manager);
        progressBar = (SmoothProgressBar) findViewById(R.id.smooth_progress_bar);
        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        topQuotesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisibleItems = manager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= (totalItemCount - 3)) {
                            loading = true;
                            progressBar.setVisibility(View.VISIBLE);
                            loadTopQuotes(page);
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
    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void loadTopQuotes(final RecyclerView topQuotesRecycler) {

        QuoteTabApi.quoteTabApi.getTopQuotes().enqueue(new Callback<models.quotes.TopQuotes>() {
            @Override
            public void onResponse(Call<models.quotes.TopQuotes> call, Response<models.quotes.TopQuotes> response) {

                ArrayList<Quote> quotes = Mapper.mapQuotes(response.body().getQuotes());

                adapter = new QuotesAdapter(TopQuotes.this, quotes, favoriteQuotes, false, false, null, null, null);
                topQuotesRecycler.setAdapter(adapter);
                findViewById(R.id.progress_bar).setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<models.quotes.TopQuotes> call, Throwable t) {

                AppHelper.showToast(getResources().getString(R.string.toast_error_message), TopQuotes.this);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

                if (page == 1) {
                    final RelativeLayout fail = (RelativeLayout) findViewById(R.id.fail_layout);
                    fail.setVisibility(View.VISIBLE);

                    final Button reload = (Button) findViewById(R.id.reload);
                    reload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            reload.startAnimation(AppHelper.getRotateAnimation(TopQuotes.this));
                            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                            initializeRecyclerView();
                            loadTopQuotes(topQuotesRecycler);
                            fail.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void loadTopQuotes(int page) {

        QuoteTabApi.quoteTabApi.getTopQuotes(page).enqueue(new Callback<models.quotes.TopQuotes>() {
            @Override
            public void onResponse(Call<models.quotes.TopQuotes> call, Response<models.quotes.TopQuotes> response) {

                ArrayList<Quote> quotes = Mapper.mapQuotes(response.body().getQuotes());

                adapter.addQuotes(quotes);
                loading = false;
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<models.quotes.TopQuotes> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == Constants.SAVE_IMAGE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            adapter.getActiveDownloadIcon().performClick();
        }
    }
}
