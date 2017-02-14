package helpers.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class QuoteImageView extends ImageView {

    private String imageUrl;

    public QuoteImageView(Context context) {
        super(context);
    }

    public QuoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuoteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
