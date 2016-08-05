package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.chamber.maroqand.R;


public class ExpandableListDrawerAdapter extends BaseExpandableListAdapter{
    private Context mContext;
    public List<String> mExpandableListTitle;
    public Map<String, List<String>> mExpandableListDetail = new HashMap<>();
    private LayoutInflater mLayoutInflater;
    int imgArray[] = {R.mipmap.home_noactive, R.mipmap.news_noactive, R.mipmap.about_noactive, R.mipmap.services_noactive, R.mipmap.prospective_noactive, R.mipmap.noactive_assistance, R.mipmap.noactive_tenders, R.mipmap.noactive_membership, R.mipmap.login, R.mipmap.sign, R.mipmap.sign};

    public ExpandableListDrawerAdapter(Context context) {
        mContext = context;
        mExpandableListTitle = Arrays.asList(context.getResources().getStringArray(R.array.nav_drawer_items));

        int[] stringArray = {R.array.nav_home, R.array.nav_news, R.array.nav_about, R.array.nav_services,  R.array.nav_investors, R.array.nav_issues, R.array.nav_purchases, R.array.nav_membership, R.array.nav_login, R.array.nav_sign, R.array.nav_setting};
        for(int i=0; i<stringArray.length; i++) {
            mExpandableListDetail.put(mExpandableListTitle.get(i), Arrays.asList(context.getResources().getStringArray(stringArray[i])));
        }

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
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.nav_drawer_row, null);

        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.tv_childitem);

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

        ImageView expandedListImageView = (ImageView) convertView.findViewById(R.id.iv_drawer_listview_icon);
        expandedListImageView.setImageDrawable(mContext.getResources().getDrawable(imgArray[listPosition]));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        Log.d("ExpandableListAdapter", listPosition + " / expandedListPosition" + expandedListPosition);
        return true;
    }
}
