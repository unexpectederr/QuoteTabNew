package digitalbath.topics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yayandroid.parallaxrecyclerview.ParallaxRecyclerView;

import adapters.TopicsAdapter;
import digitalbath.quotetabnew.R;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topics extends AppCompatActivity {

    private int page = 2;
    private boolean loading = false;
    int visibleItemCount;
    int totalItemCount;
    int pastVisibleItems;
    TopicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        initializeToolbar();

        final ParallaxRecyclerView topicsRecycler = (ParallaxRecyclerView) findViewById(R.id.topics_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        topicsRecycler.setLayoutManager(manager);

        loadTopics(topicsRecycler);

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
                            loadMoreTopics(page, topicsRecycler);
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
            }

            @Override
            public void onFailure(Call<models.topics.Topics> call, Throwable t) {

            }
        });
    }

    private void loadMoreTopics(int page, final ParallaxRecyclerView topicsRecycler) {
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

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       /* searchEditText = (EditText) toolbar.findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(getResources().getColor(
                R.color.edit_text_toolbar_underline), PorterDuff.Mode.SRC_IN);

        searchIcon = (ImageView) findViewById(R.id.search_icon);
        searchIcon.setOnClickListener(new OnSearchIconClickListener(searchEditText, this));*/
    }

    public boolean onSupportNavigateUp() {

        /*if (searchEditText.getVisibility() == View.VISIBLE) {
            searchEditText.setVisibility(View.GONE);
            searchEditText.setAnimation(AppHelper.getAnimationDown(Authors.this));
            searchEditText.setText("");
            return true;
        }*/

        onBackPressed();

        return true;
    }

}
