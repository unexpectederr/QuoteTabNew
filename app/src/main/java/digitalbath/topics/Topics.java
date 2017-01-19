package digitalbath.topics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import adapters.TopicsAdapter;
import digitalbath.quotetabnew.R;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topics extends AppCompatActivity {

    private int page = 2;
    RecyclerView topicsRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        topicsRecycler = (RecyclerView) findViewById(R.id.topics_recycler);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        topicsRecycler.setLayoutManager(manager);
        loadTopics();

        topicsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <)
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void loadTopics(){
        QuoteTabApi.quoteTabApi.getTopics().enqueue(new Callback<models.topics.Topics>() {
            @Override
            public void onResponse(Call<models.topics.Topics> call, Response<models.topics.Topics> response) {
                TopicsAdapter adapter = new TopicsAdapter(Topics.this, response.body().getTopics());
                topicsRecycler.setAdapter(adapter);
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
                TopicsAdapter adapter = new TopicsAdapter(Topics.this, response.body().getTopics());
                topicsRecycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<models.topics.Topics> call, Throwable t) {
                int i = 9;
            }
        });
    }
}
