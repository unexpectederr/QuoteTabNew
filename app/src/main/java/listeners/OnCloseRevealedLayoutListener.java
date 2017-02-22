package listeners;

import android.view.View;

import helpers.main.AppHelper;

/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnCloseRevealedLayoutListener implements View.OnClickListener {

    private View viewToUnReveal;
    private int cx;
    private int cy;
    private float finalRadius;

    public OnCloseRevealedLayoutListener(View viewToUnReveal, int cx, int cy, float finalRadius) {
        this.viewToUnReveal = viewToUnReveal;
        this.cx = cx;
        this.cy = cy;
        this.finalRadius = finalRadius;
    }

    @Override
    public void onClick(final View view) {

        AppHelper.unRevealLayout(viewToUnReveal, cx, cy, finalRadius);
    }
}
