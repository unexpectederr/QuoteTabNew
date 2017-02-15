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
import models.images.Effect;
import models.images.ImageSuggestion;

public class QuoteMaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_maker);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        QuoteImageView quoteImage = (QuoteImageView) findViewById(R.id.quote_image);

        initializeImagesRecyclerView(quoteImage);

        initializeEffectsRecyclerView(quoteImage);

        saveQuoteToDevice();

     }

    private void saveQuoteToDevice() {

        ImageView saveImage = (ImageView) findViewById(R.id.download_icon);
        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.top_layout);

        saveImage.setOnClickListener(new SaveImageToFileClickListener(QuoteMaker.this, relativeLayout));
    }

    private void initializeEffectsRecyclerView(QuoteImageView quoteImage) {

        ArrayList<Effect> effects = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Effect effect = new Effect();
            effect.setEffectName("effect" + i);
            effect.setImageId(AppController.getBitmapIndex());
            effects.add(effect);
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
        QuoteImagesAdapter adapter = new QuoteImagesAdapter(this, imageSuggestions, quoteImage);
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
