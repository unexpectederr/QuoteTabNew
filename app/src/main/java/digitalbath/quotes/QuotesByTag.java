package digitalbath.quotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import digitalbath.quotetabnew.R;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;
import models.quotes.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesByTag extends AppCompatActivity {

    ArrayList<Quote> favoriteQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_by_tag);

        initializeToolbar();

        RecyclerView quotesByTagRecycler = (RecyclerView) findViewById(R.id.quotes_by_tag_recycler);
        quotesByTagRecycler.setLayoutManager(new LinearLayoutManager(this));
        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        String tag = getIntent().getStringExtra(Constants.QUOTE_TAG);

        getQuotesByTag(tag, quotesByTagRecycler);

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
        searchIcon.setOnClickListener(new OnSearchIconClickListener(searchEditText, this));*/
    }

    private void getQuotesByTag(String tag, final RecyclerView quotesByTagRecycler) {

        QuoteTabApi.quoteTabApi.getQuotesByTag(tag).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                QuotesAdapter adapter = new QuotesAdapter(QuotesByTag.this, response.body().getQuotes(), favoriteQuotes, false, false);

                quotesByTagRecycler.setAdapter(adapter);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), QuotesByTag.this);
            }
        });
    }

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
