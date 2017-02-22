package listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnShareClickListener implements View.OnClickListener {

    private Context context;
    private String quoteText;
    private String authorName;
    private RelativeLayout relativeLayout;

    public OnShareClickListener(Context context, String quoteText, String authorName, RelativeLayout relativeLayout) {
        this.context = context;
        this.quoteText = quoteText;
        this.authorName = authorName;
        this.relativeLayout = relativeLayout;
    }

    @Override
    public void onClick(View v) {

        dialogBox();
    }

    private void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Share Quote as:");
        alertDialogBuilder.setPositiveButton("Image",
                new ShareImageClickListener(context, relativeLayout));

        alertDialogBuilder.setNegativeButton("Text",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_SUBJECT, "QuoteActivity");
                        share.putExtra(Intent.EXTRA_TEXT, "\"" + quoteText + "\""+ "\n\n" + "- "  + authorName +
                                " -" + "\n\nwww.quotetab.com");
                        context.startActivity(Intent.createChooser(share, "Share via:"));
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
