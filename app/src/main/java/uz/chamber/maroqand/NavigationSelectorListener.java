package uz.chamber.maroqand;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by WTF on 2016-08-03.
 */
public class NavigationSelectorListener implements NavigationView.OnNavigationItemSelectedListener {

    public Activity mContext;
    public NavigationSelectorListener(Activity context) {
        mContext=context;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_news:
                break;
            case R.id.nav_about:
                break;

            case R.id.nav_services:
                break;

            case R.id.nav_investors:
                break;

            case R.id.nav_issues:
                break;

            case R.id.nav_purchases:
                break;

            case R.id.nav_membership:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) mContext.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
