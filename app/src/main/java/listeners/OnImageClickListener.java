package listeners;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import activities.quote_maker.QuoteMaker;
import helpers.main.Constants;
import helpers.other.QuoteImageView;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class OnImageClickListener implements View.OnClickListener {

    private int imageId;
    private QuoteImageView image;
    private Context context;

    public OnImageClickListener(Context context, QuoteImageView image, int imageId) {

        this.context = context;
        this.image = image;
        this.imageId = imageId;
    }

    @Override
    public void onClick(View view) {
        Glide.with(context).load(Constants.COVER_IMAGES_URL + imageId + ".jpg").into(image);
        image.setImageUrl(Constants.COVER_IMAGES_URL + imageId + ".jpg");
    }
}
