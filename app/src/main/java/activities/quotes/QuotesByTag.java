package activities.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import adapters.QuotesAdapter;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.main.ReadAndWriteToFile;
import models.quotes.Quote;
import models.quotes.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesByTag extends AppCompatActivity {

    private int visibleItemCount, totalItemCount, pastVisibleItems, page = 1;
    private boolean loading = false;
    private QuotesAdapter adapter;
    private RecyclerView quotesByTagRecycler;
    private LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quotes_by_tag);

        initializeToolbar();

        initializeRecyclerView();

        final String tag = getIntent().getStringExtra(Constants.QUOTE_TAG).toLowerCase();

        TextView screenTitle = (TextView) findViewById(R.id.screen_title);
        screenTitle.setText(Character.toUpperCase(tag.charAt(0)) + tag.substring(1) + " Quotes");

        getQuotesByTag(tag, page);

        quotesByTagRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            findViewById(R.id.smooth_progress_bar).setVisibility(View.VISIBLE);
                            page++;
                            getQuotesByTag(tag, page);
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

    private void initializeRecyclerView() {

        quotesByTagRecycler = (RecyclerView) findViewById(R.id.quotes_by_tag_recycler);
        manager = new LinearLayoutManager(this);
        quotesByTagRecycler.setLayoutManager(manager);

        ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);
        adapter = new QuotesAdapter(this, new ArrayList<Quote>(), favoriteQuotes, false, false, null, null);

        quotesByTagRecycler.setAdapter(adapter);
    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       /* searchEditText = (EditText) toolbar.findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(getResources().getColor(
                R.color.edit_text_toolbar_underline), PorterDuff.Mode.SRC_IN);

        searchIcon = (ImageView) findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(new OnSearchAuthorsClickListener(searchEditText, this));*/
    }

    private void getQuotesByTag(String tag, int page) {

        QuoteTabApi.quoteTabApi.getQuotesByTag(tag, page).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                adapter.addQuotes(response.body().getQuotes());
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                findViewById(R.id.smooth_progress_bar).setVisibility(View.GONE);
                loading = false;

            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), QuotesByTag.this);
            }
        });
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
