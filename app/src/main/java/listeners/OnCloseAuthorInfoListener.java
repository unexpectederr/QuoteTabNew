package listeners;

import android.animation.Animator;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;

/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnCloseAuthorInfoListener implements View.OnClickListener{

    View authorInfo;
    int cx;
    int cy;
    float finalRadius;

    public OnCloseAuthorInfoListener(View authorInfo, int cx, int cy, float finalRadius) {
        this.authorInfo = authorInfo;
        this.cx = cx;
        this.cy = cy;
        this.finalRadius = finalRadius;
    }

    @Override
    public void onClick(View view) {

        Animator animatorReverse = ViewAnimationUtils.createCircularReveal(authorInfo, cx, cy, finalRadius, 0);
        animatorReverse.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorReverse.setDuration(600);
        animatorReverse.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((RevealFrameLayout) authorInfo.getParent()).setVisibility(View.INVISIBLE);
            }
        }, 600);
    }
}
