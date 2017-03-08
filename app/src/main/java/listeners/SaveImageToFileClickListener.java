package listeners;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import activities.quotes.TopQuotes;
import adapters.QuotesAdapter;
import digitalbath.quotetab.R;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.QuoteImageView;

/**
 * Created by Spaja on 15-Feb-17.
 */

public class SaveImageToFileClickListener implements View.OnClickListener {

    private Activity context;
    private String quoteText, authorName;
    private String quoteImage;
    private int quoteLines;
    private RecyclerView.Adapter mAdapter;

    private EditText mQuoteTxt, mAuthorNameTxt;
    private QuoteImageView mQuoteImage;

    public SaveImageToFileClickListener(Activity context, String quoteText,
                                        String authorName, String quoteImage, int quoteLines,
                                        RecyclerView.Adapter adapter) {
        this.context = context;
        this.mAdapter = adapter;
        this.quoteText = quoteText;
        this.quoteImage = quoteImage;
        this.quoteLines = quoteLines;
        this.authorName = authorName;
    }

    public SaveImageToFileClickListener(Activity context, EditText quoteText, EditText authorName,
                                        QuoteImageView quoteImage, int lineCount) {

        this.context = context;
        this.mAuthorNameTxt = authorName;
        this.mQuoteTxt = quoteText;
        this.mQuoteImage = quoteImage;
        this.quoteLines = lineCount;

    }

    @Override
    public void onClick(View view) {
//
//        this.authorName = mAuthorNameTxt.getText().toString();
//        this.quoteText = mQuoteTxt.getText().toString();
//        this.quoteImage = mQuoteImage.getImageUrl();

        loadImage();
    }

    private void saveImage(Bitmap quoteImage) {
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            if (mAdapter != null) {

                ((QuotesAdapter) mAdapter).setActiveDownloadButton((ImageView) context.findViewById(R.id.download_icon));
            }

            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.SAVE_IMAGE_PERMISSION);

        } else {

            AppHelper.createAndSaveImage(context, quoteText, authorName, quoteImage, quoteLines);

        }
    }

    private void loadImage() {

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                final Bitmap bm = AppHelper.createBitmapFromView(context, quoteText, authorName,
                        bitmap, quoteLines);
                saveImage(bm);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        };

        Glide.with(context)
                .load(quoteImage)
                .asBitmap()
                .into(target);

    }
}
