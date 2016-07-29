package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.NewsRecyclerViewAdapter;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-07-28.
 */
public class NewsFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NewsListComponent> MyDataset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    //    int resId = R.layout.news_fragment;
        View view = inflater.inflate(R.layout.news_fragment, null);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MyDataset = new ArrayList<>();
        mAdapter = new NewsRecyclerViewAdapter(MyDataset, this.getContext());
        mRecyclerView.setAdapter(mAdapter);

        MyDataset.add(new NewsListComponent("http://www.chamber.uz/uploads/news/images/small/__c553c6650b3504722b48642cd0572da0.jpg","00","Hello",""));
        MyDataset.add(new NewsListComponent("http://www.chamber.uz/uploads/news/images/small/__c553c6650b3504722b48642cd0572da0.jpg","01","Hello",""));
        MyDataset.add(new NewsListComponent("http://www.chamber.uz/uploads/news/images/small/__c553c6650b3504722b48642cd0572da0.jpg","02","Hello",""));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}
