package listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Spaja on 16-Feb-17.
 */

public class ShareAsTextClickListener implements MaterialDialog.SingleButtonCallback  {

    private Context context;
    private String mQuoteText;
    private String mAuthorName;

    public ShareAsTextClickListener(Context context, String quoteText, String authorName) {
        this.context = context;
        this.mQuoteText = quoteText;
        this.mAuthorName = authorName;
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

        shareText();

    }

    private void shareText() {

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "QuoteActivity");
        share.putExtra(Intent.EXTRA_TEXT, "\"" + mQuoteText + "\""+ "\n\n" + "- "
                + mAuthorName + " -" + "\n\nwww.quotetab.com");
        context.startActivity(Intent.createChooser(share, "Share via:"));

    }


}

