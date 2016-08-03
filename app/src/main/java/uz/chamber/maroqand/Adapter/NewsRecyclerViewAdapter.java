package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import uz.chamber.maroqand.Activity.SubPageView;
import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.R;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<NewsListComponent> mDataset;
    Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout rootView;
        public NetworkImageView newsImage;
        public TextView newsDate;
        public TextView newsTitle;

        public ViewHolder(View view) {
            super(view);
            rootView = (LinearLayout) view.findViewById(R.id.news_items);
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.newsTitle.setText(mDataset.get(position).getTitle());
        holder.newsDate.setText(mDataset.get(position).getDate());
        holder.newsImage.setImageUrl(mDataset.get(position).getSrcUrl(), AppController.getInstance().getImageLoader());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SubPageView.class);
                intent.putExtra("url", mDataset.get(position).getHtml());
                intent.putExtra("type", "news");
                intent.putExtra("date", mDataset.get(position).getDate());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
