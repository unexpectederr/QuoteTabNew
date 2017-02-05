package activities.authors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import activities.quotes.QuotesByTag;
import activities.quotetabnew.R;
import adapters.AuthorsAdapter;
import helpers.main.AppHelper;
import helpers.other.ReadAndWriteToFile;
import models.authors.AuthorsByLetter;
import models.authors.AuthorDetails;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Authors extends AppCompatActivity {

    private boolean isByLetter;
    private ArrayList<AuthorDetails> authors;
    RecyclerView authorsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_authors);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        authorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        authors = ReadAndWriteToFile.getFavoriteAuthors(this);

        isByLetter = getIntent().getBooleanExtra("IS_BY_LETTER", false);
        final String letter = getIntent().getStringExtra("LETTER");

        if (isByLetter) {

            QuoteTabApi.quoteTabApi.getAuthorsByLetter(letter).enqueue(new Callback<AuthorsByLetter>() {
                @Override
                public void onResponse(Call<AuthorsByLetter> call, Response<AuthorsByLetter> response) {

                    AuthorsAdapter adapter = new AuthorsAdapter(Authors.this, response.body().getAuthors(), true);
                    authorsRecyclerView.setAdapter(adapter);
                    findViewById(R.id.progress_bar).setVisibility(View.GONE);

                    //setati naslov za actionbar
                    //handlati favorite icone i favorite authore
                }

                @Override
                public void onFailure(Call<AuthorsByLetter> call, Throwable t) {

                }
            });
        } else {

            AuthorsAdapter adapter = new AuthorsAdapter(this, authors, false);
            authorsRecyclerView.setAdapter(adapter);
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {

        if (isByLetter) {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteAuthors(this));
            this.setResult(Authors.RESULT_OK, returnIntent);
            onBackPressed();
            return true;
        } else {
            onBackPressed();
            return true;
        }
    }

    @Override
    public void onBackPressed() {

        if (isByLetter) {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", ReadAndWriteToFile.getFavoriteAuthors(this));
            this.setResult(Authors.RESULT_OK, returnIntent);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
}
