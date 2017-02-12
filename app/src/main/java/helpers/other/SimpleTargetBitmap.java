package helpers.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import helpers.main.AppController;

/**
 * Created by Spaja on 12-Feb-17.
 */
public class SimpleTargetBitmap extends SimpleTarget<Bitmap> {
    private final Context ctx;
    private final int index;
    private final ImageView img;
    private final boolean isNewIndex;

    public SimpleTargetBitmap(Context ctx, int index, ImageView img, boolean isNewIndex) {
        super();
        this.ctx = ctx;
        this.index = index;
        this.img = img;
        this.isNewIndex = isNewIndex;
    }
    public SimpleTargetBitmap(Context ctx, int index, ImageView img, boolean isNewIndex, int width, int height) {
        super(width, height);
        this.ctx = ctx;
        this.index = index;
        this.img = img;
        this.isNewIndex = isNewIndex;
    }


    @Override
    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

        if (img != null) {

            TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                    new ColorDrawable(Color.TRANSPARENT),
                    new BitmapDrawable(ctx.getResources(), bitmap)
            });

            img.setImageDrawable(td);

            td.startTransition(300);
        }

        if (isNewIndex)
            AppController.addBitmapIndex(index);
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        super.onLoadFailed(e, errorDrawable);

        AppController.loadImageIntoView(ctx, index + 1, null, true, 0, 0);

    }
}
