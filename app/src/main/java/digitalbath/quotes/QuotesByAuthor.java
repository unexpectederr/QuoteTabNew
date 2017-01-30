package digitalbath.quotes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import adapters.QuotesAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetabnew.R;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import helpers.other.RotatingImages;
import models.authors.AuthorFieldsFromQuote;
import models.quotes.Quote;
import models.quotes.Quotes;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotesByAuthor extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private RelativeLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private ArrayList<Quote> favoriteQuotes;
    private CircleImageView mAuthorImage;
    RecyclerView quotesRecycler;
    TextView authorTitle, authorTagLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_by_author);

        initializeContent();

        String authorId = getIntent().getStringExtra(Constants.AUTHOR_ID);

        getQuotesByAuthor(authorId);

    }

    private void initializeContent() {

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (RelativeLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        mAuthorImage = (CircleImageView) findViewById(R.id.author_image);

        quotesRecycler = (RecyclerView) findViewById(R.id.author_details_recyclerView);


        authorTitle = (TextView) findViewById(R.id.author_name);
        authorTagLine = (TextView) findViewById(R.id.author_tagline);

        favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

        mAppBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        /*bornIn = (TextView) findViewById(R.id.born_in);
        profession = (TextView) findViewById(R.id.profession);
        country = (TextView) findViewById(R.id.country);*/

    }

    private void getQuotesByAuthor(String authorId) {

        QuoteTabApi.quoteTabApi.getQuotes(authorId).enqueue(new Callback<Quotes>() {
            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {

                quotesRecycler.setLayoutManager(new LinearLayoutManager(QuotesByAuthor.this));
                QuotesAdapter adapter = new QuotesAdapter(QuotesByAuthor.this, response.body().getQuotes(), favoriteQuotes, false, true);
                quotesRecycler.setAdapter(adapter);

                AuthorFieldsFromQuote detailsFromQuote = response.body().getAuthorDetailsFromQuote().getAuthorFieldsFromQuote();

                mTitle.setText(detailsFromQuote.getAuthorName());
                authorTitle.setText(detailsFromQuote.getAuthorName());
                if (detailsFromQuote.getProfession() != null) {
                    authorTagLine.setText(detailsFromQuote.getProfession().getProfessionName() + " - "
                            + detailsFromQuote.getBirthplace());
                } else {
                    authorTagLine.setText("Unknown");
                }

                Glide.with(QuotesByAuthor.this)
                        .load(Constants.IMAGES_URL + detailsFromQuote.getAuthorImageUrl())
                        .dontAnimate()
                        .placeholder(R.drawable.avatar)
                        .error(R.drawable.avatar)
                        .into(mAuthorImage);

                mAppBarLayout.setVisibility(View.VISIBLE);
                Animation animTwo = AnimationUtils.loadAnimation(QuotesByAuthor.this, R.anim.pop_up_two);
                mAppBarLayout.startAnimation(animTwo);

                Animation animOne = AnimationUtils.loadAnimation(QuotesByAuthor.this, R.anim.pop_up_one);
                mAuthorImage.startAnimation(animOne);

                findViewById(R.id.progress_bar).setVisibility(View.GONE);

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


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {

        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!mIsTheTitleVisible) {
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
            if (mIsTheTitleContainerVisible) {
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

    public static void startAlphaAnimation(View v, long duration, int visibility) {

        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
