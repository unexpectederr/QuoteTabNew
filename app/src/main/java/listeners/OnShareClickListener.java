package listeners;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnShareClickListener implements View.OnClickListener {

    private Context context;
    private String quoteText;
    private String authorName;

    public OnShareClickListener(Context context, String quoteText, String authorName) {
        this.context = context;
        this.quoteText = quoteText;
        this.authorName = authorName;
    }

    @Override
    public void onClick(View v) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "Quote");
        share.putExtra(Intent.EXTRA_TEXT, "\"" + quoteText + "\""+ "\n\n" + "- "  + authorName +
                " -" + "\n\nwww.quotetab.com");
        context.startActivity(Intent.createChooser(share, "Share via:"));
    }

}
