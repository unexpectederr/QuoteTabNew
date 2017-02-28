package adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;

import activities.dashboard.DashboardFragment;
import models.authors.AuthorDetails;
import models.quotes.Quote;

public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private ArrayList<Quote> mItems;
    private ArrayList<Quote> favoriteQuotes;
    private ArrayList<AuthorDetails> favoriteAuthors;

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<Quote> items, ArrayList<Quote> favoriteQuotes,
                                 ArrayList<AuthorDetails> favoriteAuthors) {
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
        return DashboardFragment.getNewInstance(position, mItems.get(position), favoriteQuotes,
                favoriteAuthors, mItems.get(position).getAuthor().getAuthor());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).getQuoteDetails().getAuthorName();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
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

    public SparseArray<Fragment> getRegisteredFragments() {
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
