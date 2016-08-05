package uz.chamber.maroqand.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.chamber.maroqand.Adapter.ExpandableListDrawerAdapter;
import uz.chamber.maroqand.Adapter.MainViewPagerAdapter;
import uz.chamber.maroqand.Adapter.MainViewPagerNewsAdapter;
import uz.chamber.maroqand.AppConfig;
import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.CallBack.CallBack;
import uz.chamber.maroqand.Model.MainViewListData;
import uz.chamber.maroqand.Model.MainViewPagerData;
import uz.chamber.maroqand.Parser.MainPageParser;
import uz.chamber.maroqand.R;
import uz.chamber.maroqand.Util.ExpandableListViewOnClickListener;

public class Main extends AppCompatActivity {

    ViewPager viewPager;
    ViewPager viewPagerNews;
    Handler handler;
    MainViewPagerAdapter adapterMainPager;
    MainViewPagerNewsAdapter adapterNewsPager;
    NetworkImageView nvBannerBottom;
    HorizontalScrollView svPartner;
    LinearLayout llRootView;
    int p;
    int s;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public ExpandableListView mExpandableListView;
    private ExpandableListDrawerAdapter mExpandableListAdapter;
    private Map<String, List<String>> mExpandableListData;
    private String selectedItem;
    private Toolbar toolbar;

    private HashMap<String, List<String>> listDataChild;
    private List<String> listDataHeader;

    View listHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);

        int[][] headerResources = {{R.drawable.headereng, R.drawable.headerru, R.drawable.headeruz, R.drawable.headeruzb}, {R.drawable.headerenglan, R.drawable.headerrulan, R.drawable.headeruzlan, R.drawable.headeruzblan}};

        ImageView headerLogo = (ImageView) findViewById(R.id.imageViewplaces);
        ImageView headerLang = (ImageView) findViewById(R.id.imageViewplaceslang);
        headerLogo.setImageDrawable(getResources().getDrawable(headerResources[0][AppConfig.languageNum]));
        headerLang.setImageDrawable(getResources().getDrawable(headerResources[1][AppConfig.languageNum]));

        LayoutInflater inflater = getLayoutInflater();
        listHeaderView = inflater.inflate(R.layout.nav_header_main, null, false);
        mExpandableListView.addHeaderView(listHeaderView);
        setToolbar();

        addDrawerItems();
        setupDrawer();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        viewPager = (ViewPager) findViewById(R.id.vp_mainViewPager);
        viewPagerNews = (ViewPager) findViewById(R.id.vp_mainViewPager_news);
        nvBannerBottom = (NetworkImageView) findViewById(R.id.nv_main_banner_bottom);
        svPartner = (HorizontalScrollView) findViewById(R.id.sv_main_partners);
        llRootView = (LinearLayout) findViewById(R.id.ll_main_rootView);

        CallBack callBack = new CallBack() {
            @Override
            public void doneViewPager(final ArrayList<MainViewPagerData> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterMainPager = new MainViewPagerAdapter(getApplicationContext(), list);
                        viewPager.setAdapter(adapterMainPager);
                        viewPager.getAdapter().notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void doneNews(final ArrayList<MainViewPagerData> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapterNewsPager = new MainViewPagerNewsAdapter(getApplicationContext(), list);
                        viewPagerNews.setAdapter(adapterNewsPager);
                        viewPagerNews.setOffscreenPageLimit(3);
                        viewPagerNews.getAdapter().notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void doneBannerBottom(final String imgUrl, String linkUrl) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nvBannerBottom.setImageUrl(imgUrl, AppController.getInstance().getImageLoader());

                    }
                });
            }

            @Override
            public void doneSchedule(final ArrayList<MainViewListData> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LinearLayout layout = (LinearLayout) findViewById(R.id.ll_main_schedule);

                        for (int i = 0; i < list.size(); i++) {
                            LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View v = inflater.inflate(R.layout.item_main_schedule, null, false);

                            TextView tvDay = (TextView) v.findViewById(R.id.tv_item_schedule_day);
                            TextView tvMonth = (TextView) v.findViewById(R.id.tv_item_schedule_month);
                            TextView tvContent = (TextView) v.findViewById(R.id.tv_item_schedule_content);
                            TextView tvTime = (TextView) v.findViewById(R.id.tv_item_schedule_time);
                            TextView tvAddress = (TextView) v.findViewById(R.id.tv_item_schedule_address);

                            tvDay.setText(list.get(i).getDay());
                            tvMonth.setText(list.get(i).getMonth());
                            tvContent.setText(list.get(i).getContent());
                            tvTime.setText(list.get(i).getTime());
                            tvAddress.setText(list.get(i).getAddress());


                            layout.addView(v);
                        }
                    }
                });

            }

            @Override
            public void donePartner(final ArrayList<MainViewPagerData> dataListPartners) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout rootView = (LinearLayout) findViewById(R.id.ll_main_partners);
                        for (int i = 0; i < dataListPartners.size(); i++) {
                            NetworkImageView v = new NetworkImageView(getApplicationContext());
                            v.setImageUrl(dataListPartners.get(i).getImgUrl(), AppController.getInstance().getImageLoader());
                            rootView.addView(v);
                        }
                        svPartner.post(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
            }

            @Override
            public void doneFooter() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        llRootView.addView(new FooterView(getApplicationContext()));
                    }
                });
            }
        };

        MainPageParser image = new MainPageParser(callBack);


        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                viewPager.setCurrentItem(p);
                p++;
                try {
                    if (p > viewPager.getAdapter().getCount())
                        p = 0;
                } catch (NullPointerException e) {
                    p = 0;
                }
                svPartner.post(new Runnable() {
                    @Override
                    public void run() {
                        ObjectAnimator.ofInt(svPartner, "scrollX", s).setDuration(1000).start();
                    }
                });
                s += 500;
                if (s > svPartner.getBottom() + 2000)
                    s = 0;
            }
        };
        new AutoTransferThread().start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //todo It has some error in Chamber.uz, do we have to make a search Engine.
//                Log.i("text", query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        searchView.setBackgroundColor(Color.WHITE);
        return true;
    }

    class AutoTransferThread extends Thread {
        public void run() {
            while (true) {
                try {
                    handler.sendEmptyMessage(0);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_search) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    // setting toolbar
    private void setToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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

}
