package digitalbath.quotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import adapters.QuotesByTagAdapter;
import adapters.TopicsAdapter;
import digitalbath.quotetabnew.R;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopQuotes extends AppCompatActivity {

    private int page = 2;
    private boolean loading = false;
    int visibleItemCount;
    int totalItemCount;
    int pastVisibleItems;
    QuotesByTagAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_quotes);

        final RecyclerView topQuotesRecycler = (RecyclerView) findViewById(R.id.top_quotes_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        topQuotesRecycler.setLayoutManager(manager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

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

                adapter = new QuotesByTagAdapter(TopQuotes.this, response.body().getQuotes());
                topQuotesRecycler.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<models.quotes.TopQuotes> call, Throwable t) {}

        });
    }

    private void loadTopQuotes(int page) {
        QuoteTabApi.quoteTabApi.getTopQuotes(page).enqueue(new Callback<models.quotes.TopQuotes>() {
            @Override
            public void onResponse(Call<models.quotes.TopQuotes> call, Response<models.quotes.TopQuotes> response) {

                adapter.addQuotes(response.body().getQuotes());
                loading = false;
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<models.quotes.TopQuotes> call, Throwable t) {}
        });
    }
}
