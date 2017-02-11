package listeners;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import activities.quotetabnew.R;

import adapters.DashboardAuthorAdapter;
import helpers.main.AppHelper;
import helpers.other.ExpandableFrame;
import models.dashboard.DashboardData;
import models.dashboard.PopularAuthor;


/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnShowDashboardMoreListener implements View.OnClickListener {

    Activity mActivity;
    DashboardData mData;

    public OnShowDashboardMoreListener(Activity activity, DashboardData data) {
        this.mActivity = activity;
        this.mData = data;
    }

    @Override
    public void onClick(View view) {

        View dashboardMore = mActivity.findViewById(R.id.dashboard_more);
        ImageView closeBtn = (ImageView) dashboardMore.findViewById(R.id.close_info);

        AppHelper.revealLayout(dashboardMore, view, closeBtn);

        bindDashboardMore();

        initializePopularAuthors();
        initializeTodayBirthdays();
        initializeTrendingAuthors();
    }

    private void bindDashboardMore() {

        LinearLayout dashboardMoreMain = (LinearLayout) mActivity.findViewById(R.id.dashboard_more_main);

        LinearLayout popularAuthors = (LinearLayout) mActivity.findViewById(R.id.popular_authors_btn);
        popularAuthors.setOnClickListener(new ExpandClickListener(dashboardMoreMain));

        LinearLayout todayBirthdays = (LinearLayout) mActivity.findViewById(R.id.today_birthdays_btn);
        todayBirthdays.setOnClickListener(new ExpandClickListener(dashboardMoreMain));

        LinearLayout trendingAuthors = (LinearLayout) mActivity.findViewById(R.id.trending_authors_btn);
        trendingAuthors.setOnClickListener(new ExpandClickListener(dashboardMoreMain));
    }

    private class ExpandClickListener implements View.OnClickListener {

        LinearLayout mDashboardMoreMain;

        public ExpandClickListener(LinearLayout dashboardMoreMain) {
            mDashboardMoreMain = dashboardMoreMain;
        }

        @Override
        public void onClick(View view) {

            for (int i = 0; mDashboardMoreMain.getChildCount() > i; i++) {

                if (mDashboardMoreMain.getChildAt(i) instanceof ExpandableFrame) {

                    if (mDashboardMoreMain.getChildAt(i).getTag().equals(view.getTag())) {

                        if (((ExpandableFrame) mDashboardMoreMain.getChildAt(i)).isExpanded()) {
                            AppHelper.collapse(mDashboardMoreMain.getChildAt(i), 600, 0);
                            ((ExpandableFrame) mDashboardMoreMain.getChildAt(i)).setExpanded(false);
                        } else {
                            AppHelper.expand(mDashboardMoreMain.getChildAt(i), 600, 1300);
                            ((ExpandableFrame) mDashboardMoreMain.getChildAt(i)).setExpanded(true);
                        }

                    } else {

                        if (((ExpandableFrame) mDashboardMoreMain.getChildAt(i)).isExpanded()) {
                            AppHelper.collapse(mDashboardMoreMain.getChildAt(i), 600, 0);
                            ((ExpandableFrame) mDashboardMoreMain.getChildAt(i)).setExpanded(false);
                        }
                    }
                }
            }
        }
    }

    private void initializePopularAuthors() {

        RecyclerView mRecyclerView = (RecyclerView) mActivity.findViewById(R.id.popular_authors);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(mActivity, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DashboardAuthorAdapter mAdapter = new DashboardAuthorAdapter(mData.getPopularAuthors(), mActivity);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initializeTodayBirthdays() {

        ArrayList<PopularAuthor> todayBirthdays = new ArrayList<>();

        for (int i = 0; i < mData.getTodaysBirthdays().size(); i++) {

            PopularAuthor author = new PopularAuthor();
            author.setAuthorId(mData.getTodaysBirthdays().get(i).getId());
            author.setImageUrl(mData.getTodaysBirthdays().get(i).getAuthorFields().getImageUrl());
            author.setName(mData.getTodaysBirthdays().get(i).getAuthorFields().getName());

            todayBirthdays.add(author);
        }

        RecyclerView birthdaysRecycler = (RecyclerView) mActivity.findViewById(R.id.today_birthdays);
        birthdaysRecycler.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(mActivity, 4);
        birthdaysRecycler.setLayoutManager(manager);

        DashboardAuthorAdapter adapter = new DashboardAuthorAdapter(todayBirthdays, mActivity);
        birthdaysRecycler.setAdapter(adapter);
    }

    private void initializeTrendingAuthors() {

        ArrayList<PopularAuthor> trendingAuthors = new ArrayList<>();

        for (int i = 0; i < mData.getTrendingAuthors().size(); i++) {

            PopularAuthor author = new PopularAuthor();
            author.setAuthorId(mData.getTrendingAuthors().get(i).getId());
            author.setImageUrl(mData.getTrendingAuthors().get(i).getAuthorFields().getImageUrl());
            author.setName(mData.getTrendingAuthors().get(i).getAuthorFields().getName());

            trendingAuthors.add(author);
        }

        RecyclerView trendingAuthorsRecycler = (RecyclerView) mActivity.findViewById(R.id.trending_authors);
        trendingAuthorsRecycler.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(mActivity, 4);
        trendingAuthorsRecycler.setLayoutManager(manager);

        DashboardAuthorAdapter adapter = new DashboardAuthorAdapter(trendingAuthors, mActivity);
        trendingAuthorsRecycler.setAdapter(adapter);
    }
}
