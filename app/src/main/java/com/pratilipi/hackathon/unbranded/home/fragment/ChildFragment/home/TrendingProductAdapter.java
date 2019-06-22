package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.network.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 *
 */
public class TrendingProductAdapter extends RecyclerView.Adapter<TrendingProductAdapter.ViewHolder> {

    private ArrayList<Product> mDataset = new ArrayList<>();

    private TrendingRecyclerViewAdapter.OnItemClickListener mItemClickListener;

    public TrendingProductAdapter(ArrayList<Product> dataset, TrendingRecyclerViewAdapter.OnItemClickListener mItemClickListener) {
        mDataset.clear();
        mDataset.addAll(dataset);
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public TrendingProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Product product = mDataset.get(position);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
//        holder.productRating.setText("(" + product.getRa() + ")");

        RequestOptions options = new RequestOptions()
                .priority(Priority.HIGH)
//                .fitCenter()
                .error(R.drawable.loader)
                .placeholder(R.drawable.loader)
                .centerCrop()
                .transform(new RoundedCornersTransformation(8, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        if (product.getImageUrl() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(product.getImageUrl())
                    .apply(options)
                    .into(holder.productImage);
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.product_sale_text)
        TextView productSaleText;
        @BindView(R.id.product_price)
        TextView productPrice;
        @BindView(R.id.product_rating)
        TextView productRating;
        @BindView(R.id.product_sold_count)
        TextView productSoldCount;

        @BindView(R.id.product_image)
        AppCompatImageView productImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (mItemClickListener != null)
                            mItemClickListener.onItemClick(itemView, getAdapterPosition(), mDataset.get(getAdapterPosition()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


/*

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextTitle;
        public TextView mTextRate;
        public TextView mTextRateCount;
        public TextView mTextView;
        public ImageView mThumbnail;

        public ItemViewHolder(View v) {
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

*/

}
