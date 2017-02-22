package activities.quote_maker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import adapters.ImageEffectsAdapter;
import adapters.QuoteImagesAdapter;
import digitalbath.quotetab.R;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.QuoteImageView;
import listeners.SaveImageToFileClickListener;
import listeners.ShareImageClickListener;
import models.filters.Filter;
import models.filters.FiltersList;
import models.images.ImageSuggestion;

public class QuoteMaker extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_maker);

        initializeCommonContent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QuoteImageView quoteImage = (QuoteImageView) findViewById(R.id.quote_image);

        initializeImagesRecyclerView(quoteImage);

        initializeEffectsRecyclerView(quoteImage);

    }

    private void initializeCommonContent() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.top_layout);

        ImageView share = (ImageView) findViewById(R.id.share_quote_icon);
        share.setOnClickListener(new ShareImageClickListener(this, relativeLayout));

        ImageView saveImage = (ImageView) findViewById(R.id.download_icon);
        saveImage.setOnClickListener(new SaveImageToFileClickListener(QuoteMaker.this, relativeLayout, null, null));

        EditText quoteText = (EditText) findViewById(R.id.quote_text);
        quoteText.setTypeface(AppHelper.getRalewayLight(this));

        EditText authorName = (EditText) findViewById(R.id.author_name);
        authorName.setTypeface(AppHelper.getRalewayLight(this));
    }

    private void initializeEffectsRecyclerView(QuoteImageView quoteImage) {

        ArrayList<Filter> effects = new ArrayList<>();
        FiltersList filters = new FiltersList();
        int filtersSize = filters.getFiltersList().size();
        String[] filterNames = filters.getFiltersList().keySet().toArray(new String[filtersSize]);
        String[] filterClassNames = filters.getFiltersList().values().toArray(new String[filtersSize]);

        for (int i = 0; i < filtersSize; i++) {

            Filter filter = new Filter();
            filter.setFilterName(filterNames[i]);
            filter.setFilterClass(filterClassNames[i]);
            effects.add(filter);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.effects_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new ImageEffectsAdapter(effects, this, quoteImage));
    }

    private void initializeImagesRecyclerView(QuoteImageView quoteImage) {

        ArrayList<ImageSuggestion> imageSuggestions = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ImageSuggestion imageSuggestion = new ImageSuggestion();
            imageSuggestion.setImageId(AppController.getBitmapIndex());
            imageSuggestions.add(imageSuggestion);
        }

        RecyclerView imagesRecycler = (RecyclerView) findViewById(R.id.background_images_recycler);
        GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        imagesRecycler.setLayoutManager(manager);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        QuoteImagesAdapter adapter = new QuoteImagesAdapter(this, imageSuggestions,
                quoteImage, metrics.widthPixels);
        imagesRecycler.setAdapter(adapter);



        Glide.with(this).load(Constants.COVER_IMAGES_URL + imageSuggestions.get(0).getImageId() + ".jpg")
                .into(quoteImage);
        quoteImage.setImageUrl(Constants.COVER_IMAGES_URL + imageSuggestions.get(0).getImageId() + ".jpg");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}