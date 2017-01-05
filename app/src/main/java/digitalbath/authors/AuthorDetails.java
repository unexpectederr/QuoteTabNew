package digitalbath.authors;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import adapters.AuthorDetailsAdapter;
import digitalbath.quotetabnew.R;
import models.authors.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorDetails extends AppCompatActivity {

    String authorID;
    RecyclerView authorDetailsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("TESSSST");

        authorDetailsRecyclerView = (RecyclerView) findViewById(R.id.author_details_recyclerView);
        authorDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        authorID = getIntent().getStringExtra("AUTHOR_ID");

        QuoteTabApi.quoteTabApi.getQuotes("by-" + authorID).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {
                AuthorDetailsAdapter adapter = new AuthorDetailsAdapter(response.body());
                authorDetailsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                int i = 9;
            }
        });

    }
}
