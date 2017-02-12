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
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.Random;

import helpers.other.SimpleTargetBitmap;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class AppController extends Application {

    private static ArrayList<Integer> bitmapIndexes = new ArrayList<>();

    public static void loadImageIntoView(final Context ctx, final int index,
                                         final ImageView img, final boolean isNewIndex,
                                         int height, int width) {
        if (contextExists(ctx)) {
            SimpleTarget<Bitmap> target = getSimpleTarget(ctx, index, img, isNewIndex, height, width);
            Glide.with(ctx)
                    .load(Constants.COVER_IMAGES_URL + index + ".jpg")
                    .asBitmap()
                    .into(target);

            if (!isNewIndex)
                AppController.loadImageIntoView(ctx, (index + 1), null, true, 0, 0);
        }
    }

    @NonNull
    private static SimpleTarget<Bitmap> getSimpleTarget(final Context ctx, final int index, final ImageView img, final boolean isNewIndex, final int height, final int width) {
        SimpleTarget<Bitmap> target;
        if (height == 0 && width == 0) {
            target = new SimpleTargetBitmap(ctx, index, img, isNewIndex);
        } else {
            target = new SimpleTargetBitmap(ctx, index, img, isNewIndex, width, height);
        }
        return target;
    }

    private static boolean contextExists(Context ctx) {
        return !(ctx == null || ((ctx instanceof Activity) && ((Activity) ctx).isDestroyed()));
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

    @Override
    public void onCreate() {
        super.onCreate();
    }


}

