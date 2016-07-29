package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.NewsPagerAdapter;
import uz.chamber.maroqand.CallBack.CallBackNetwork;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Model.Selector;
import uz.chamber.maroqand.Model.TabList;
import uz.chamber.maroqand.Parser.NewsTabParser;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-07-28.
 */
public class NewsTabActivity extends AppCompatActivity{
    ViewPager viewPager;
    NewsPagerAdapter adapter;
    NewsTabParser newsTabParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        viewPager = (ViewPager) findViewById(R.id.viewpager);


        newsTabParser = new NewsTabParser("http://www.chamber.uz/en/news#cci_news",callBackNetwork);

/*
        adapter.addFragment(new NewsFragment(), "All");
        adapter.addFragment(new NewsFragment(), "CCI News");
        adapter.addFragment(new NewsFragment(), "News from the world of business");
        adapter.addFragment(new NewsFragment(), "Announcements");
        adapter.addFragment(new NewsFragment(), "Uzbekistan news");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
*/
    }
    CallBackNetwork callBackNetwork = new CallBackNetwork() {
        @Override
        public void setTitle(String title) {
        }

        @Override
        public void setBreadcrumb(ArrayList<String> breadcrumb) {
        }

        @Override
        public void setContent(ArrayList<Selector> content) {
        }

        @Override
        public void setNewsContent(ArrayList<NewsListComponent> newsContent) {
        }

        @Override
        public void setNewsTab(ArrayList<TabList> newsTab) {
            adapter = new NewsPagerAdapter(getSupportFragmentManager(), newsTab);
            for(int i=0;i<newsTab.size();i++){

                NewsFragment fragment=new NewsFragment();
                fragment.setNewsList(newsTab.get(i).getNewsListComponents());
                Log.i("aa", newsTab.get(i).getNewsListComponents().size() + "" );
                adapter.addFragment(fragment);

            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewPager.setAdapter(adapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(viewPager);
                }
            });

        }
    };

}
