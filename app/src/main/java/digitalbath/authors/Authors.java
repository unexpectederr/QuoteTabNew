package digitalbath.authors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import org.zakariya.stickyheaders.StickyHeaderLayoutManager;
import adapters.AuthorsAdapter;
import digitalbath.quotetabnew.R;
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

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<models.authors.Authors>() {
            @Override
            public void onResponse(Call<models.authors.Authors> call, Response<models.authors.Authors> response) {
                AuthorsAdapter adapter = new AuthorsAdapter(response.body());
                authorsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<models.authors.Authors> call, Throwable t) {

            }
        });
    }
}
