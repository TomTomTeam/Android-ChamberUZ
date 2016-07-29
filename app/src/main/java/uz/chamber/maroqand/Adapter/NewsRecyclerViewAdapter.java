package uz.chamber.maroqand.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-07-28.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<NewsListComponent> mDataset;
    Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public NetworkImageView newsImage;
        public TextView newsDate;
        public TextView newsTitle;

        public ViewHolder(View view) {
            super(view);
            newsImage = (NetworkImageView)view.findViewById(R.id.news_image);
            newsDate = (TextView)view.findViewById(R.id.news_date);
            newsTitle = (TextView)view.findViewById(R.id.news_title);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsRecyclerViewAdapter(ArrayList<NewsListComponent> myDataset, Context mContext) {
        mDataset = myDataset;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.newsTitle.setText(mDataset.get(position).getTitle());
        holder.newsDate.setText(mDataset.get(position).getDate());
        holder.newsImage.setImageUrl(mDataset.get(position).getSrcUrl(), AppController.getInstance().getImageLoader());
     //   holder.mImageView.setImageResource(mDataset.get(position).img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
