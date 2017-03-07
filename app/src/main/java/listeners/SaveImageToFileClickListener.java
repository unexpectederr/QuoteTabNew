package listeners;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import activities.quotes.TopQuotes;
import adapters.QuotesAdapter;
import digitalbath.quotetab.R;
import helpers.main.AppHelper;
import helpers.main.Constants;

/**
 * Created by Spaja on 15-Feb-17.
 */

public class SaveImageToFileClickListener implements View.OnClickListener {

    private Activity context;
    private RelativeLayout relativeLayout;
    private HorizontalScrollView tags;
    private LinearLayout actionButtons;
    private RecyclerView.Adapter mAdapter;

    public SaveImageToFileClickListener(Activity context, RelativeLayout relativeLayout,
                                        HorizontalScrollView tags, LinearLayout actionButtons,
                                        RecyclerView.Adapter adapter) {
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.tags = tags;
        this.actionButtons = actionButtons;
        this.mAdapter = adapter;
    }

    @Override
    public void onClick(View view) {

        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            if (mAdapter != null) {

                ((QuotesAdapter) mAdapter).setActiveDownloadButton((ImageView) relativeLayout
                        .findViewById(R.id.download_icon));
            }

            ActivityCompat.requestPermissions(context,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.SAVE_IMAGE_PERMISSION);

        } else {

            AppHelper.createAndSaveImage(context, relativeLayout, tags, actionButtons);

        }
    }
}
