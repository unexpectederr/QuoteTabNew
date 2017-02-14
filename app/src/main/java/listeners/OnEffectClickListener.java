package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import activities.quote_maker.QuoteMaker;
import helpers.main.Constants;
import helpers.other.QuoteImageView;
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
    private QuoteImageView imageView;
    private int position;


    public OnEffectClickListener(Context context, QuoteImageView imageView, int position) {
        this.context = context;
        this.imageView = imageView;
        this.position = position;

    }

    @Override
    public void onClick(View view) {

        switch (position) {
            case 0:
                Glide.with(context).load(imageView.getImageUrl())
                        .bitmapTransform(new SepiaFilterTransformation(context)).into(imageView);
                break;
            case 1:
                Glide.with(context).load(imageView.getImageUrl())
                        .bitmapTransform(new ToonFilterTransformation(context)).into(imageView);
                break;
            case 2:
                Glide.with(context).load(imageView.getImageUrl())
                        .bitmapTransform(new KuwaharaFilterTransformation(context)).into(imageView);
                break;
            case 3:
                Glide.with(context).load(imageView.getImageUrl())
                        .bitmapTransform(new GrayscaleTransformation(context)).into(imageView);
                break;
            case 4:
                Glide.with(context).load(imageView.getImageUrl())
                        .bitmapTransform(new InvertFilterTransformation(context)).into(imageView);
                break;
        }
    }
}
