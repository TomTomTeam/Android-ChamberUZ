package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.R;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<NewsListComponent> mDataset;
    Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView newsImage;
        public TextView newsDate;
        public TextView newsTitle;

        public ViewHolder(View view) {
            super(view);
            newsImage = (NetworkImageView) view.findViewById(R.id.news_image);
            newsDate = (TextView) view.findViewById(R.id.news_date);
            newsTitle = (TextView) view.findViewById(R.id.news_title);
        }
    }

    public NewsRecyclerViewAdapter(ArrayList<NewsListComponent> myDataset, Context mContext) {
        mDataset = myDataset;
        this.mContext = mContext;
    }

    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.newsTitle.setText(mDataset.get(position).getTitle());
        holder.newsDate.setText(mDataset.get(position).getDate());
        holder.newsImage.setImageUrl(mDataset.get(position).getSrcUrl(), AppController.getInstance().getImageLoader());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
