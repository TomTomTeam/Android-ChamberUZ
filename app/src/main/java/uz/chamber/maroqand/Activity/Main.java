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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uz.chamber.maroqand.Adapter.ExpandableListDrawerAdapter;
import uz.chamber.maroqand.Adapter.MainViewPagerAdapter;
import uz.chamber.maroqand.Adapter.MainViewPagerNewsAdapter;
import uz.chamber.maroqand.AppConfig;
import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.CallBack.CallBack;
import uz.chamber.maroqand.Model.MainViewListData;
import uz.chamber.maroqand.Model.MainViewPagerData;
import uz.chamber.maroqand.NavigationSelectorListener;
import uz.chamber.maroqand.Parser.MainPageParser;
import uz.chamber.maroqand.R;

public class Main extends AppCompatActivity{

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
    Activity activity = this;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public ExpandableListView mExpandableListView;
    private ExpandableListDrawerAdapter mExpandableListAdapter;
    private Map<String, List<String>> mExpandableListData;
    private String selectedItem;
    private LinearLayout linearLayout;
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
        //

        LayoutInflater inflater = getLayoutInflater();
        listHeaderView = inflater.inflate(R.layout.nav_header_main, null, false);

        setToolbar();

        prepareListData();
        addDrawerItems();
        setupDrawer();

    //    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
     //   ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
    //            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    //    drawer.setDrawerListener(toggle);
     //   toggle.syncState();

   //     NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
   //     navigationView.invalidate();
    //    navigationView.setNavigationItemSelectedListener(new NavigationSelectorListener(this));

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
                s+=500;
                if(s > svPartner.getBottom() + 2000)
                    s = 0;
            }
        };
        new AutoTransferThread().start();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //todo It has some error in Chamber.uz, do we have to make a search Engine.
                Log.i("text", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

        if (id == R.id.action_search) {
            return true;
        }

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
    private void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader = Arrays.asList(getResources().getStringArray(R.array.nav_drawer_items));

        List<String> home = Arrays.asList(getResources().getStringArray(R.array.nav_home));

        List<String> newslist = Arrays.asList(getResources().getStringArray(R.array.nav_news));

        List<String> aboutlist =Arrays.asList(getResources().getStringArray(R.array.nav_about));

        List<String> serviceslist = Arrays.asList(getResources().getStringArray(R.array.nav_services));

        List<String> investorslist = Arrays.asList(getResources().getStringArray(R.array.nav_investors));

        List<String> issueslist = Arrays.asList(getResources().getStringArray(R.array.nav_issues));

        List<String> purchaseslist = Arrays.asList(getResources().getStringArray(R.array.nav_purchases));

        List<String> membershiplist = Arrays.asList(getResources().getStringArray(R.array.nav_membership));

        List<String> login = Arrays.asList(getResources().getStringArray(R.array.nav_login));

        List<String> sign = Arrays.asList(getResources().getStringArray(R.array.nav_sign));



        listDataChild.put(listDataHeader.get(0), home);
        listDataChild.put(listDataHeader.get(1), newslist);
        listDataChild.put(listDataHeader.get(2), aboutlist);
        listDataChild.put(listDataHeader.get(3), serviceslist);
        listDataChild.put(listDataHeader.get(4), investorslist);
        listDataChild.put(listDataHeader.get(5), issueslist);
        listDataChild.put(listDataHeader.get(6), purchaseslist);
        listDataChild.put(listDataHeader.get(7), membershiplist);
        listDataChild.put(listDataHeader.get(8), login);
        listDataChild.put(listDataHeader.get(9), sign);


        mExpandableListView.addHeaderView(listHeaderView);
        mExpandableListData = listDataChild;
        // mExpandableListTitle = new ArrayList(listDataChild.keySet());
      //  Log.v("Main", " Key set " + listDataChild.keySet());
    }



    private void addDrawerItems() {
        mExpandableListAdapter = new ExpandableListDrawerAdapter(this, listDataHeader, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e("Main"," you click parent ");
                return false;
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.e("Main"," you click child ");
                selectedItem = ((List) (mExpandableListData.get(listDataHeader.get(groupPosition)))).get(childPosition).toString();
                toolbar.setTitle(selectedItem);

                Log.e("Main"," you click child ");
                if (selectedItem.equalsIgnoreCase("Shirt")) {
                    Intent intent = new Intent(getApplicationContext(), SubPageView.class);
                    intent.putExtra("url", "http://chamber.uz/en/page/3435");
                    startActivity(intent);
                }
                else if (selectedItem.equalsIgnoreCase("Paint")) {
                    Intent intent = new Intent(getApplicationContext(), SubPageView.class);
                    intent.putExtra("url", "http://chamber.uz/ru");
                    startActivity(intent);
                }
                else if (selectedItem.equalsIgnoreCase("Watch")) {
                    Intent intent = new Intent(getApplicationContext(), SubPageView.class);
                    intent.putExtra("url", "http://chamber.uz/uz");
                    startActivity(intent);
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                toolbar.setTitle("test");
                toolbar.setTitle(listDataHeader.get(groupPosition).toString());
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



