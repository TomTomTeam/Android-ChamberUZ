package uz.chamber.maroqand.Activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.MainViewPagerAdapter;
import uz.chamber.maroqand.Adapter.MainViewPagerNewsAdapter;
import uz.chamber.maroqand.CallBack;
import uz.chamber.maroqand.Model.MainViewPagerData;
import uz.chamber.maroqand.Parser.MainPageParser;
import uz.chamber.maroqand.R;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    ViewPager viewPagerNews;
    Handler handler;
    MainViewPagerAdapter adapterMainPager;
    MainViewPagerNewsAdapter adapterNewsPager;
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.vp_mainViewPager);
        viewPagerNews = (ViewPager) findViewById(R.id.vp_mainViewPager_news);

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
                        //viewPagerNews.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);
                        //viewPagerNews.setClipToPadding(false);
                        //viewPagerNews.setPadding(40,0,40,0);
                        //viewPagerNews.setPageMargin(-50);
                        //viewPagerNews.setOffscreenPageLimit(2);
                        viewPagerNews.getAdapter().notifyDataSetChanged();
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
                }catch (NullPointerException e){
                    p=0;
                }
            }
        };
        new AutoTransferThread().start();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
}
