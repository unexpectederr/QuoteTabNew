package activities.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import adapters.QuotesAdapter;
import helpers.main.AppHelper;
import helpers.main.ReadAndWriteToFile;
import models.quotes.Quote;

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

        CardView cardView = (CardView) findViewById(R.id.empty_list);
        adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes, true, false, favoritesRecycler, cardView);

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

                CardView cardView = (CardView) findViewById(R.id.empty_list);
                adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes, true, false, favoritesRecycler, cardView);
                favoritesRecycler.setAdapter(adapter);

            } else if (resultCode == QuotesByTag.RESULT_CANCELED) {

                AppHelper.showToast("Something went wrong", this);

            }
        }
    }

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

