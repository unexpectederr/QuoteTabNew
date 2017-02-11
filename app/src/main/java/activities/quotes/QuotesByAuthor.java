package activities.quotes;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import adapters.QuotesAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import listeners.OnShowAuthorInfoListener;
import listeners.OnWikipediaButtonClickListener;
import models.authors.AuthorDetails;
import models.authors.AuthorDetailsFromQuote;
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
    private LinearLayoutManager manager;
    private int visibleItemCount, totalItemCount, pastVisibleItems, page = 1;
    private boolean loading = false;
    private QuotesAdapter adapter;
    private ImageView favoriteIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_by_author);

        initializeContent();

        final String authorId = getIntent().getStringExtra(Constants.AUTHOR_ID);

        getQuotesByAuthor(authorId, page);

        ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile
                .getFavoriteQuotes(QuotesByAuthor.this);

        adapter = new QuotesAdapter(QuotesByAuthor.this,
                new ArrayList<Quote>(), favoriteQuotes, false, true);

        RecyclerView quotesRecycler = (RecyclerView)
                findViewById(R.id.author_details_recyclerView);

        quotesRecycler.setLayoutManager(manager);
        quotesRecycler.setAdapter(adapter);

        quotesRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisibleItems = manager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisibleItems) >= (totalItemCount - 3)) {
                            loading = true;
                            findViewById(R.id.progress_bar_quotes_by_author).setVisibility(View.VISIBLE);
                            page++;
                            getQuotesByAuthor(authorId, page);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

    }

    private void initializeContent() {

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (RelativeLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
        manager = new LinearLayoutManager(this);
        favoriteIcon = (ImageView) findViewById(R.id.favorite_icon_author);

        mAppBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

    }

    private void getQuotesByAuthor(String authorId, final int page) {

        QuoteTabApi.quoteTabApi.getQuotes(authorId, page).enqueue(new Callback<Quotes>() {

            @Override
            public void onResponse(Call<Quotes> call, Response<Quotes> response) {


                AuthorDetailsFromQuote authorFromQuote = response.body().getAuthorDetailsFromQuote();

//                AuthorDetails author = new AuthorDetails();
//                author.setFavorite(authorFromQuote.isFavorite());
//                author.setId(authorFromQuote.getAuthorFieldsFromQuote().getAuthorId());

                //napraviti novi authordetails objekat od authordetailsfromquote
                //proslijeiti u author i napraviti adapter
                //author.setAuthorFields(authorFromQuote.getAuthorFieldsFromQuote());

                //favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener());

                adapter.addQuotes(response.body().getQuotes());
                loading = false;

                findViewById(R.id.progress_bar_quotes_by_author).setVisibility(View.GONE);
                findViewById(R.id.progress_bar).setVisibility(View.GONE);

                if (page == 1) {

                    bindAuthorHeader(authorFromQuote);

                    ImageView infoIcon = (ImageView) findViewById(R.id.info_icon);
                    infoIcon.setOnClickListener(new OnShowAuthorInfoListener
                            (QuotesByAuthor.this, authorFromQuote.getAuthorFieldsFromQuote()));
                }
            }

            @Override
            public void onFailure(Call<Quotes> call, Throwable t) {
                findViewById(R.id.progress_bar_quotes_by_author).setVisibility(View.GONE);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener(this);

    }

    private void bindAuthorHeader(AuthorDetailsFromQuote authorFromQuote) {

        ArrayList<AuthorDetails> favoriteAuthors = ReadAndWriteToFile
                .getFavoriteAuthors(QuotesByAuthor.this);

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (authorFromQuote.getAuthorFieldsFromQuote()
                    .getAuthorId().equals(favoriteAuthors.get(i).getId())) {

                authorFromQuote.setFavorite(true);
                favoriteIcon.setImageResource(R.drawable.ic_author);

            }
        }

        TextView authorTitle = (TextView) findViewById(R.id.author_name);
        TextView authorTagLine = (TextView) findViewById(R.id.author_tagline);

        mTitle.setText(authorFromQuote.getAuthorFieldsFromQuote().getAuthorName());

        authorTitle.setText(authorFromQuote.getAuthorFieldsFromQuote().getAuthorName());

        if (authorFromQuote.getAuthorFieldsFromQuote().getProfession() != null) {
            authorTagLine.setText(authorFromQuote.getAuthorFieldsFromQuote()
                    .getProfession().getProfessionName() + " - "
                    + authorFromQuote.getAuthorFieldsFromQuote().getBirthplace());
        } else {
            authorTagLine.setText("Unknown");
        }

        CircleImageView mAuthorImage = (CircleImageView) findViewById(R.id.author_image);

        Glide.with(QuotesByAuthor.this)
                .load(Constants.IMAGES_URL + authorFromQuote
                        .getAuthorFieldsFromQuote().getAuthorImageUrl())
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(mAuthorImage);

        mAppBarLayout.setVisibility(View.VISIBLE);
        Animation animTwo = AnimationUtils.loadAnimation(QuotesByAuthor.this, R.anim.pop_up_two);
        mAppBarLayout.startAnimation(animTwo);

        Animation animOne = AnimationUtils.loadAnimation(QuotesByAuthor.this, R.anim.pop_up_one);
        mAuthorImage.startAnimation(animOne);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
