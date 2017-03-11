package listeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import adapters.ImageEffectsAdapter;
import adapters.PreviewImagesAdapter;
import helpers.other.QuoteImageView;
import models.images.ImageSuggestion;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class OnPreviewImageClickListener implements View.OnClickListener {

    private String imageUrl;
    private QuoteImageView image;
    private Context context;
    private ArrayList<ImageSuggestion> mDataSet;
    private PreviewImagesAdapter adapter;
    private int position;
    private ImageEffectsAdapter effectsAdapter;


    public OnPreviewImageClickListener(Context context, QuoteImageView image, String imageUrl,
                                       ArrayList<ImageSuggestion> mDataSet,
                                       PreviewImagesAdapter adapter, int position, ImageEffectsAdapter effectsAdapter) {
        this.context = context;
        this.image = image;
        this.imageUrl = imageUrl;
        this.mDataSet = mDataSet;
        this.adapter = adapter;
        this.position = position;
        this.effectsAdapter = effectsAdapter;
    }

    @Override
    public void onClick(View view) {

        for (int i = 0; i < mDataSet.size(); i++) {
            if (mDataSet.get(i).isSelected()) {
                mDataSet.get(i).setSelected(false);
                adapter.notifyItemChanged(i);
            }
        }

        effectsAdapter.setAdapterToFirstPosition(position);

        mDataSet.get(position).setSelected(true);
        adapter.notifyItemChanged(position);

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                        new ColorDrawable(Color.TRANSPARENT),
                        new BitmapDrawable(context.getResources(), bitmap)
                });

                image.setImageDrawable(td);

                td.startTransition(300);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        };

        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .into(target);

        image.setImageUrl(imageUrl);
        image.setEffectClassName("normal");
    }
}
