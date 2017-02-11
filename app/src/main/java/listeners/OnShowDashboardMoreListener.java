package listeners;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import activities.quotetabnew.R;

import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;


/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnShowDashboardMoreListener implements View.OnClickListener {

    Activity mActivity;
    boolean ex;

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

        bindDashboardMore();
    }

    private void bindDashboardMore() {

        LinearLayout popularAuthors = (LinearLayout) mActivity.findViewById(R.id.popular_authors_btn);
        final FrameLayout expandableLayout
                = (FrameLayout) mActivity.findViewById(R.id.popular_authors_expandable);

        popularAuthors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ex) {
                    collapse(expandableLayout, 1000, 0);
                    ex = false;
                } else {
                    expand(expandableLayout, 1000, 600);
                    ex = true;
                }
            }
        });

    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }
}
