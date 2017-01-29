package digitalbath.quotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import digitalbath.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.quotes.Quote;

public class FavoriteQuotes extends AppCompatActivity {

    public ArrayList<Quote> favoriteQuotes;
    public RecyclerView favoritesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_quotes);

        favoritesRecycler = (RecyclerView) findViewById(R.id.favorite_quotes_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        favoritesRecycler.setLayoutManager(llm);

        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        QuotesAdapter adapter = new QuotesAdapter(this, favoriteQuotes, favoriteQuotes);

        favoritesRecycler.setAdapter(adapter);
    }
}
