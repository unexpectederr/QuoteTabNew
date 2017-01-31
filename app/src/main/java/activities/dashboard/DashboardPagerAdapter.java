package activities.dashboard;


import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import java.util.ArrayList;
import models.dashboard.DashboardItem;


public class DashboardPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    ArrayList<DashboardItem> mItems;

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<DashboardItem> items) {
        super(fm);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Fragment getItem(int position) {
        return DashboardFragment.getNewInstance(position, mItems.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).getAuthor().toString();
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
