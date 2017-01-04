package digitalbath.authors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import adapters.AuthorsAdapter;
import digitalbath.quotetabnew.R;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import models.authors.PopularAuthors;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authors extends AppCompatActivity {

    RecyclerView authorsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recyclerView);
        authorsRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<PopularAuthors>() {
            @Override
            public void onResponse(Call<PopularAuthors> call, Response<PopularAuthors> response) {
                AuthorsAdapter adapter = new AuthorsAdapter(response.body());
                authorsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PopularAuthors> call, Throwable t) {

            }
        });
    }
}
