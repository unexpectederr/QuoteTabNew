package listeners;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import helpers.main.AppHelper;
import helpers.main.ReadAndWriteToFile;
import helpers.other.QuoteImageView;

/**
 * Created by Spaja on 16-Feb-17.
 */

public class ShareAsImageClickListener implements View.OnClickListener,
        MaterialDialog.SingleButtonCallback {

    private Activity mContext;
    private String mQuoteText;
    private String mAuthorName;
    private String mQuoteImageUrl;
    private int mQuoteLines;

    private EditText mQuoteTxt;
    private EditText mAuthorNameTxt;
    private QuoteImageView mQuoteImage;

    public ShareAsImageClickListener(Activity context, String quoteText, String authorName,
                                     String quoteImage, int lineCount) {
        this.mContext = context;
        this.mAuthorName = authorName;
        this.mQuoteText = quoteText;
        this.mQuoteImageUrl = quoteImage;
        this.mQuoteLines = lineCount;

    }

    public ShareAsImageClickListener(Activity context, EditText quoteText, EditText authorName,
                                     QuoteImageView quoteImage, int lineCount) {
        this.mContext = context;
        this.mAuthorNameTxt = authorName;
        this.mQuoteTxt = quoteText;
        this.mQuoteImage = quoteImage;
        this.mQuoteLines = lineCount;

    }

    @Override
    public void onClick(View view) {

        this.mAuthorName = mAuthorNameTxt.getText().toString();
        this.mQuoteText = mQuoteTxt.getText().toString();
        this.mQuoteImageUrl = mQuoteImage.getImageUrl();

        loadImage();

    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

        loadImage();

    }

    private void loadImage() {

        SimpleTarget<Bitmap> target = new SimpleTarget<Bitmap>() {

            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

                final Bitmap bm = AppHelper.createBitmapFromView(mContext, mQuoteText, mAuthorName,
                        bitmap, mQuoteLines);
                shareImage(bm);

            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        };

        Glide.with(mContext)
                .load(mQuoteImageUrl)
                .asBitmap()
                .into(target);

    }

    private void shareImage(Bitmap bitmap) {

        ReadAndWriteToFile.writeQuoteImageToCache(mContext, bitmap);

        File imagePath = new File(mContext.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.jpg");

        //imageIndex++;

        Uri contentUri = FileProvider.getUriForFile(mContext,
                "com.digitalbath.quotetabnew.fileprovider", newFile);

        if (contentUri != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, mContext.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            mContext.startActivity(Intent.createChooser(shareIntent, "Choose an app:"));
        }
    }
}

