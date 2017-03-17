package adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import activities.dashboard.DashboardFragment;
import models.authors.Author;
import models.authors.AuthorDetails;
import models.quotes.Quote;
import models.quotes.QuoteReference;

public class DashboardPagerAdapter extends FragmentStatePagerAdapter implements Serializable {

    private ArrayList<Fragment> registeredFragments = new ArrayList<>();
    private ArrayList<Quote> mItems;
    private ArrayList<Quote> favoriteQuotes;
    private ArrayList<Author> favoriteAuthors;

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<Quote> items,
                                 ArrayList<Quote> favoriteQuotes,
                                 ArrayList<Author> favoriteAuthors) {
        super(fm);
        mItems = items;
        this.favoriteQuotes = favoriteQuotes;
        this.favoriteAuthors = favoriteAuthors;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Fragment getItem(int position) {
        return DashboardFragment.getNewInstance(position, mItems.get(position),
                favoriteQuotes, favoriteAuthors, this);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).getAuthor().getAuthorName();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.add(fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public ArrayList<Fragment> getRegisteredFragments() {
        return registeredFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
