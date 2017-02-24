package listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import helpers.main.AppHelper;
import helpers.main.ReadAndWriteToFile;

/**
 * Created by Spaja on 16-Feb-17.
 */

public class ShareAsImageClickListener implements View.OnClickListener,
        MaterialDialog.SingleButtonCallback {

    private Context context;
    private RelativeLayout relativeLayout;
    private String quoteText, authorName;

    public ShareAsImageClickListener(Context context, RelativeLayout relativeLayout,
                                     String quoteText, String authorName) {
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.authorName = authorName;
        this.quoteText = quoteText;

    }

    @Override
    public void onClick(View view) {

        final Bitmap bm = AppHelper.createBitmapFromView(context, quoteText, authorName);
        shareImage(bm);
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

        final Bitmap bm = AppHelper.createBitmapFromView(context, quoteText, authorName);
        shareImage(bm);

    }

    private void shareImage(Bitmap bitmap) {

//        relativeLayout.setDrawingCacheEnabled(true);
//        Bitmap bm = Bitmap.createBitmap(relativeLayout.getDrawingCache());
//        relativeLayout.setDrawingCacheEnabled(false);

        ReadAndWriteToFile.writeQuoteImageToCache(context, bitmap);

        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.jpg");

        //imageIndex++;

        Uri contentUri = FileProvider.getUriForFile(context,
                "com.digitalbath.quotetabnew.fileprovider", newFile);

        if (contentUri != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app:"));
        }
    }
}

