package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-08-03.
 */
public class ExpandableListDrawerAdapter extends BaseExpandableListAdapter{
    private Context mContext;
    public List<String> mExpandableListTitle;
    public Map<String, List<String>> mExpandableListDetail;
    private LayoutInflater mLayoutInflater;

    public ExpandableListDrawerAdapter(Context context) {
        mContext = context;
        mExpandableListTitle = Arrays.asList(context.getResources().getStringArray(R.array.nav_drawer_items));


        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

        List<String> home = Arrays.asList(context.getResources().getStringArray(R.array.nav_home));
        List<String> newslist = Arrays.asList(context.getResources().getStringArray(R.array.nav_news));
        List<String> aboutlist = Arrays.asList(context.getResources().getStringArray(R.array.nav_about));
        List<String> serviceslist = Arrays.asList(context.getResources().getStringArray(R.array.nav_services));
        List<String> investorslist = Arrays.asList(context.getResources().getStringArray(R.array.nav_investors));
        List<String> issueslist = Arrays.asList(context.getResources().getStringArray(R.array.nav_issues));
        List<String> purchaseslist = Arrays.asList(context.getResources().getStringArray(R.array.nav_purchases));
        List<String> membershiplist = Arrays.asList(context.getResources().getStringArray(R.array.nav_membership));
        List<String> login = Arrays.asList(context.getResources().getStringArray(R.array.nav_login));
        List<String> sign = Arrays.asList(context.getResources().getStringArray(R.array.nav_sign));

        listDataChild.put(mExpandableListTitle.get(0), home);
        listDataChild.put(mExpandableListTitle.get(1), newslist);
        listDataChild.put(mExpandableListTitle.get(2), aboutlist);
        listDataChild.put(mExpandableListTitle.get(3), serviceslist);
        listDataChild.put(mExpandableListTitle.get(4), investorslist);
        listDataChild.put(mExpandableListTitle.get(5), issueslist);
        listDataChild.put(mExpandableListTitle.get(6), purchaseslist);
        listDataChild.put(mExpandableListTitle.get(7), membershiplist);
        listDataChild.put(mExpandableListTitle.get(8), login);
        listDataChild.put(mExpandableListTitle.get(9), sign);

        mExpandableListDetail = listDataChild;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return mExpandableListDetail.get(mExpandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.nav_drawer_row, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.tv_childitem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return mExpandableListDetail.get(mExpandableListTitle.get(listPosition)).size();
    }


    @Override
    public Object getGroup(int listPosition) {
        return mExpandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return mExpandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.nav_drawer_item_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.tv_groupitem);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

       // Log.v("Main"," Key title  "+listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
