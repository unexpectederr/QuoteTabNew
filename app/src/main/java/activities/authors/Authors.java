package activities.authors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import activities.quotetabnew.R;
import adapters.AuthorsAdapter;
import helpers.other.ReadAndWriteToFile;
import models.authors.AuthorsByLetter;
import models.authors.AuthorDetails;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Authors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_authors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final RecyclerView authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        authorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ArrayList<AuthorDetails> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(this);

        boolean isByLetter = getIntent().getBooleanExtra("IS_BY_LETTER", false);
        final String letter = getIntent().getStringExtra("LETTER");

        if (isByLetter) {

            QuoteTabApi.quoteTabApi.getAuthorsByLetter(letter).enqueue(new Callback<AuthorsByLetter>() {
                @Override
                public void onResponse(Call<AuthorsByLetter> call, Response<AuthorsByLetter> response) {

                    AuthorsAdapter adapter = new AuthorsAdapter(Authors.this, response.body().getAuthors());
                    authorsRecyclerView.setAdapter(adapter);
                    findViewById(R.id.progress_bar).setVisibility(View.GONE);

                    //setati naslov za actionbar
                    //handlati favorite icone i favorite authore
                }

                @Override
                public void onFailure(Call<AuthorsByLetter> call, Throwable t) {

                }
            });
        }

        AuthorsAdapter adapter = new AuthorsAdapter(this, favoriteAuthors);
        authorsRecyclerView.setAdapter(adapter);
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
