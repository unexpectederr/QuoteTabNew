package listeners;

import android.animation.Animator;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import activities.quotes.QuotesByAuthor;
import activities.quotetabnew.R;
import de.hdodenhof.circleimageview.CircleImageView;
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

        final View authorInfo = mActivity.findViewById(R.id.author_info);
        ((RevealFrameLayout) authorInfo.getParent()).setVisibility(View.VISIBLE);

        // get the center for the clipping circle
        final int cx = (view.getLeft() + view.getRight()) / 2;
        final int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, authorInfo.getWidth() - cx);
        int dy = Math.max(cy, authorInfo.getHeight() - cy);
        final float finalRadius = (float) Math.hypot(dx, dy);

        // Android native animator
        final Animator animator =
                ViewAnimationUtils.createCircularReveal(authorInfo, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(600);
        animator.start();

        ImageView closeInfo = (ImageView) authorInfo.findViewById(R.id.close_info);
        closeInfo.setOnClickListener(new OnCloseAuthorInfoListener(authorInfo, cx, cy, finalRadius));

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
