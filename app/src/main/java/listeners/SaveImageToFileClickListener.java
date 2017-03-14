package listeners;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import adapters.QuotesAdapter;
import digitalbath.quotetab.R;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.QuoteImageView;

/**
 * Created by Spaja on 15-Feb-17.
 */

public class SaveImageToFileClickListener implements View.OnClickListener {

    private Activity mContext;
    private String mQuoteText, mAuthorName;
    private String quoteImage;
    private int quoteLines;
    private RecyclerView.Adapter mAdapter;

    private EditText mQuoteTxt, mAuthorNameTxt;
    private QuoteImageView mQuoteImage;

    public SaveImageToFileClickListener(Activity context, String quoteText,
                                        String authorName, String quoteImage, int quoteLines,
                                        RecyclerView.Adapter adapter) {
        this.mContext = context;
        this.mAdapter = adapter;
        this.mQuoteText = quoteText;
        this.quoteImage = quoteImage;
        this.quoteLines = quoteLines;
        this.mAuthorName = authorName;

    }

    public SaveImageToFileClickListener(Activity context, EditText quoteText, EditText authorName,
                                        QuoteImageView quoteImage, int lineCount) {
        this.mContext = context;
        this.mAuthorNameTxt = authorName;
        this.mQuoteTxt = quoteText;
        this.mQuoteImage = quoteImage;
        this.quoteLines = lineCount;

    }

    @Override
    public void onClick(View view) {

        if (mAuthorNameTxt != null && mQuoteTxt != null) {

            this.mAuthorName = mAuthorNameTxt.getText().toString();
            this.mQuoteText = mQuoteTxt.getText().toString();
            this.quoteImage = mQuoteImage.getImageUrl();
        }

        loadImage();

    }

    private void saveImage(Bitmap quoteImage) {

        int permissionCheck = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            if (mAdapter != null) {

                ((QuotesAdapter) mAdapter).setActiveDownloadButton((ImageView) mContext.findViewById(R.id.download_icon));
            }

            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.SAVE_IMAGE_PERMISSION);

        } else {

            AppHelper.createAndSaveImage(mContext, mQuoteText, mAuthorName, quoteImage, quoteLines);

        }
    }

    private void loadImage() {

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                final Bitmap bm = AppHelper.createBitmapFromView(mContext, mQuoteText, mAuthorName,
                        bitmap, quoteLines);
                saveImage(bm);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        };

        Glide.with(mContext)
                .load(quoteImage)
                .asBitmap()
                .into(target);

    }
}
