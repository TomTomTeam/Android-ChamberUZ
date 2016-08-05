package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.ExpandableListDrawerAdapter;
import uz.chamber.maroqand.Adapter.NewsPagerAdapter;
import uz.chamber.maroqand.Util.AppConfig;
import uz.chamber.maroqand.CallBack.CallBackNews;
import uz.chamber.maroqand.Model.TabList;
import uz.chamber.maroqand.Parser.NewsTabParser;
import uz.chamber.maroqand.R;
import uz.chamber.maroqand.Util.ExpandableListViewOnClickListener;

public class NewsTabActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private NewsPagerAdapter adapter;
    private NewsTabParser newsTabParser;
    public ExpandableListView mExpandableListView;
    private DrawerLayout mDrawerLayout;
    private String selectedItem;
    private ImageView nav_header;
    private View listHeaderView;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListDrawerAdapter mExpandableListAdapter;
    private Toolbar toolbar;
    private ImageView headerLogo;
    private ImageView headerLang;
    private int[][] headerResources = {{R.drawable.headereng, R.drawable.headerru, R.drawable.headeruz, R.drawable.headeruzb},
            {R.drawable.headerenglan, R.drawable.headerrulan, R.drawable.headeruzlan, R.drawable.headeruzblan}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        initView();
        setNavigationDrawer();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        newsTabParser = new NewsTabParser(AppConfig.getRealPath("/en/news"), callBackNetwork);

        addDrawerItems();
        setupDrawer();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav_header = (ImageView) findViewById(R.id.nav_header);
        setSupportActionBar(toolbar);
        ImageView headerLogo = (ImageView) findViewById(R.id.imageViewplaces);
        ImageView headerLang = (ImageView) findViewById(R.id.imageViewplaceslang);
        headerLogo.setImageDrawable(getResources().getDrawable(headerResources[0][AppConfig.languageNum]));
        headerLang.setImageDrawable(getResources().getDrawable(headerResources[1][AppConfig.languageNum]));
    }

    private void setNavigationDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);

        LayoutInflater inflater = getLayoutInflater();
        listHeaderView = inflater.inflate(R.layout.nav_header_main, null, false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mExpandableListView.addHeaderView(listHeaderView);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    private void addDrawerItems() {
        mExpandableListAdapter = new ExpandableListDrawerAdapter(this);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListViewOnClickListener(this, mExpandableListAdapter.mExpandableListTitle, mExpandableListAdapter.mExpandableListDetail));
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                toolbar.setTitle(mExpandableListAdapter.mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (selectedItem != null) {
                    toolbar.setTitle(selectedItem);
                } else {
                    toolbar.setTitle("MyShopingCart");
                }
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
