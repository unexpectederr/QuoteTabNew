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
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class AppController extends Application {

    static ArrayList<Integer> bitmapIndexes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static void loadImageIntoView(final Context ctx, final int index,
                                         final ImageView img, final boolean isNewIndex,
                                         final boolean loadOriginal) {

        if (!contextExists(ctx))
            return;

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>(loadOriginal ? Target.SIZE_ORIGINAL
                : Constants.RESIZED_WIDTH, loadOriginal ? Target.SIZE_ORIGINAL : Constants.RESIZED_HEIGHT) {

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

                AppController.loadImageIntoView(ctx, index + 1, img, true, loadOriginal);

            }
        };

        Glide.with(ctx)
                .load(Constants.COVER_IMAGES_URL + index + ".jpg")
                .asBitmap()
                .into(target);

        if (!isNewIndex)
            AppController.loadImageIntoView(ctx, (index + 1), null, true, loadOriginal);
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

    private static boolean contextExists(Context ctx) {
        return !(ctx == null || ((ctx instanceof Activity)
                && ((Activity) ctx).isDestroyed()));
    }
}

