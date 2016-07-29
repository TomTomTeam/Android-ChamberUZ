package uz.chamber.maroqand.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uz.chamber.maroqand.Adapter.NewsRecyclerViewAdapter;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.R;

public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<NewsListComponent> MyDataset;

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

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setNewsList(ArrayList<NewsListComponent> newsListComponent) {
        MyDataset.addAll(newsListComponent);
        mAdapter.notifyDataSetChanged();
    }
}
