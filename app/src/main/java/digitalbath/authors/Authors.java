package digitalbath.authors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import adapters.AuthorsAdapter;
import digitalbath.quotes.QuotesByTag;
import digitalbath.quotetabnew.R;
import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import helpers.AppHelper;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.authors_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recyclerView);
        authorsRecyclerView.setLayoutManager(new StickyHeaderLayoutManager());

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<PopularAuthors>() {
            @Override
            public void onResponse(Call<PopularAuthors> call, Response<PopularAuthors> response) {
                AuthorsAdapter adapter = new AuthorsAdapter(response.body(), Authors.this);
                authorsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PopularAuthors> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), Authors.this);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
