package activities.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import activities.authors.Authors;
import digitalbath.quotetab.R;
import adapters.QuotesAdapter;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.main.Mapper;
import helpers.main.ReadAndWriteToFile;
import models.quotes.Quote;
import models.quotes.QuoteReference;
import models.quotes.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesByTag extends AppCompatActivity {

    private int page = 1;
    private boolean loading = false;
    private QuotesAdapter adapter;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_by_tag);

        String tag = getIntent().getStringExtra(Constants.QUOTE_TAG).toLowerCase();

        initializeContent(tag);

        getQuotesByTag(tag, page);


    }

    private void initializeContent(String tag) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView screenTitle = (TextView) findViewById(R.id.screen_title);
        screenTitle.setText(Character.toUpperCase(tag.charAt(0)) + tag.substring(1) + " Quotes");

        RecyclerView quotesByTagRecycler = (RecyclerView) findViewById(R.id.quotes_by_tag_recycler);
        manager = new LinearLayoutManager(this);
        quotesByTagRecycler.setLayoutManager(manager);

        ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        adapter = new QuotesAdapter(this, new ArrayList<Quote>(),
                favoriteQuotes, false, false, null, null, null);

        quotesByTagRecycler.setAdapter(adapter);

        quotesByTagRecycler.addOnScrollListener(new OnScrollListener(tag));
    }

    private void getQuotesByTag(final String tag, final int page) {

        QuoteTabApi.quoteTabApi.getQuotesByTag(tag, page).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                adapter.addQuotes(Mapper.mapQuotes(response.body().getQuotes()));

                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                findViewById(R.id.smooth_progress_bar).setVisibility(View.GONE);

                loading = false;

            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {

                AppHelper.showToast(getResources().getString(R.string.toast_error_message), QuotesByTag.this);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                findViewById(R.id.smooth_progress_bar).setVisibility(View.GONE);

                final RelativeLayout fail = (RelativeLayout) findViewById(R.id.fail_layout);
                fail.setVisibility(View.VISIBLE);

                final Button reload = (Button) findViewById(R.id.reload);
                reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        reload.startAnimation(AppHelper.getRotateAnimation(QuotesByTag.this));
                        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                        initializeContent(tag);
                        getQuotesByTag(tag, page);
                        fail.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {

        String mTag;
        OnScrollListener(String tag) {
            mTag = tag;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0) {

                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int pastVisibleItems = manager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisibleItems) >= (totalItemCount - 3)) {
                        loading = true;
                        findViewById(R.id.smooth_progress_bar).setVisibility(View.VISIBLE);
                        page++;
                        getQuotesByTag(mTag, page);
                    }
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteQuotes(this));
        this.setResult(QuotesByTag.RESULT_OK, returnIntent);
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteQuotes(this));
        this.setResult(QuotesByTag.RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}
