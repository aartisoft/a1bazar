package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pratilipi.hackathon.unbranded.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class PagerAdapterHomeHeader extends PagerAdapter {

    private final Context context;

    private List<HomeHdrPagerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private String Image_;

    private Intent intent_quick_menu = null;

    public PagerAdapterHomeHeader(Context context, List<HomeHdrPagerItem> data) {

        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView;
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        itemView = inflater.inflate(R.layout.pager_item_home_hdr, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


            }
        });

        HomeHdrPagerItem current = data.get(position);
        Image_ = current.getThumbnailUrl();

/*
        Glide.with(activity)
                .load(Image_)
                .fitCenter()
//                .thumbnail(0.4f)
                .placeholder(R.drawable.loader)
                .error(R.drawable.loader)
                .into(imageView);
*/


        Picasso.with(context)
                .load(Image_)
                .placeholder(R.drawable.iv_viewpagerdefault)   // optional
                .error(R.drawable.iv_viewpagerdefault)      // optional
                .fit()
                // .resize(150, 120)
                .into(imageView);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup viewPager, int position, Object object) {
        //viewPager.removeView((View) object);
        ((ViewPager) viewPager).removeView((View) object);
    }

//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

/*
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((LinearLayout) object);
        container.removeView((RelativeLayout) object);
    }
*/

}