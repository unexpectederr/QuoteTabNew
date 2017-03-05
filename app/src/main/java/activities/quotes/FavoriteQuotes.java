package activities.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import adapters.QuotesAdapter;
import helpers.main.AppHelper;
import helpers.main.ReadAndWriteToFile;
import models.quotes.Quote;
import models.quotes.QuoteReference;

public class FavoriteQuotes extends AppCompatActivity {

    public ArrayList<Quote> favoriteQuotes;
    public RecyclerView favoritesRecycler;
    private QuotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        initializeToolbar();

        initializeRecyclerView();

    }

    private void initializeRecyclerView() {

        favoritesRecycler = (RecyclerView) findViewById(R.id.favorite_quotes_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        favoritesRecycler.setLayoutManager(llm);

        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        TextView textView = (TextView) findViewById(R.id.favorites_text);
        textView.setText("Quotes you add to favorites will appear here...");

        RelativeLayout emptyList = (RelativeLayout) findViewById(R.id.empty_list_favorites);
        adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes,
                true, false, favoritesRecycler, emptyList);

        favoritesRecycler.setAdapter(adapter);
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_favorite_quotes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == QuotesByTag.RESULT_OK) {

                favoriteQuotes = (ArrayList<Quote>) data.getSerializableExtra("result");

                RelativeLayout emptyList = (RelativeLayout) findViewById(R.id.empty_list_favorites);
                adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes, true, false, favoritesRecycler, emptyList);

                favoritesRecycler.setAdapter(adapter);

            } else if (resultCode == QuotesByTag.RESULT_CANCELED) {

                AppHelper.showToast("Something went wrong", this);

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteQuotes(this));
        this.setResult(FavoriteQuotes.RESULT_OK, returnIntent);
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteQuotes(this));
        this.setResult(FavoriteQuotes.RESULT_OK, returnIntent);
        super.onBackPressed();
    }
}

