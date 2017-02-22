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

import helpers.other.QuoteImageView;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class OnPreviewImageClickListener implements View.OnClickListener {

    private String imageUrl;
    private QuoteImageView image;
    private Context context;

    public OnPreviewImageClickListener(Context context, QuoteImageView image, String imageUrl) {

        this.context = context;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    @Override
    public void onClick(View view) {

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
    }
}
