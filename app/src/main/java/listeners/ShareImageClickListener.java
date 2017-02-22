package listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Spaja on 16-Feb-17.
 */

public class ShareImageClickListener implements View.OnClickListener, DialogInterface.OnClickListener{

    private Context context;
    private RelativeLayout relativeLayout;
    private static int imageIndex = 0;

    public ShareImageClickListener(Context context, RelativeLayout relativeLayout) {
        this.context = context;
        this.relativeLayout = relativeLayout;
    }

    @Override
    public void onClick(View view) {

        shareImage();
    }

    private void shareImage() {
        relativeLayout.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);

        writeImageToCache(bm);

        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image" + imageIndex + ".jpg");
        imageIndex++;
        Uri contentUri = FileProvider.getUriForFile(context,
                "com.digitalbath.quotetabnew.fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app:"));
        }
    }

    private void writeImageToCache(Bitmap bm) {
        FileOutputStream stream = null;
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            if (!cachePath.exists()) {
                cachePath.mkdirs();
            }
            stream = new FileOutputStream(cachePath + "/image" + imageIndex + ".jpg"); // overwrites this image every time
            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        shareImage();
    }
}

