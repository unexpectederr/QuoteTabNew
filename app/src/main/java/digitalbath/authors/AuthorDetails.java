package digitalbath.authors;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import adapters.AuthorDetailsAdapter;
import digitalbath.quotetabnew.R;
import helpers.Constants;
import models.authors.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorDetails extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    ImageView toolbarAuthorImage;
    RecyclerView authorDetailsRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details_1);

        initializeVariables();

        authorDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String authorID = getIntent().getStringExtra(Constants.AUTHOR_ID);

        QuoteTabApi.quoteTabApi.getQuotes(authorID).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                AuthorDetailsAdapter adapter = new AuthorDetailsAdapter(response.body(), AuthorDetails.this);
                authorDetailsRecyclerView.setAdapter(adapter);

                //authorName = response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getAuthorName();
                //collapsingToolbarLayout.setTitle(authorName);

                //authorImageUrl = response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getAuthorImageUrl();
                Glide.with(AuthorDetails.this).load("https://lh3.googleusercontent.com/-NnaUBvaHFeQ/VYa2yvBGIxI/AAAAAAAAdhY/qSpaK9ubPWY/w2048-h1152/4K-Wallpaper-Pack-Smartphone-Tablet-Android-Apple-Notebook-Windows-21.jpg")
                        .error(R.drawable.avatar).into(toolbarAuthorImage);

                /*bornIn.setText(response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getBirthplace());
                profession.setText(response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote()
                        .getProfession().getProfessionName());
                country.setText(response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote().getCountry());*/
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                int i = 9;
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    private void initializeVariables() {

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);

        //collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbarAuthorImage = (ImageView) findViewById(R.id.cover_image);
        authorDetailsRecyclerView = (RecyclerView) findViewById(R.id.author_details_recyclerView);
        /*bornIn = (TextView) findViewById(R.id.born_in);
        profession = (TextView) findViewById(R.id.profession);
        country = (TextView) findViewById(R.id.country);*/
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
                mToolbar.setBackgroundColor(Color.WHITE);
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
