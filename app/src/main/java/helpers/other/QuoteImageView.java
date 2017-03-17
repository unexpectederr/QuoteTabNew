package helpers.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class QuoteImageView extends ImageView implements Serializable {

    private String imageUrl;
    private String effectClassName;

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

    public String getEffectClassName() {
        return effectClassName;
    }

    public void setEffectClassName(String effectClassName) {
        this.effectClassName = effectClassName;
    }
}
