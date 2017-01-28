package digitalbath.quotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapters.QuotesByTagAdapter;
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

    RecyclerView quotesByTagRecycler;
    ArrayList<Quote> favoriteQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_by_tag);

        quotesByTagRecycler = (RecyclerView) findViewById(R.id.quotesByTagRecycler);
        quotesByTagRecycler.setLayoutManager(new LinearLayoutManager(this));
        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);


        String TAG = getIntent().getStringExtra(Constants.QUOTE_TAG);

        QuoteTabApi.quoteTabApi.getQuotesByTag(TAG).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {
                QuotesByTagAdapter adapter = new QuotesByTagAdapter(QuotesByTag.this, response.body().getQuotes(),favoriteQuotes);
                quotesByTagRecycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), QuotesByTag.this);
            }
        });
    }
}
