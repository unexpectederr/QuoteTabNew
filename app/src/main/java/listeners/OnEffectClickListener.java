package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import activities.quote_maker.QuoteMaker;
import helpers.main.Constants;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class OnEffectClickListener implements View.OnClickListener {

    private Context context;
    private ImageView imageView;
    private int position;


    public OnEffectClickListener(Context context, ImageView imageView, int position) {
        this.context = context;
        this.imageView = imageView;
        this.position = position;

    }

    @Override
    public void onClick(View view) {

        switch (position) {
            case 0:
                Glide.with(context).load(Constants.COVER_IMAGES_URL + QuoteMaker.getLargeImageId() + ".jpg")
                        .bitmapTransform(new SepiaFilterTransformation(context)).into(imageView);
                break;
            case 1:
                Glide.with(context).load(Constants.COVER_IMAGES_URL + QuoteMaker.getLargeImageId() + ".jpg")
                        .bitmapTransform(new ToonFilterTransformation(context)).into(imageView);
                break;
            case 2:
                Glide.with(context).load(Constants.COVER_IMAGES_URL + QuoteMaker.getLargeImageId() + ".jpg")
                        .bitmapTransform(new KuwaharaFilterTransformation(context)).into(imageView);
                break;
            case 3:
                Glide.with(context).load(Constants.COVER_IMAGES_URL + QuoteMaker.getLargeImageId() + ".jpg")
                        .bitmapTransform(new GrayscaleTransformation(context)).into(imageView);
                break;
            case 4:
                Glide.with(context).load(Constants.COVER_IMAGES_URL + QuoteMaker.getLargeImageId() + ".jpg")
                        .bitmapTransform(new InvertFilterTransformation(context)).into(imageView);
                break;
        }
    }
}
