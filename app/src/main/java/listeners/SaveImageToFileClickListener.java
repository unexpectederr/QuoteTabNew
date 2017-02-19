package listeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import helpers.main.AppHelper;
import helpers.other.ReadAndWriteToFile;

/**
 * Created by Spaja on 15-Feb-17.
 */

public class SaveImageToFileClickListener implements View.OnClickListener {

    private Context context;
    private RelativeLayout relativeLayout;

    public SaveImageToFileClickListener(Context context, RelativeLayout relativeLayout) {
        this.context = context;
        this.relativeLayout = relativeLayout;
    }

    @Override
    public void onClick(View view) {
        relativeLayout.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(relativeLayout.getDrawingCache());

        if (bm != null) {

            ReadAndWriteToFile.saveImage(bm, context);
            Toast toast = Toast.makeText(context, "QuoteActivity saved",
                    Toast.LENGTH_LONG);
            toast.show();
            relativeLayout.setDrawingCacheEnabled(false);

        } else {
            AppHelper.showToast("Something went wrong!", context);
        }
    }
}
