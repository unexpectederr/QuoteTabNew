package activities.authors;

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
import activities.quotetabnew.R;
import adapters.PopularAuthorsAdapter;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import listeners.OnSearchAuthorWatcher;
import listeners.OnSearchIconClickListener;
import models.authors.AuthorDetails;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularAuthors extends AppCompatActivity {

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

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<models.authors.PopularAuthors>() {
            @Override
            public void onResponse(Call<models.authors.PopularAuthors> call, Response<models.authors.PopularAuthors> response) {

                ArrayList<AuthorDetails> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(PopularAuthors.this);

                PopularAuthorsAdapter adapter = new PopularAuthorsAdapter(response.body(), PopularAuthors.this, favoriteAuthors);
                authorsRecyclerView.setAdapter(adapter);

                searchEditText.addTextChangedListener(new OnSearchAuthorWatcher(response.body(),
                        authorsRecyclerView, searchIcon, PopularAuthors.this));

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

                if (!PreferenceManager.getDefaultSharedPreferences(PopularAuthors.this)
                        .getBoolean(Constants.SEARCH_AUTHORS_TIP, false))
                    AppHelper.showMaterialTip(searchIcon, PopularAuthors.this, "Search authors",
                            "You can search authors here and find easily what you are looking for",
                            Constants.SEARCH_AUTHORS_TIP, R.drawable.ic_search);
            }

            @Override
            public void onFailure(Call<models.authors.PopularAuthors> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), PopularAuthors.this);
            }
        });
    }

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
}
