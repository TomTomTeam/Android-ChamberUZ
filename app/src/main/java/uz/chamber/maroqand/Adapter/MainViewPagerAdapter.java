package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import uz.chamber.maroqand.Activity.SubPageView;
import uz.chamber.maroqand.Util.AppConfig;
import uz.chamber.maroqand.Util.AppController;
import uz.chamber.maroqand.Model.MainViewPagerData;
import uz.chamber.maroqand.R;


public class MainViewPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<MainViewPagerData> list;
    MainViewPagerAdapter context;

    public MainViewPagerAdapter(Context mContext, ArrayList<MainViewPagerData> list) {

        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = this;
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.main_viewpager, collection, false);
        NetworkImageView view = (NetworkImageView) itemView.findViewById(R.id.nv_main_viewpager);
        TextView title = (TextView) itemView.findViewById(R.id.tv_mainViewPager_title);
        view.setImageUrl(list.get(position).getImgUrl(), AppController.getInstance().getImageLoader());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SubPageView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", AppConfig.getRealPath(list.get(position).getLinkUrl()));
                Log.i("aa",AppConfig.getRealPath(list.get(position).getLinkUrl()) );
                intent.putExtra("type", "news");
                intent.putExtra("date", "");
                mContext.startActivity(intent);
            }
        });
        title.setText(list.get(position).getTitle());
        collection.addView(itemView);
        return itemView;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);

    }
}
