package uz.chamber.maroqand.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.List;
import java.util.Map;

import uz.chamber.maroqand.Activity.SubPageView;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-08-04.
 */
public class ExpandableListViewOnClickListener implements ExpandableListView.OnChildClickListener{

    Activity mContext;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListDetail;

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

        if (selectedItem.equals("HOME")) {
            Intent intent = new Intent(mContext, SubPageView.class);
            intent.putExtra("url", "http://chamber.uz/en/page/3435");
            mContext.startActivity(intent);
        } else if (selectedItem.equalsIgnoreCase("Paint")) {
            Intent intent = new Intent(mContext, SubPageView.class);
            intent.putExtra("url", "http://chamber.uz/ru");
            mContext.startActivity(intent);
        } else if (selectedItem.equalsIgnoreCase("Watch")) {
            Intent intent = new Intent(mContext, SubPageView.class);
            intent.putExtra("url", "http://chamber.uz/uz");
            mContext.startActivity(intent);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
