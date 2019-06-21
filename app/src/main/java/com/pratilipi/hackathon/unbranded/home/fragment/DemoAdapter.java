package com.pratilipi.hackathon.unbranded.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home.RecyclerViewAdapter;
import com.pratilipi.hackathon.unbranded.model.homepage.Content;

import java.util.ArrayList;

/**
 *
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    private ArrayList<Content> mDataset = new ArrayList<>();

    private RecyclerViewAdapter.OnItemClickListener mItemClickListener;

    public DemoAdapter(ArrayList<Content> dataset, RecyclerViewAdapter.OnItemClickListener mItemClickListener) {
        mDataset.clear();
        mDataset.addAll(dataset);
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_demo, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Content content = mDataset.get(position);

        holder.mTextTitle.setText(content.getContentHeading());
        holder.mTextRate.setText(content.getContentRate());
        holder.mTextRateCount.setText("(" + content.getContentRateCount() + ")");


        if (Integer.parseInt(content.getContentView()) < 1000) {
            holder.mTextView.setText(content.getContentView());
        } else {
            float d = (float) Integer.parseInt(content.getContentView()) / 1000;
            String s = String.format("%.1f", d);
            holder.mTextView.setText(s + "K");

        }

        if (content.getContentImg() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(content.getContentImg())
                    .into(holder.mThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;
        public TextView mTextRate;
        public TextView mTextRateCount;
        public TextView mTextView;
        public ImageView mThumbnail;

        public ViewHolder(View v) {
            super(v);
            mTextRate = v.findViewById(R.id.title_text_rate);
            mTextTitle = v.findViewById(R.id.layout_item_demo_title);
            mTextRateCount = v.findViewById(R.id.title_text_rate_count);
            mTextView = v.findViewById(R.id.title_text_view);
            mThumbnail = v.findViewById(R.id.iv_thumnail);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (mItemClickListener != null)
                            mItemClickListener.onItemClick(v, getAdapterPosition(), mDataset.get(getAdapterPosition()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
