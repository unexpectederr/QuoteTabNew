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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import adapters.AuthorDetailsAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetabnew.R;
import helpers.AppHelper;
import helpers.Constants;
import models.authors.AuthorFieldsFromQuote;
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

    private RelativeLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    RecyclerView quotesRecycler;
    CircleImageView authorImage;
    TextView authorTitle, authorTagLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);

        initializeVariables();

        String authorID = getIntent().getStringExtra(Constants.AUTHOR_ID);

        QuoteTabApi.quoteTabApi.getQuotes("a").enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                quotesRecycler.setLayoutManager(new LinearLayoutManager(AuthorDetails.this));
                AuthorDetailsAdapter adapter = new AuthorDetailsAdapter(response.body(), AuthorDetails.this);
                quotesRecycler.setAdapter(adapter);

                AuthorFieldsFromQuote detailsFromQuote = response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote();

                mTitle.setText(detailsFromQuote.getAuthorName());
                authorTitle.setText(detailsFromQuote.getAuthorName());
                authorTagLine.setText(detailsFromQuote.getProfession().getProfessionName() + " - "
                        + detailsFromQuote.getBirthplace());

                Glide.with(AuthorDetails.this)
                        .load(Constants.IMAGES_URL + detailsFromQuote.getAuthorImageUrl())
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar).into(authorImage);

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
        mTitleContainer = (RelativeLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        quotesRecycler = (RecyclerView) findViewById(R.id.author_details_recyclerView);

        ImageView coverImage = (ImageView) findViewById(R.id.cover_image);
        Glide.with(AuthorDetails.this).load("https://lh3.googleusercontent.com/-NnaUBvaHFeQ/VYa2yvBGIxI/AAAAAAAAdhY/qSpaK9ubPWY/w2048-h1152/4K-Wallpaper-Pack-Smartphone-Tablet-Android-Apple-Notebook-Windows-21.jpg")
                .error(R.drawable.avatar).into(coverImage);

        authorTitle = (TextView) findViewById(R.id.author_name);
        authorTagLine = (TextView) findViewById(R.id.author_tagline);
        authorImage = (CircleImageView) findViewById(R.id.author_image);

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
