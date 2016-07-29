package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import uz.chamber.maroqand.Adapter.NewsPagerAdapter;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-07-28.
 */
public class NewsTabActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        NewsPagerAdapter adapter = new NewsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFragment(), "All");
        adapter.addFragment(new NewsFragment(), "CCI News");
        adapter.addFragment(new NewsFragment(), "News from the world of business");
        adapter.addFragment(new NewsFragment(), "Announcements");
        adapter.addFragment(new NewsFragment(), "Uzbekistan news");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
}
