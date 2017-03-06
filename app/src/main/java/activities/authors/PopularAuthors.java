package activities.authors;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import adapters.PopularAuthorsAdapter;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.main.Mapper;
import helpers.main.ReadAndWriteToFile;
import listeners.OnSearchAuthorsClickListener;
import listeners.OnSearchAuthorWatcher;
import models.authors.Author;
import models.authors.AuthorDetails;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularAuthors extends AppCompatActivity {

    EditText searchEditText;
    ImageView searchIcon;
    TextView screenTitle;
    RecyclerView authorsRecyclerView;
    models.authors.PopularAuthors authors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_authors);

        initializeContent();
    }

    private void initializeContent() {

        initializeToolbar();

        initializeAuthorsList();
    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        screenTitle = (TextView) findViewById(R.id.screen_title);

        searchEditText = (EditText) toolbar.findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(getResources().getColor(
                R.color.edit_text_toolbar_underline), PorterDuff.Mode.SRC_IN);

        searchIcon = (ImageView) findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(
                new OnSearchAuthorsClickListener(searchEditText, screenTitle, this));

    }

    private void initializeAuthorsList() {

        authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        authorsRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<models.authors.PopularAuthors>() {

            @Override
            public void onResponse(Call<models.authors.PopularAuthors> call, Response<models.authors.PopularAuthors> response) {

                ArrayList<Author> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(PopularAuthors.this);

                AuthorDetails author = new AuthorDetails();
                author.setLast(true);

                authors = response.body();

                for (int i = 0; i < response.body().getAuthorGroup().size(); i++) {
                    authors.getAuthorGroup().get(i).getAuthorDetailsList().add(author);
                }

                PopularAuthorsAdapter adapter = new PopularAuthorsAdapter(authors, PopularAuthors.this,
                        favoriteAuthors);

                authorsRecyclerView.setAdapter(adapter);

                searchEditText.addTextChangedListener(new OnSearchAuthorWatcher(authors,
                        authorsRecyclerView, searchIcon, PopularAuthors.this));

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

                if (!PreferenceManager.getDefaultSharedPreferences(PopularAuthors.this).getBoolean(Constants.SEARCH_AUTHORS_TIP, false))
                    AppHelper.showMaterialTip(searchIcon, PopularAuthors.this, "Search authors",
                            "You can search authors here and find easily what you are looking for",
                            Constants.SEARCH_AUTHORS_TIP, R.drawable.ic_search);
            }

            @Override
            public void onFailure(Call<models.authors.PopularAuthors> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), PopularAuthors.this);
            }
        }

        );
    }

    @Override
    public boolean onSupportNavigateUp() {

        if (searchEditText.getVisibility() == View.VISIBLE) {
            searchEditText.setVisibility(View.GONE);
            searchEditText.setText("");
            screenTitle.setVisibility(View.VISIBLE);
            screenTitle.startAnimation(AppHelper.getAnimationUp(PopularAuthors.this));
            return true;
        }

        onBackPressed();

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Authors.RESULT_OK) {

                ArrayList<Author> favoriteAuthors = (ArrayList<Author>) data.getSerializableExtra("result");
                PopularAuthorsAdapter adapter = new PopularAuthorsAdapter(authors, this, favoriteAuthors);
                authorsRecyclerView.setAdapter(adapter);
                findViewById(R.id.progress_bar).setVisibility(View.GONE);

            } else if (resultCode == Authors.RESULT_CANCELED) {

                AppHelper.showToast("Something went wrong", this);

            }
        }
    }
}

