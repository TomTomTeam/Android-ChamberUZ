package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.R;
import uz.chamber.maroqand.VolleySingleton;

/**
 * Created by lk on 2016. 7. 19..
 */
public class MainPagerFragment extends Fragment {

    View view;
    NetworkImageView imageView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_viewpager, container, false);
        imageView = (NetworkImageView) view.findViewById(R.id.nv_main_viewpager);
        //textView = (TextView) view.findViewById(R.id.textView2);
        //textView.setText("aaaaa");
        return view;
    }

    public void setImage(String url){
        try {
            imageView.setImageUrl(url, AppController.getInstance().getImageLoader());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
