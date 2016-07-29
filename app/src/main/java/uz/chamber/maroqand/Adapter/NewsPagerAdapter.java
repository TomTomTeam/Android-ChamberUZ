package uz.chamber.maroqand.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import uz.chamber.maroqand.Activity.NewsFragment;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Model.TabList;

/**
 * Created by WTF on 2016-07-28.
 */
public class NewsPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<TabList> tabList;

    public NewsPagerAdapter(FragmentManager fm, ArrayList<TabList> tabList)  {
        super(fm);
        this.tabList = tabList;
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);

       // ((NewsFragment)fragment).setNewsList(newsListComponents);

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getTitle();
    }
}
