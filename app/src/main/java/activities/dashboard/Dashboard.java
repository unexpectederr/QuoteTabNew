package activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.liangfeizc.RubberIndicator;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.Random;

import activities.authors.Authors;
import activities.authors.PopularAuthors;
import activities.quote_maker.QuoteMaker;
import activities.quotes.FavoriteQuotes;
import activities.quotes.QuotesByTag;
import activities.quotes.TopQuotes;
import digitalbath.quotetab.R;
import activities.topics.Topics;
import adapters.DashboardPagerAdapter;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.main.Mapper;
import helpers.other.ParallaxPageTransformer;
import helpers.main.ReadAndWriteToFile;
import listeners.OnSearchGlobalClickListener;
import listeners.OnShowDashboardMoreListener;
import models.authors.Author;
import models.authors.AuthorDetails;
import models.dashboard.DashboardData;
import models.quotes.Quote;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int rubberOldPosition;
    ViewPager mPager;
    ArrayList<Quote> mItems;
    RubberIndicator mRubberIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_dashboard);

        initializeCommonContent();

        initializeSearchContent();

        FirebaseMessaging.getInstance().subscribeToTopic("quote-of-the-day");

    }

    private void initializeCommonContent() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RelativeLayout splashScreen = (RelativeLayout) findViewById(R.id.splash_screen);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        Glide.with(Dashboard.this).load(R.drawable.splash).into(logo);

        getDashboardData(toolbar, splashScreen);

        preloadImages();

    }

    private void initializeSearchContent() {

        EditText searchQuotetab = (EditText) findViewById(R.id.search_edit_text_dashboard);

        SmoothProgressBar searchProgress = (SmoothProgressBar) findViewById(R.id.smooth_progress_bar);

        ImageView revealPoint = (ImageView) findViewById(R.id.reveal_point);

        RecyclerView searchRecycler = (RecyclerView) findViewById(R.id.search_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        searchRecycler.setLayoutManager(manager);

        ImageView searchIcon = (ImageView) findViewById(R.id.search_icon_dashboard);
        searchIcon.setOnClickListener(new OnSearchGlobalClickListener
                (searchQuotetab, this, searchRecycler, searchProgress, revealPoint));

    }

    private void initializeDashboard(ArrayList<Quote> items) {

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setOffscreenPageLimit(items.size());

        ParallaxPageTransformer pageTransformer = new ParallaxPageTransformer()
                .addViewToParallax(new ParallaxPageTransformer
                        .ParallaxTransformInformation(R.id.quote_text, 1.2f, 1.9f))
                .addViewToParallax(new ParallaxPageTransformer
                        .ParallaxTransformInformation(R.id.author, 0.6f, 1.7f))
                .addViewToParallax(new ParallaxPageTransformer
                        .ParallaxTransformInformation(R.id.author_image, 0.3f, 1.5f))
                .addViewToParallax(new ParallaxPageTransformer
                        .ParallaxTransformInformation(R.id.action_buttons_cont, 0.7f, 1.3f))
                .addViewToParallax(new ParallaxPageTransformer
                        .ParallaxTransformInformation(R.id.backdrop, -1.8f, -1.8f));

        mPager.setPageTransformer(true, pageTransformer);

        ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);
        ArrayList<Author> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(this);
        DashboardPagerAdapter mPagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(),
                items, favoriteQuotes, favoriteAuthors);
        mPager.setAdapter(mPagerAdapter);

        mRubberIndicator = (RubberIndicator) findViewById(R.id.rubber);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) getResources().getDimension(
                R.dimen.rubber_dot_width) * items.size(), (int) getResources().getDimension(R.dimen.rubber_dot_height));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.bottomMargin = (int) getResources().getDimension(R.dimen.rubber_margin);
        lp.leftMargin = (int) getResources().getDimension(R.dimen.rubber_margin);
        mRubberIndicator.setLayoutParams(lp);
        mRubberIndicator.setCount(items.size(), 0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position > rubberOldPosition)
                    mRubberIndicator.moveToRight();
                else
                    mRubberIndicator.moveToLeft();

                rubberOldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getDashboardData(final Toolbar toolbar, final RelativeLayout splashScreen) {

        Call<DashboardData> call = QuoteTabApi.quoteTabApi.getDashboardData();

        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {

                hideSplashScreen(splashScreen, toolbar);

                mItems = Mapper.mapQuotesFromDashboardData(response.body().getQuotesPartial());

                initializeDashboard(mItems);

                ImageView moreIcon = (ImageView) findViewById(R.id.more_icon);
                moreIcon.setOnClickListener(new OnShowDashboardMoreListener(Dashboard.this, response.body()));
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                AppHelper.showToast(getResources().getString(R.string.toast_error_message), Dashboard.this);
            }
        });
    }

    private void hideSplashScreen(final RelativeLayout splashScreen, final Toolbar toolbar) {

        splashScreen.setVisibility(View.GONE);
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(400);
        splashScreen.startAnimation(alpha);

        ImageView navImage = (ImageView) findViewById(R.id.nav_image);
        AppController.loadImageIntoView(Dashboard.this, new Random().nextInt(
                Constants.NUMBER_OF_COVERS), navImage, true, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!PreferenceManager.getDefaultSharedPreferences(Dashboard.this)
                        .getBoolean(Constants.NAVIGATION_TIP, false))
                    AppHelper.showMaterialTip(toolbar.getChildAt(1), Dashboard.this, "Open a navigation menu",
                            "You can find here more quotes, topics and authors", Constants.NAVIGATION_TIP,
                            R.drawable.ic_menu);

            }
        }, 2000);
    }

    private void preloadImages() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; Constants.NUMBER_OF_IMAGES_TO_PRELOAD > i; i++) {
                    AppController.loadImageIntoView(Dashboard.this, new Random().nextInt(
                            Constants.NUMBER_OF_COVERS), null, true, true);
                }
            }
        }, 4000);
    }

    @Override
    public void onBackPressed() {

        if (findViewById(R.id.search_edit_text_dashboard).getVisibility() == View.VISIBLE) {

            ((EditText) findViewById(R.id.search_edit_text_dashboard)).setText("");
            findViewById(R.id.search_icon_dashboard).performClick();

            RecyclerView searchRecycler = (RecyclerView) findViewById(R.id.search_recycler);

            if (searchRecycler.getVisibility() == View.VISIBLE) {
                searchRecycler.setVisibility(View.GONE);
                searchRecycler.startAnimation(AppHelper.getAnimationDown(this));
            }
            return;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_authors) {
            Intent i = new Intent(Dashboard.this, PopularAuthors.class);
            startActivity(i);
        } else if (id == R.id.nav_topics) {
            Intent i = new Intent(Dashboard.this, Topics.class);
            startActivity(i);
        } else if (id == R.id.nav_topQuotes) {
            Intent i = new Intent(Dashboard.this, TopQuotes.class);
            startActivity(i);
        } else if (id == R.id.nav_send) {
            Intent i = new Intent(Dashboard.this, FavoriteQuotes.class);
            startActivityForResult(i, 1);
        } else if (id == R.id.menu_favorite_authors) {
            Intent i = new Intent(Dashboard.this, Authors.class);
            startActivityForResult(i, 1);
        } else if (id == R.id.menu_quote_maker) {
            Intent i = new Intent(Dashboard.this, QuoteMaker.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.gc();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == FavoriteQuotes.RESULT_OK) {

                boolean isFromAuthors = data.getBooleanExtra("isFromAuthors", false);
                ArrayList<Author> favoriteAuthors;
                ArrayList<Quote> favoriteQuotes;

                if (isFromAuthors) {

                    favoriteAuthors = (ArrayList<Author>) data.getSerializableExtra("result");
                    favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(this);

                } else {

                    favoriteQuotes = (ArrayList<Quote>) data.getSerializableExtra("result");
                    favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(this);
                }

                ArrayList<Fragment> fragments = ((DashboardPagerAdapter)
                        mPager.getAdapter()).getRegisteredFragments();

                for (int j = 0; j < fragments.size(); j++) {

                    if (fragments.get(j) != null)
                        ((DashboardFragment) fragments.get(j))
                                .refreshData(favoriteAuthors, favoriteQuotes);

                }

            } else if (resultCode == FavoriteQuotes.RESULT_CANCELED) {

                AppHelper.showToast("Something went wrong", this);

            }
        }
    }
}
