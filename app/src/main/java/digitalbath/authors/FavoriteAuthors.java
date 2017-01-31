package digitalbath.authors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

import adapters.AuthorsAdapter;
import adapters.FavoriteAuthorsAdapter;
import digitalbath.quotetabnew.R;
import helpers.other.ReadAndWriteToFile;
import models.authors.AuthorDetails;
import models.dashboard.PopularAuthor;

public class FavoriteAuthors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authors);

        RecyclerView authorsRecyclerView = (RecyclerView) findViewById(R.id.authors_recycler_view);
        authorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<AuthorDetails> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(this);

        FavoriteAuthorsAdapter adapter = new FavoriteAuthorsAdapter(this, favoriteAuthors);
        authorsRecyclerView.setAdapter(adapter);
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }
}
