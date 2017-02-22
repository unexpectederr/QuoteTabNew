package listeners;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import helpers.main.AppHelper;

/**
 * Created by unexpected_err on 11/01/2017.
 */

public class OnSearchAuthorsClickListener implements View.OnClickListener {

    private EditText mSearchEditText;
    private TextView mScreenTitle;
    private Context mContext;

    public OnSearchAuthorsClickListener(EditText searchEditText, TextView screenTitle, Context context) {
        mSearchEditText = searchEditText;
        mScreenTitle = screenTitle;
        mContext = context;
    }

    @Override
    public void onClick(View view) {
        if (mSearchEditText.getText().length() == 0) {
            mSearchEditText.setVisibility(View.VISIBLE);
            mSearchEditText.requestFocus();
            mSearchEditText.startAnimation(AppHelper.getAnimationUp(mContext));
            mScreenTitle.setVisibility(View.GONE);
        } else {
            mSearchEditText.setText("");
        }
    }
}
