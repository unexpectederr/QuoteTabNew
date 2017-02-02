package activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.FirebaseMessaging;
import com.liangfeizc.RubberIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import activities.authors.Authors;
import activities.authors.FavoriteAuthors;
import activities.quotes.FavoriteQuotes;
import activities.quotes.QuotesByTag;
import activities.quotes.TopQuotes;
import activities.quotetabnew.R;
import activities.topics.Topics;
import adapters.DashboardPagerAdapter;
import adapters.PoplarAuthorsAdapter;
import adapters.QuotesAdapter;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.ParallaxPageTransformer;
import helpers.other.ReadAndWriteToFile;
import models.dashboard.DashboardData;
import models.dashboard.PopularAuthor;
import models.dashboard.TopPhotos;
import models.quotes.Quote;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int rubberOldPosition;
    ViewPager mPager;
    ArrayList<TopPhotos> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeContent();

        FirebaseMessaging.getInstance().subscribeToTopic("quote-of-the-day");

    }

    private void initializeContent() {

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

    private void initializeDashboard(ArrayList<TopPhotos> items) {

        mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setOffscreenPageLimit(3);

        ParallaxPageTransformer pageTransformer = new ParallaxPageTransformer()
                .addViewToParallax(new ParallaxPageTransformer
                        .ParallaxTransformInformation(R.id.quote, 1.2f, 1.9f))
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
        DashboardPagerAdapter mPagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), items, favoriteQuotes);
        mPager.setAdapter(mPagerAdapter);

        final RubberIndicator mRubberIndicator = (RubberIndicator) findViewById(R.id.rubber);
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

                items = new ArrayList<>();

                for (int i = 0; response.body().getTopPhotos().size() > i; i++) {

                    items.add(response.body().getTopPhotos().get(i));
                }

                initializeDashboard(items);

                //initializePopularAuthors(response.body().getPopularAuthors());
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                int ad = 9;
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
                Constants.NUMBER_OF_COVERS), navImage, true);

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
                            Constants.NUMBER_OF_COVERS), null, true);
                }
            }
        }, 4000);
    }

    private void initializePopularAuthors(List<PopularAuthor> popularAuthorsList) {

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.popular_authors);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        PoplarAuthorsAdapter mAdapter = new PoplarAuthorsAdapter(popularAuthorsList, Dashboard.this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_authors) {
            Intent i = new Intent(Dashboard.this, Authors.class);
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
            Intent i = new Intent(Dashboard.this, FavoriteAuthors.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == FavoriteQuotes.RESULT_OK) {

                ArrayList<Quote> favoriteQuotes = (ArrayList<Quote>) data.getSerializableExtra("result");
                DashboardPagerAdapter mPagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), items, favoriteQuotes);
                mPager.setAdapter(mPagerAdapter);

            } else if (resultCode == QuotesByTag.RESULT_CANCELED) {

                AppHelper.showToast("Something went wrong", this);

            }
        }
    }
}
