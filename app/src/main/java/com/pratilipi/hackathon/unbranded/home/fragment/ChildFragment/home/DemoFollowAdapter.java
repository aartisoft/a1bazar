package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.model.homepage.HomePageFollow;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

/**
 *
 */
public class DemoFollowAdapter extends RecyclerView.Adapter<DemoFollowAdapter.ViewHolder> {

    private ArrayList<HomePageFollow> mDataset = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;
        public TextView mTextRate;
        public TextView mTextRateCount;
        public TextView mTextView;
        public ImageView mThumbnail;

        public ViewHolder(View v) {
            super(v);
            mTextTitle = v.findViewById(R.id.layout_item_demo_title);
            mThumbnail = v.findViewById(R.id.iv_thumnail);
        }
    }

    public DemoFollowAdapter(ArrayList<HomePageFollow> dataset) {
        mDataset.clear();
        mDataset.addAll(dataset);
    }

    @Override
    public DemoFollowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_demo_follow, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HomePageFollow content = mDataset.get(position);

        holder.mTextTitle.setText(content.getName());


        if (content.getProfile_pic() != null) {

            Transformation transformation = new RoundedTransformationBuilder()
                    //              .borderColor(rainbow[i])
                    .borderColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.colorAccent))
                    .borderWidthDp(2)
                    .cornerRadiusDp(70)
                    .oval(true)
                    .build();

            Picasso.with(holder.itemView.getContext())
                    .load(content.getProfile_pic())
                    .transform(transformation)
                    .error(R.drawable.ic_def_user_circle_128)
                    .placeholder(R.drawable.ic_def_user_circle_128)
                    .fit()
                    .into(holder.mThumbnail);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
