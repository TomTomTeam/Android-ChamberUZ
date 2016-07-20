package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import uz.chamber.maroqand.AppController;



/**
 * Created by lk on 16. 7. 19..
 */
public class MainViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<String> list;
    MainViewPagerAdapter context;

    public MainViewPagerAdapter(Context mContext, ArrayList<String> list) {

        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = this;
        this.list = list;
    }

    public void addImageUrl(String url){
        list.add(url);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        NetworkImageView view = new NetworkImageView(mContext);
        view.setImageUrl(list.get(position), AppController.getInstance().getImageLoader());
        collection.addView(view, 0);
        return view;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
