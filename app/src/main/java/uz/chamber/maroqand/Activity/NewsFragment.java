package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.NewsRecyclerViewAdapter;
import uz.chamber.maroqand.Util.EndlessRecyclerOnScrollListener;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.R;

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<NewsListComponent> MyDataset;
    private EndlessRecyclerOnScrollListener mScrollListener;

    public NewsFragment() {
        MyDataset = new ArrayList<>();
        mAdapter = new NewsRecyclerViewAdapter(MyDataset, this.getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        mScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.i("onLoadMore", current_page + "");
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();
        mScrollListener.reset(0, true);
    }

    public void setNewsList(ArrayList<NewsListComponent> newsListComponent) {
        MyDataset.addAll(newsListComponent);
        mAdapter.notifyDataSetChanged();
    }
}
