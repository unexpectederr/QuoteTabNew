package listeners;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import activities.quotes.TopQuotes;
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
    private static final int MY_REQUEST_PERMISSION_WRITE_TO_EXTERNAL_MEMORY = 0;

    public SaveImageToFileClickListener(Context context, RelativeLayout relativeLayout,
                                        HorizontalScrollView tags, LinearLayout actionButtons) {
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.tags = tags;
        this.actionButtons = actionButtons;
    }

    @Override
    public void onClick(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(((TopQuotes)context),
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_REQUEST_PERMISSION_WRITE_TO_EXTERNAL_MEMORY);

        } else {

            AppHelper.createAndSaveImage(context, relativeLayout, tags, actionButtons);
        }
    }
}
