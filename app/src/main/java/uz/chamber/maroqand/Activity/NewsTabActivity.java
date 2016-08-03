package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.NewsPagerAdapter;
import uz.chamber.maroqand.CallBack.CallBackNetwork;
import uz.chamber.maroqand.CallBack.CallBackNews;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Model.Selector;
import uz.chamber.maroqand.Model.TabList;
import uz.chamber.maroqand.Parser.NewsTabParser;
import uz.chamber.maroqand.R;

public class NewsTabActivity extends AppCompatActivity {
    ViewPager viewPager;
    NewsPagerAdapter adapter;
    NewsTabParser newsTabParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        newsTabParser = new NewsTabParser("http://www.chamber.uz/en/news#cci_news", callBackNetwork);

    }

    CallBackNews callBackNetwork = new CallBackNews() {
        @Override
        public void setNewsTab(ArrayList<TabList> newsTab) {
            adapter = new NewsPagerAdapter(getSupportFragmentManager(), newsTab);
            for (int i = 0; i < newsTab.size(); i++) {
                NewsFragment fragment = new NewsFragment();
                fragment.setNewsList(newsTab.get(i).getNewsListComponents());
                Log.i("aa", newsTab.get(i).getNewsListComponents().size() + "");
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
