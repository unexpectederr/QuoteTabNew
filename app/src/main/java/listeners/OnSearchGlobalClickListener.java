package listeners;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import digitalbath.quotetab.R;
import helpers.main.AppHelper;

/**
 * Created by unexpected_err on 11/01/2017.
 */

public class OnSearchGlobalClickListener implements View.OnClickListener {

    private EditText mSearchEditText;
    private Activity mActivity;

    public OnSearchGlobalClickListener(EditText searchEditText, Activity context) {
        mSearchEditText = searchEditText;
        mActivity = context;
    }

    @Override
    public void onClick(View view) {

        View dashboardMore = mActivity.findViewById(R.id.search_bcg);

        if (mSearchEditText.getVisibility() == View.VISIBLE) {

            mSearchEditText.setVisibility(View.GONE);
            AppHelper.revealLayout(dashboardMore, view, null, true);

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSearchEditText.setVisibility(View.VISIBLE);

                }
            }, 600);

            AppHelper.revealLayout(dashboardMore, view, null, false);

        }

        /*if (mSearchEditText.getText().length() == 0) {
            mSearchEditText.setVisibility(View.VISIBLE);
            mSearchEditText.requestFocus();
            mSearchEditText.startAnimation(AppHelper.getAnimationUp(mContext));
        } else {
            mSearchEditText.setText("");
        }*/


    }
}
