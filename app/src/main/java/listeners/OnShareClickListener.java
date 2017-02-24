package listeners;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import digitalbath.quotetab.R;

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

        MaterialStyledDialog.Builder dialog = new MaterialStyledDialog.Builder(context);

        dialog.setTitle("Share Quote!");
        dialog.setDescription("Please choose an option how to share a quote:");

        dialog.setIcon(R.drawable.logo_3);

        dialog.setPositiveText("AS IMAGE");
        //dialog.onPositive(new ShareAsImageClickListener(context, relativeLayout));

        dialog.setNeutralText("AS TEXT");
        dialog.onNeutral(new ShareAsTextClickListener(context, quoteText, authorName));

        dialog.show();

    }

}
