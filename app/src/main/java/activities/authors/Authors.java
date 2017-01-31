package activities.authors;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import adapters.AuthorsAdapter;
import activities.quotetabnew.R;
import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

import helpers.main.AppHelper;
import helpers.other.ReadAndWriteToFile;
import listeners.OnSearchAuthorWatcher;
import listeners.OnSearchIconClickListener;
import models.authors.AuthorDetails;
import models.authors.PopularAuthors;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authors extends AppCompatActivity {

    EditText searchEditText;
    ImageView searchIcon;
    TextView screenTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

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
                new OnSearchIconClickListener(searchEditText,screenTitle, this));
    }

    private void initializeAuthorsList() {

        final RecyclerView authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        authorsRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<PopularAuthors>() {
            @Override
            public void onResponse(Call<PopularAuthors> call, Response<PopularAuthors> response) {

                ArrayList<AuthorDetails> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(Authors.this);

                AuthorsAdapter adapter = new AuthorsAdapter(response.body(), Authors.this, favoriteAuthors, false);
                authorsRecyclerView.setAdapter(adapter);

                searchEditText.addTextChangedListener(new OnSearchAuthorWatcher(response.body(),
                        authorsRecyclerView, searchIcon, Authors.this));

                findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PopularAuthors> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), Authors.this);
            }
        });
    }

    public boolean onSupportNavigateUp() {

        if (searchEditText.getVisibility() == View.VISIBLE) {
            searchEditText.setVisibility(View.GONE);
            searchEditText.setText("");
            screenTitle.setVisibility(View.VISIBLE);
            screenTitle.startAnimation(AppHelper.getAnimationUp(Authors.this));
            return true;
        }

        onBackPressed();

        return true;
    }
}
