package listeners;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Spaja on 16-Feb-17.
 */

public class ShareImageClickListener implements View.OnClickListener {

    private Context context;
    private RelativeLayout relativeLayout;

    public ShareImageClickListener(Context context, RelativeLayout relativeLayout) {
        this.context = context;
        this.relativeLayout = relativeLayout;
    }

    @Override
    public void onClick(View view) {

        relativeLayout.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        FileOutputStream fo = null;

        try {
            fo = new FileOutputStream(f);
            f.createNewFile();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(Environment.getExternalStorageDirectory().getPath() + File.separator + "temporary_file.jpg"));
        context.startActivity(Intent.createChooser(share, "Share Image"));
    }
}
