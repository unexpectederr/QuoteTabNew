package helpers.main;

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

import digitalbath.quotetabnew.R;
import networking.QuoteTabApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class AppController extends Application {

    static ArrayList<Integer> bitmapIndexes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static void loadImageIntoView(final Context ctx, final int index, final ImageView img, final boolean isNew) {

        final SimpleTarget target = new SimpleTarget<Bitmap>() {

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

                if (isNew)
                    AppController.addBitmapIndex(index);
            }
        };

        Glide.with(ctx)
                .load(Constants.COVER_IMAGES_URL + index + ".jpg")
                .asBitmap()
                .into(target);

        if (!isNew)
            AppController.loadImageIntoView(ctx, (index + 1), null, true);
    }

    public static void addBitmapIndex(int index) {

        bitmapIndexes.add(index);

    }

    public static int getBitmapIndex() {

        int index = 0;

        index = bitmapIndexes.get(0);
        bitmapIndexes.remove(0);

        return index;

    }

}

