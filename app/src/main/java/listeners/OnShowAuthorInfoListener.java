package listeners;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import activities.quotetabnew.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.AppHelper;
import helpers.main.Constants;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import models.authors.AuthorFieldsFromQuote;

/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnShowAuthorInfoListener implements View.OnClickListener {

    Activity mActivity;
    AuthorFieldsFromQuote mDetailsFromQuote;

    public OnShowAuthorInfoListener(Activity activity, AuthorFieldsFromQuote detailsFromQuote) {
        this.mActivity = activity;
        this.mDetailsFromQuote = detailsFromQuote;
    }

    @Override
    public void onClick(View view) {

        View authorInfo = mActivity.findViewById(R.id.author_info);
        ImageView closeInfo = (ImageView) authorInfo.findViewById(R.id.close_info);

        AppHelper.revealLayout(authorInfo, view, closeInfo);

        bindAuthorInfo();
    }

    private void bindAuthorInfo() {

        TextView infoText = (TextView) mActivity.findViewById(R.id.info_text);
        TextView infoTextName = (TextView) mActivity.findViewById(R.id.info_text_name);
        TextView infoTextBirthday = (TextView) mActivity.findViewById(R.id.info_text_birthday);
        TextView infoTextBirthplace = (TextView) mActivity.findViewById(R.id.info_text_birthplace);
        CircleImageView infoAuthorImage = (CircleImageView) mActivity.findViewById(R.id.info_author_image);

        infoText.setText(mDetailsFromQuote.getDescription());
        infoTextName.setText(mDetailsFromQuote.getAuthorName());
        infoTextBirthplace.setText(mDetailsFromQuote.getBirthplace());
        infoTextBirthday.setText("Born on " + mDetailsFromQuote.getBornDay() + "."
                + mDetailsFromQuote.getBornMonth()
                + "."
                + mDetailsFromQuote.getBornYear());

        Glide.with(mActivity)
                .load(Constants.IMAGES_URL + mDetailsFromQuote.getAuthorImageUrl())
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(infoAuthorImage);
    }
}
