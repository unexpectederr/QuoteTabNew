package digitalbath.authors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import adapters.AuthorsRecyclerViewAdapter;
import digitalbath.quotetabnew.R;
import models.Authors.Results;
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
        authorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<models.Authors.Authors>() {
            @Override
            public void onResponse(Call<models.Authors.Authors> call, Response<models.Authors.Authors> response) {
                ArrayList<Results> authorsList = new ArrayList<>();
                for (int i = 0; i < response.body().getPopularAuthors().size(); i++) {
                    authorsList.addAll(response.body().getPopularAuthors().get(i).getResults());
                }
                AuthorsRecyclerViewAdapter adapter = new AuthorsRecyclerViewAdapter(authorsList);
                authorsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<models.Authors.Authors> call, Throwable t) {

            }
        });
    }
}
