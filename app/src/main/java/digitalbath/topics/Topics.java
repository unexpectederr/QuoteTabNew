package digitalbath.topics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import adapters.QuotesByTagAdapter;
import adapters.TopicsAdapter;
import digitalbath.quotetabnew.R;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        final RecyclerView topicsRecycler = (RecyclerView) findViewById(R.id.topics_recycler);
        topicsRecycler.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        QuoteTabApi.quoteTabApi.getTopics().enqueue(new Callback<models.topics.Topics>() {
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
