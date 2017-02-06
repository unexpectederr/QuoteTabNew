package activities.quotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import activities.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
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
    private ProgressBar progressBar;
    private ArrayList<Quote> favoriteQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_quotes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecyclerView topQuotesRecycler = (RecyclerView) findViewById(R.id.top_quotes_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        topQuotesRecycler.setLayoutManager(manager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        loadTopQuotes(topQuotesRecycler);

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

    private void loadTopQuotes(final RecyclerView topQuotesRecycler) {

        QuoteTabApi.quoteTabApi.getTopQuotes().enqueue(new Callback<models.quotes.TopQuotes>() {
            @Override
            public void onResponse(Call<models.quotes.TopQuotes> call, Response<models.quotes.TopQuotes> response) {

                ArrayList<Quote> quotes = response.body().getQuotes();

                for (int i = 0; i < quotes.size(); i++) {

                    if (quotes.get(i).getQuoteDetails() == null) {

                        quotes.remove(i);

                    }
                }

                adapter = new QuotesAdapter(TopQuotes.this, quotes, favoriteQuotes, false, false);
                topQuotesRecycler.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<models.quotes.TopQuotes> call, Throwable t) {
            }

        });
    }

    private void loadTopQuotes(int page) {

        QuoteTabApi.quoteTabApi.getTopQuotes(page).enqueue(new Callback<models.quotes.TopQuotes>() {
            @Override
            public void onResponse(Call<models.quotes.TopQuotes> call, Response<models.quotes.TopQuotes> response) {

                ArrayList<Quote> quotes = response.body().getQuotes();

                for (int i = 0; i < quotes.size(); i++) {

                    if (quotes.get(i).getQuoteDetails() == null) {

                        quotes.remove(i);

                    }
                }
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
}
