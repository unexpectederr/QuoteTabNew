package listeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import helpers.main.AppHelper;
import helpers.main.ReadAndWriteToFile;

/**
 * Created by Spaja on 15-Feb-17.
 */

public class SaveImageToFileClickListener implements View.OnClickListener {

    private Context context;
    private RelativeLayout relativeLayout;
    private HorizontalScrollView tags;
    private LinearLayout actionButtons;

    public SaveImageToFileClickListener(Context context, RelativeLayout relativeLayout,
                                        HorizontalScrollView tags, LinearLayout actionButtons) {
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.tags = tags;
        this.actionButtons = actionButtons;
    }

    @Override
    public void onClick(View view) {

        if (tags != null && actionButtons != null) {
            tags.setVisibility(View.GONE);
            actionButtons.setVisibility(View.GONE);
        }
        relativeLayout.setDrawingCacheEnabled(true);
        Bitmap bm = Bitmap.createBitmap(relativeLayout.getDrawingCache());

        if (bm != null) {

            ReadAndWriteToFile.saveImage(bm, context);
            Toast toast = Toast.makeText(context, "Quote saved to Gallery",
                    Toast.LENGTH_LONG);
            toast.show();
            relativeLayout.setDrawingCacheEnabled(false);
            if (tags != null && actionButtons != null) {
                tags.setVisibility(View.VISIBLE);
                actionButtons.setVisibility(View.VISIBLE);
            }

        } else {
            AppHelper.showToast("Something went wrong!", context);
        }
    }
}
