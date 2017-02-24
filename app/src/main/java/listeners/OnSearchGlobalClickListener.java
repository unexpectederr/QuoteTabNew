package listeners;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import activities.dashboard.Dashboard;
import adapters.SearchAdapter;
import digitalbath.quotetab.R;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import helpers.main.AppController;
import helpers.main.AppHelper;
import models.search.SearchResponse;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by unexpected_err on 11/01/2017.
 */

public class OnSearchGlobalClickListener implements View.OnClickListener {

    private EditText mSearchEditText;
    private Activity mActivity;
    private RecyclerView mRecyclerView;
    private SmoothProgressBar mSearchProgress;
    private ImageView mRevealPoint;

    public OnSearchGlobalClickListener(EditText editText, Activity context,
                                       RecyclerView recyclerView, SmoothProgressBar progress,
                                       ImageView revealPoint) {
        mSearchEditText = editText;
        mActivity = context;
        mRecyclerView = recyclerView;
        mSearchProgress = progress;
        mRevealPoint = revealPoint;
    }

    @Override
    public void onClick(View view) {

        View dashboardMore = mActivity.findViewById(R.id.search_bcg);

        if (mSearchEditText.getVisibility() == View.VISIBLE) {

            if (TextUtils.isEmpty(mSearchEditText.getText().toString())) {

                mSearchEditText.setVisibility(View.GONE);
                AppHelper.revealLayout(dashboardMore, mRevealPoint, null, true);

            } else {

                getSearchResults(mSearchEditText.getText().toString());

            }

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSearchEditText.setVisibility(View.VISIBLE);
                    mSearchEditText.startAnimation(AppHelper.getAnimationUp(mActivity));

                }
            }, 600);

            AppHelper.revealLayout(dashboardMore, mRevealPoint, null, false);

        }

        /*if (mSearchEditText.getText().length() == 0) {
            mSearchEditText.setVisibility(View.VISIBLE);
            mSearchEditText.requestFocus();
            mSearchEditText.startAnimation(AppHelper.getAnimationUp(mContext));
        } else {
            mSearchEditText.setText("");
        }*/


    }

    private void getSearchResults(String query) {

        mSearchProgress.setVisibility(View.VISIBLE);

        QuoteTabApi.quoteTabApi.getSearchResults(true, true, true, query)
                .enqueue(new Callback<SearchResponse>() {

                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        SearchAdapter adapter = new SearchAdapter(response.body(), mActivity);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.startAnimation(AppHelper.getAnimationUp(mActivity));
                        mSearchProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        mSearchProgress.setVisibility(View.GONE);
                        AppHelper.showToast("Oops! Something went wrong", mActivity);
                    }
                });
    }
}
