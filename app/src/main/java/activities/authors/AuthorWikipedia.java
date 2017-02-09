package activities.authors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.TextView;

import activities.quotetabnew.R;

public class AuthorWikipedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_wikipedia);

        initializeToolbar();

        String wikipediaUrl = getIntent().getStringExtra("URL");
        String authorName = getIntent().getStringExtra("AUTHOR_NAME");
        WebView webView = (WebView) findViewById(R.id.web_view);

        TextView screenTitle = (TextView) findViewById(R.id.screen_title);
        screenTitle.setText("About " + authorName);

        webView.loadUrl(wikipediaUrl);
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
