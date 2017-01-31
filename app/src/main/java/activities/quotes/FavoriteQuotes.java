package activities.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import activities.quotetabnew.R;
import helpers.main.AppHelper;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;

public class FavoriteQuotes extends AppCompatActivity {

    public ArrayList<Quote> favoriteQuotes;
    public RecyclerView favoritesRecycler;
    QuotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        favoritesRecycler = (RecyclerView) findViewById(R.id.favorite_quotes_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        favoritesRecycler.setLayoutManager(llm);

        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes, true, false);

        favoritesRecycler.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == QuotesByTag.RESULT_OK) {

                favoriteQuotes = (ArrayList<Quote>) data.getSerializableExtra("result");
                adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes, true, false);
                favoritesRecycler.setAdapter(adapter);

            } else if (resultCode == QuotesByTag.RESULT_CANCELED) {

                AppHelper.showToast("Something went wrong", this);

            }
        }
    }
}

