package listeners;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import digitalbath.authors.Authors;
import helpers.AppHelper;

/**
 * Created by unexpected_err on 11/01/2017.
 */

public class OnSearchIconClickListener implements View.OnClickListener {

    EditText mSearchEditText;
    Context mContext;

    public OnSearchIconClickListener(EditText searchEditText, Context context) {
        mSearchEditText =searchEditText;
        mContext = context;
    }

    @Override
    public void onClick(View view) {
        if (mSearchEditText.getText().length() == 0) {
            mSearchEditText.setVisibility(View.VISIBLE);
            mSearchEditText.requestFocus();
            mSearchEditText.setAnimation(AppHelper.getAnimationUp(mContext));
        } else {
            mSearchEditText.setText("");
        }
    }
}
