package activities.quote_maker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import adapters.ImageEffectsAdapter;
import adapters.QuoteImagesAdapter;
import helpers.main.AppController;
import helpers.main.Constants;
import helpers.other.QuoteImageView;
import listeners.SaveImageToFileClickListener;
import listeners.ShareImageClickListener;
import models.images.Effect;
import models.images.ImageSuggestion;

public class QuoteMaker extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_maker);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<ImageSuggestion> imageSuggestions = new ArrayList<>();
        final QuoteImageView quoteImage = (QuoteImageView) findViewById(R.id.quote_image);

        for (int i = 0; i < 8; i++) {
            ImageSuggestion imageSuggestion = new ImageSuggestion();
            imageSuggestion.setImageId(AppController.getBitmapIndex());
            imageSuggestions.add(imageSuggestion);
        }
        initializeImagesRecyclerView(imageSuggestions, quoteImage);

        Glide.with(this).load(Constants.COVER_IMAGES_URL + imageSuggestions.get(0).getImageId() + ".jpg")
                .into(quoteImage);
        quoteImage.setImageUrl(Constants.COVER_IMAGES_URL + imageSuggestions.get(0).getImageId() + ".jpg");

        ArrayList<Effect> effects = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Effect effect = new Effect();
            effect.setEffectName("effect" + i);
            effect.setImageId(AppController.getBitmapIndex());
            effects.add(effect);
        }

        initializeEffectsRecyclerView(quoteImage, effects);

        saveImage();

        shareImage();
    }

    private void shareImage() {
        
        ImageView share = (ImageView) findViewById(R.id.share_quote_icon);
        share.setOnClickListener(new ShareImageClickListener(this, relativeLayout));
    }

    private void saveImage() {

        ImageView saveImage = (ImageView) findViewById(R.id.download_icon);
        relativeLayout = (RelativeLayout) findViewById(R.id.top_layout);

        saveImage.setOnClickListener(new SaveImageToFileClickListener(QuoteMaker.this, relativeLayout));
    }

    private void initializeEffectsRecyclerView(QuoteImageView quoteImage, ArrayList<Effect> effects) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.effects_recycler);ž
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new ImageEffectsAdapter(effects, this, quoteImage));
    }

    private void initializeImagesRecyclerView(ArrayList<ImageSuggestion> imageSuggestions, QuoteImageView quoteImage) {

        RecyclerView imagesRecycler = (RecyclerView) findViewById(R.id.background_images_recycler);
        GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);
        imagesRecycler.setLayoutManager(manager);
        QuoteImagesAdapter adapter = new QuoteImagesAdapter(this, imageSuggestions, quoteImage);
        imagesRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
