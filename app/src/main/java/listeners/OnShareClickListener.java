package listeners;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import digitalbath.quotetab.R;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnShareClickListener implements View.OnClickListener {

    private Activity mContext;
    private String mQuoteText;
    private String mAuthorName;
    private String mQuoteImageUrl;
    private TextView mQuoteTextView;

    public OnShareClickListener(Activity context, String quoteText, String authorName,
                                String quoteImage, TextView lineCount) {
        this.mContext = context;
        this.mQuoteText = quoteText;
        this.mAuthorName = authorName;
        this.mQuoteImageUrl = quoteImage;
        this.mQuoteTextView = lineCount;
    }

    @Override
    public void onClick(View v) {

        dialogBox();
    }

    private void dialogBox() {

        MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(mContext);

        dialog.setTitle("Share QuoteReference!");
        dialog.setDescription("Please choose an option how to share a quote:");

        dialog.setIcon(R.drawable.logo_4);

        dialog.setPositiveText("AS IMAGE");
        dialog.onPositive(new ShareAsImageClickListener(mContext, mQuoteText, mAuthorName,
                mQuoteImageUrl, mQuoteTextView.getLineCount()));

        dialog.setNeutralText("AS TEXT");
        dialog.onNeutral(new ShareAsTextClickListener(mContext, mQuoteText, mAuthorName));

        dialog.show();

    }

}
