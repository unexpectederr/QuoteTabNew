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
import helpers.main.Constants;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import models.authors.AuthorFieldsFromQuote;

/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnShowDashboardMoreListener implements View.OnClickListener {

    Activity mActivity;

    public OnShowDashboardMoreListener(Activity activity) {
        this.mActivity = activity;
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

        //bindAuthorInfo();
    }

    private void bindAuthorInfo() {


    }
}
