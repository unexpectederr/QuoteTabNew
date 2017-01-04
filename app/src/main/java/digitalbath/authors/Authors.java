package digitalbath.authors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import adapters.AuthorsAdapter;
import digitalbath.quotetabnew.R;
import models.Authors.Author;
import models.Authors.AuthorListItem;
import models.Authors.PopularAuthors;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        final RecyclerView authorsRecycler = (RecyclerView) findViewById(R.id.authors_recyclerView);

        QuoteTabApi.quoteTabApi.getAuthors().enqueue(new Callback<PopularAuthors>() {
            @Override
            public void onResponse(Call<PopularAuthors> call, Response<PopularAuthors> response) {

                ArrayList<AuthorListItem> authorsList = new ArrayList<>();

                for (int i = 0; i < response.body().getPopularAuthors().size(); i++) {

                    AuthorListItem header = new AuthorListItem(response.body().getPopularAuthors().get(i).
                            getReferences().getLetter(), true);

                    authorsList.add(header);

                    for (int j = 0; j < response.body().getPopularAuthors().get(i).getAuthors().size(); j++) {

                        AuthorListItem author = new AuthorListItem(response.body().getPopularAuthors().get(i)
                                .getAuthors().get(j).getFields().getName(), false);

                        authorsList.add(author);
                    }
                }

                AuthorsAdapter adapter = new AuthorsAdapter(Authors.this, authorsList);
                authorsRecycler.setLayoutManager(new LinearLayoutManager(Authors.this));
                authorsRecycler.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<PopularAuthors> call, Throwable t) {

            }
        });
    }
}
