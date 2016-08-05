package uz.chamber.maroqand.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import uz.chamber.maroqand.Activity.LanguageSelectActivity;
import uz.chamber.maroqand.Activity.Main;
import uz.chamber.maroqand.Activity.MainActivity;
import uz.chamber.maroqand.Activity.NewsTabActivity;
import uz.chamber.maroqand.Activity.SubPageView;
import uz.chamber.maroqand.AppConfig;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-08-04.
 */
public class ExpandableListViewOnClickListener implements ExpandableListView.OnChildClickListener{

    Activity mContext;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListDetail;
    int[] arrayList = {R.array.nav_home, R.array.nav_news, R.array.nav_about, R.array.nav_services, R.array.nav_investors, R.array.nav_issues, R.array.nav_purchases, R.array.nav_membership, R.array.nav_login, R.array.nav_sign, R.array.nav_setting};
    int[] arrayUrlList = {R.array.nav_home_url, R.array.nav_news_url, R.array.nav_about_url, R.array.nav_services_url, R.array.nav_investors_url, R.array.nav_issues_url, R.array.nav_purchases_url, R.array.nav_membership_url, R.array.nav_login, R.array.nav_sign, R.array.nav_setting};


    public ExpandableListViewOnClickListener(Activity activity, List<String> mExpandableListTitle, Map<String, List<String>>mExpandableListDetail) {
        this.mContext = activity;
        this.mExpandableListDetail = mExpandableListDetail;
        this.mExpandableListTitle = mExpandableListTitle;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        String selectedItem = ((List) (mExpandableListDetail.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
        Toolbar toolbar = (Toolbar) mContext.findViewById(R.id.toolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) mContext.findViewById(R.id.drawer_layout);
        toolbar.setTitle(selectedItem);

        Log.i("ExpandedListAdapter", "GroupPosition : " + groupPosition + " / childPosition : " + childPosition);

        if(groupPosition == 0){
            Intent intent = new Intent(mContext, Main.class);
            mContext.startActivity(intent);
            mContext.finish();
        } else if(groupPosition == 1) {
            Intent intent = new Intent(mContext, NewsTabActivity.class);
            mContext.startActivity(intent);
        }else if(groupPosition == 10){
            Intent intent = new Intent(mContext, LanguageSelectActivity.class);
            mContext.startActivity(intent);
            mContext.finish();
        }
        else {
            String url = Arrays.asList(v.getResources().getStringArray(arrayUrlList[groupPosition])).get(childPosition);
            Intent intent = new Intent(mContext, SubPageView.class);
            intent.putExtra("type", "home");
            intent.putExtra("url", AppConfig.getRealPath(url));
            mContext.startActivity(intent);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
