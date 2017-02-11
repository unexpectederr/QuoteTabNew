package helpers.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class AppController extends Application {

    static ArrayList<Integer> bitmapIndexes = new ArrayList<>();
    static SimpleTarget target;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static void loadImageIntoView(final Context ctx, final int index,
                                         final ImageView img, final boolean isNewIndex,
                                         int height, int width) {

        if (ctx == null || (ctx instanceof Activity && ((Activity) ctx).isDestroyed()))
            return;
        if (height == 0 && width == 0) {
            target = new SimpleTarget<Bitmap>() {

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
            };
        } else {
            target = new SimpleTarget<Bitmap>(height, width) {

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
            };
        }


        Glide.with(ctx)
                .load(Constants.COVER_IMAGES_URL + index + ".jpg")
                .asBitmap()
                .into(target);


        if (!isNewIndex)
            AppController.loadImageIntoView(ctx, (index + 1), null, true, 0, 0);
    }

    public static void addBitmapIndex(int index) {

        bitmapIndexes.add(index);

    }

    public static int getBitmapIndex() {

        int index;

        if (bitmapIndexes.size() < 4)
            return new Random().nextInt(Constants.NUMBER_OF_COVERS);

        int arrayIndex = new Random().nextInt(bitmapIndexes.size());

        index = bitmapIndexes.get(arrayIndex);
        bitmapIndexes.remove(arrayIndex);

        return index;

    }
}

