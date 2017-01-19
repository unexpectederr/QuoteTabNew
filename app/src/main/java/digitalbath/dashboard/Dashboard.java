package digitalbath.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

import digitalbath.authors.Authors;
import digitalbath.quotetabnew.R;
import digitalbath.topics.Topics;
import helpers.other.ParallaxPageTransformer;
import models.dashboard.DashboardData;
import models.dashboard.DashboardItem;
import models.dashboard.PopularAuthor;
import networking.QuoteTabApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int rubberOldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeContent();

        getDashboardData();

        FirebaseMessaging.getInstance().subscribeToTopic("quote-of-the-day");

    }

    private void initializeContent() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RelativeLayout splashScreen = (RelativeLayout) findViewById(R.id.splash_screen);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        Glide.with(Dashboard.this).load(R.drawable.splash).into(logo);
        hideSplashScreen(splashScreen);

        initializeDashboard();

    }

    private void hideSplashScreen(final RelativeLayout splashScreen) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.setVisibility(View.GONE);
                AlphaAnimation alpha = new AlphaAnimation(1, 0);
                alpha.setDuration(400);
                splashScreen.startAnimation(alpha);
            }
        }, 1200);
    }

    private void initializeDashboard() {

        ArrayList<DashboardItem> items = new ArrayList<>();
        items.add(new DashboardItem("The sun, the earth, love, friends, our very breath are parts of the banquet", "Rebecca H. Davis", "http://www.planwallpaper.com/static/images/nature-wallpapers-hd.jpg"));
        items.add(new DashboardItem("It is costly wisdom that is bought by experience.", "Roger Ascham", "http://cdn.pcwallart.com/images/canada-city-houses-wallpaper-3.jpg"));
        items.add(new DashboardItem("Yesterday is a cancelled check. Today is cash on the line. Tomorrow is a promissory note.", "Hank Stram", "http://i.imgur.com/eWtfMME.png"));
        items.add(new DashboardItem("Success is not forever and failure isn't fatal.", "Don Shula", "https://www.planwallpaper.com/static/images/HD-Wallpapers1.jpeg"));
        items.add(new DashboardItem("Happiness is when what you think, what you say, and what you do are in harmony.", "Mahatma Gandhi", "http://www.sz0931.com/data/out/60/52682537-new-york-city-wallpaper.jpg"));
        items.add(new DashboardItem("The home should be the treasure chest of living.", "Le Corbusier", "https://lh3.googleusercontent.com/-NnaUBvaHFeQ/VYa2yvBGIxI/AAAAAAAAdhY/qSpaK9ubPWY/w2048-h1152/4K-Wallpaper-Pack-Smartphone-Tablet-Android-Apple-Notebook-Windows-21.jpg"));
        items.add(new DashboardItem("We know what we are, but know not what we may be.", "William Shakespeare", "http://s1.picswalls.com/wallpapers/2015/09/20/wallpaper-2015_111528356_269.jpg"));
        items.add(new DashboardItem("Sleep is that golden chain that ties health and our bodies together.", "Thomas Dekker", "http://www.planwallpaper.com/static/images/6768666-1080p-wallpapers.jpg"));

        ViewPager mPager = (ViewPager) findViewById(R.id.view_pager);
        mPager.setOffscreenPageLimit(6);

        ParallaxPageTransformer pageTransformer = new ParallaxPageTransformer()
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.quote, 1.2f, 1.9f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.author, 0.6f, 1.7f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.author_image, 0.3f, 1.5f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.action_buttons_cont, 0.7f, 1.3f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.backdrop, -1.8f, -1.8f));

        mPager.setPageTransformer(true, pageTransformer);

        DashboardPagerAdapter mPagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), items);
        mPager.setAdapter(mPagerAdapter);

        final RubberIndicator mRubberIndicator = (RubberIndicator) findViewById(R.id.rubber);
        mRubberIndicator.setCount(items.size(), 0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                if (position > rubberOldPosition)
                    mRubberIndicator.moveToRight();
                else
                    mRubberIndicator.moveToLeft();

                rubberOldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void getDashboardData() {

        Call<DashboardData> call = QuoteTabApi.quoteTabApi.getDashboardData();

        call.enqueue(new Callback<DashboardData>() {
            @Override
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                //initializePopularAuthors(response.body().getPopularAuthorsList());
                int asd = 9;
            }

            @Override
            public void onFailure(Call<DashboardData> call, Throwable t) {
                int ad = 9;
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
