package activities.topics;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;

import digitalbath.quotetab.R;
import adapters.TopicsAdapter;
import helpers.main.AppHelper;
import helpers.main.Constants;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topics extends AppCompatActivity {

    private boolean loading = false;
    int page = 2, visibleItemCount, totalItemCount, pastVisibleItems;
    private TopicsAdapter adapter;
    private ParallaxRecyclerView topicsRecycler;
    private ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        initializeToolbar();

        initializeContent();

        loadTopics(topicsRecycler);

    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchIcon = (ImageView) findViewById(R.id.search_icon);

       /* searchEditText = (EditText) toolbar.findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(getResources().getColor(
                R.color.edit_text_toolbar_underline), PorterDuff.Mode.SRC_IN);

        searchIcon = (ImageView) findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(new OnSearchAuthorsClickListener(searchEditText, this));*/
    }

    private void initializeContent() {

        topicsRecycler = (ParallaxRecyclerView) findViewById(R.id.topics_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        topicsRecycler.setLayoutManager(manager);

        topicsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisibleItems = manager.findFirstVisibleItemPosition();

                    if (!loading) {

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = true;
                            loadMoreTopics(page);
                            page++;
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void loadTopics(final ParallaxRecyclerView topicsRecycler) {

        QuoteTabApi.quoteTabApi.getTopics().enqueue(new Callback<models.topics.Topics>() {
            @Override
            public void onResponse(Call<models.topics.Topics> call, Response<models.topics.Topics> response) {

                adapter = new TopicsAdapter(Topics.this, response.body().getTopics());
                topicsRecycler.setAdapter(adapter);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

                if (!PreferenceManager.getDefaultSharedPreferences(Topics.this)
                        .getBoolean(Constants.SEARCH_TOPICS_TIP, false))
                    AppHelper.showMaterialTip(searchIcon, Topics.this, "Search topics",
                            "You can search topics here and find easily what you are looking for",
                            Constants.SEARCH_TOPICS_TIP, R.drawable.ic_search);
            }

            @Override
            public void onFailure(Call<models.topics.Topics> call, Throwable t) {

            }
        });
    }

    private void loadMoreTopics(int page) {

        QuoteTabApi.quoteTabApi.getTopics(page).enqueue(new Callback<models.topics.Topics>() {
            @Override
            public void onResponse(Call<models.topics.Topics> call, Response<models.topics.Topics> response) {

                adapter.addTopics(response.body().getTopics());
                loading = false;
            }

            @Override
            public void onFailure(Call<models.topics.Topics> call, Throwable t) {

            }
        });
    }

    public boolean onSupportNavigateUp() {

        /*if (searchEditText.getVisibility() == View.VISIBLE) {
            searchEditText.setVisibility(View.GONE);
            searchEditText.setAnimation(AppHelper.getAnimationDown(PopularAuthors.this));
            searchEditText.setText("");
            return true;
        }*/

        onBackPressed();

        return true;
    }

}
