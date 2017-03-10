package activities.quote_maker;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import adapters.PreviewImagesAdapter;
import digitalbath.quotetab.R;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.QuoteImageView;
import listeners.SaveImageToFileClickListener;
import listeners.ShareAsImageClickListener;
import models.filters.Filter;
import models.filters.FiltersList;
import models.images.ImageSuggestion;

public class QuoteMaker extends AppCompatActivity {

    private ImageView mSaveImage;
    private ImageEffectsAdapter effectsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_maker);

        QuoteImageView quoteImage = (QuoteImageView) findViewById(R.id.quote_image);

        initializeCommonContent(quoteImage);

        initializeEffectsRecyclerView(quoteImage);

        initializeImagesRecyclerView(quoteImage);


    }

    private void initializeCommonContent(QuoteImageView quoteImage) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText quoteText = (EditText) findViewById(R.id.quote_text);
        quoteText.setTypeface(AppHelper.getRalewayLight(this));

        EditText authorName = (EditText) findViewById(R.id.author_name);
        authorName.setTypeface(AppHelper.getRalewayLight(this));

        mSaveImage = (ImageView) findViewById(R.id.download_icon);
        mSaveImage.setOnClickListener(new SaveImageToFileClickListener(QuoteMaker.this,
                quoteText, authorName, quoteImage, quoteText.getLineCount()));

        ImageView share = (ImageView) findViewById(R.id.share_quote_icon);
        share.setOnClickListener(new ShareAsImageClickListener(this, quoteText,
                authorName, quoteImage, quoteText.getLineCount()));
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

        effects.get(0).setSelected(true);

        effectsAdapter = new ImageEffectsAdapter(effects, this, quoteImage);
        recyclerView.setAdapter(effectsAdapter);
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

        PreviewImagesAdapter adapter = new PreviewImagesAdapter(this, imageSuggestions,
                quoteImage, metrics.widthPixels, effectsAdapter);
        imagesRecycler.setAdapter(adapter);

        Glide.with(this)
                .load(Constants.COVER_IMAGES_URL + imageSuggestions.get(0).getImageId() + ".jpg")
                .asBitmap()
                .into(quoteImage);

        quoteImage.setImageUrl(Constants.COVER_IMAGES_URL + imageSuggestions.get(0).getImageId() + ".jpg");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == Constants.SAVE_IMAGE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mSaveImage.performClick();
        }
    }
}
