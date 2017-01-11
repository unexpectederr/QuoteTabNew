package digitalbath.authors;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import adapters.AuthorDetailsAdapter;
import digitalbath.quotetabnew.R;
import helpers.AppController;
import models.authors.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorDetails extends AppCompatActivity {

    String authorID, authorName, authorImageUrl;
    RecyclerView authorDetailsRecyclerView;
    ImageView toolbarAuthorImage;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    TextView bornIn, country, profession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        initializeVariables();

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        authorDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        authorID = getIntent().getStringExtra("AUTHOR_ID");

        QuoteTabApi.quoteTabApi.getQuotes(authorID).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                AuthorDetailsAdapter adapter = new AuthorDetailsAdapter(response.body(), AuthorDetails.this);
                authorDetailsRecyclerView.setAdapter(adapter);

                authorName = response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getAuthorName();
                collapsingToolbarLayout.setTitle(authorName);

                authorImageUrl = response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getAuthorImageUrl();
                Glide.with(AuthorDetails.this).load(AppController.IMAGES_URL + authorImageUrl)
                        .error(R.drawable.avatar).into(toolbarAuthorImage);

                bornIn.setText(response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getBirthplace());
                profession.setText(response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote()
                        .getProfession().getProfessionName());
                country.setText(response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getCountry());
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                int i = 9;
            }
        });

    }

    private void initializeVariables() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarAuthorImage = (ImageView) findViewById(R.id.toolbar_author_image);
        authorDetailsRecyclerView = (RecyclerView) findViewById(R.id.author_details_recyclerView);
        bornIn = (TextView) findViewById(R.id.born_in);
        profession = (TextView) findViewById(R.id.profession);
        country = (TextView) findViewById(R.id.country);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.dashboard, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings)
//            Toast.makeText(this, "Settings icon pressed", Toast.LENGTH_SHORT).show();
//            return true;
//    }
}
