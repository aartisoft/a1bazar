package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.home.fragment.DemoAdapter;
import com.pratilipi.hackathon.unbranded.model.homepage.Content;
import com.pratilipi.hackathon.unbranded.model.homepage.Datum;
import com.pratilipi.hackathon.unbranded.model.homepage.HomePageFollow;
import com.pratilipi.hackathon.unbranded.network.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Handler handler;
    Timer swipeTimer;
    int currentPage = 0;
    private String mHeaderTitle;
    private String mFooterTitle;
    private OnHeaderClickListener mHeaderClickListener;
    private OnFooterClickListener mFooterClickListener;
    private Context mContext;
    private ArrayList<Datum> modelList;
    private OnItemClickListener mItemClickListener;
    private Runnable Update;


    public RecyclerViewAdapter(Context context, ArrayList<Datum> modelList, String headerTitle, String footerTitle,OnItemClickListener mItemClickListener) {
        this.mContext = context;
        this.modelList = modelList;
        this.mHeaderTitle = headerTitle;
        this.mFooterTitle = footerTitle;
        this.mItemClickListener = mItemClickListener;
    }

    public void updateList(ArrayList<Datum> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
            return new FooterViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_list_home, parent, false);
            return new ViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

//            headerHolder.txtTitleHeader.setText(mHeaderTitle);
            setHdrPager(headerHolder);


        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;

//            footerHolder.txtFooter.setText(mFooterTitle);

//            genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_1));


            ArrayList<HomePageFollow> homePageFollows = new ArrayList<>();
            HomePageFollow
                    itemsData = new HomePageFollow();
            itemsData.setName("Ajay");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);

            itemsData = new HomePageFollow();
            itemsData.setName("Naresh");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);

            itemsData = new HomePageFollow();
            itemsData.setName("Geeta");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);

            itemsData = new HomePageFollow();
            itemsData.setName("Manohar");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);

            itemsData = new HomePageFollow();
            itemsData.setName("Nishant");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);

            itemsData = new HomePageFollow();
            itemsData.setName("Saurabh");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);

            itemsData = new HomePageFollow();
            itemsData.setName("Reena");
            itemsData.setFollower_count("2234");
            itemsData.setProfile_pic("http://lorempixel.com/200/200/people/");
            homePageFollows.add(itemsData);


            footerHolder.homeChildRecView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            footerHolder.homeChildRecView.setLayoutManager(layoutManager);


/*
            ArrayList<String> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add("Fragment " + -1 + " / Item " + i);
            }
*/

            DemoFollowAdapter adapter = new DemoFollowAdapter(homePageFollows);
            footerHolder.homeChildRecView.setAdapter(adapter);


        } else if (holder instanceof ViewHolder) {
            final Datum model = getItem(position - 1);


            ViewHolder genericViewHolder = (ViewHolder) holder;

            switch (position) {
                case 0:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_1));
                    break;
                case 1:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_2));
                    break;
                case 2:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_3));
                    break;
                case 3:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_4));
                    break;
                case 4:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_5));
                    break;

                case 5:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_1));
                    break;
                case 6:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_2));
                    break;
                case 7:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_3));
                    break;
                case 8:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_4));
                    break;
                case 9:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_5));
                    break;

                default:
                    genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_1));
                    break;

            }
/*
            if (position % 2 == 0)
                genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_2));
            else
                genericViewHolder.rlRoot.setBackground(mContext.getResources().getDrawable(R.drawable.gradient_3));
*/

            genericViewHolder.itemTxtTitle.setText(model.getHeader());

            Random r = new Random();
            int min = 40;
            int max = 250;
            int random = r.nextInt((max - min) + 1) + min;
            genericViewHolder.tvCount.setText(String.valueOf(random));

            genericViewHolder.homeChildRecView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            genericViewHolder.homeChildRecView.setLayoutManager(layoutManager);

/*
            ArrayList<String> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add("Fragment " + -1 + " / Item " + i);
            }
*/

            DemoAdapter adapter = new DemoAdapter((ArrayList<Content>) model.getContent(), mItemClickListener);
            genericViewHolder.homeChildRecView.setAdapter(adapter);

/*
            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getMessage());
*/


        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == modelList.size() + 1;
    }


    @Override
    public int getItemCount() {

        return modelList.size() + 2;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnHeaderClickListener(final OnHeaderClickListener headerClickListener) {
        this.mHeaderClickListener = headerClickListener;
    }

    public void SetOnFooterClickListener(final OnFooterClickListener footerClickListener) {
        this.mFooterClickListener = footerClickListener;
    }

    private Datum getItem(int position) {
        return modelList.get(position);
    }

    private void setHdrPager(HeaderViewHolder headerHolder) {
//        final List <String> videoList = new ArrayList<>();
        currentPage = 0;

        List<HomeHdrPagerItem> homeHdrPagerItems = new ArrayList<>();

        HomeHdrPagerItem item = new HomeHdrPagerItem();
        //       item.setHeader(c.getString("header_id"));
        item.setThumbnailUrl("https://0.ptlp.co/init/banner?language=HINDI&name=Jan-18-hi-5.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        item = new HomeHdrPagerItem();
        item.setThumbnailUrl("https://3.ptlp.co/init/banner?language=HINDI&name=Jan-18-hi-3a.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        item = new HomeHdrPagerItem();
        item.setThumbnailUrl("https://2.ptlp.co/init/banner?language=HINDI&name=Jan-18-hi-2.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        item = new HomeHdrPagerItem();
        item.setThumbnailUrl("https://1.ptlp.co/init/banner?language=HINDI&name=Jan-18-hi-1.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        headerHolder.pagerHdr.setOffscreenPageLimit(1);
        PagerAdapterHomeHeader pagerAdapterHomeHeader = new PagerAdapterHomeHeader(mContext, homeHdrPagerItems);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            headerHolder.pagerHdr.setNestedScrollingEnabled(false);
        }

        headerHolder.pagerHdr.setScrollContainer(false);

        headerHolder.pagerHdr.setClipToPadding(false);
        headerHolder.pagerHdr.setPadding(50, 0, 50, 0);
        headerHolder.pagerHdr.setPageMargin(20);

//        headerHolder.pagerHdr.setPageTransformer(true, new StackTransformer());
        headerHolder.pagerHdr.setAdapter(pagerAdapterHomeHeader);

        headerHolder.pagerHdr.startAutoScroll();
        headerHolder.pagerHdr.setInterval(3000);
        headerHolder.pagerHdr.setCycle(true);
        headerHolder.pagerHdr.setScrollDurationFactor(4);
        headerHolder.pagerHdr.setStopScrollWhenTouch(true);


/*
        mViewPager_hdr.setPageTransformer(true, new StackTransformer());
*/


//        indicator_hdr.setViewPager(mViewPager_hdr);

/*
        mViewPager_hdr.setInterval(4200);
        mViewPager_hdr.startAutoScroll();
*/
//        _search.setVisibility(View.GONE);

/*
        handler = new Handler();
        Update = new Runnable() {
            public void run() {
                if (currentPage == homeHdrPagerItems.size()) {
                    currentPage = 0;
                }
                headerHolder.pagerHdr.setCurrentItem(currentPage++, true);
            }
        };
        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1500, 1500);
*/
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
//        handler.removeCallbacks(Update);
//        swipeTimer.cancel();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Content model);

        void onItemClick(View view, int position, Datum model);

        void onItemClick(View view, int position, Product model);
    }

    public interface OnHeaderClickListener {
        void onHeaderClick(View view, String headerTitle);
    }

    public interface OnFooterClickListener {
        void onFooterClick(View view, String headerTitle);
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView txtFooter;
        private RecyclerView homeChildRecView;

        public FooterViewHolder(final View itemView) {
            super(itemView);
//            this.txtFooter = (TextView) itemView.findViewById(R.id.txtFooter);

            this.homeChildRecView = itemView.findViewById(R.id.fragment_home_child_recycler_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mFooterClickListener.onFooterClick(itemView, mFooterTitle);
                }
            });

        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleHeader;
        //        ViewPager pagerHdr;
        AutoScrollViewPager pagerHdr;

        public HeaderViewHolder(final View itemView) {
            super(itemView);
            this.pagerHdr = itemView.findViewById(R.id.pager_hdr);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHeaderClickListener.onHeaderClick(itemView, mHeaderTitle);
                }
            });

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*
                private ImageView imgUser;
                private TextView itemTxtTitle;
        */
        private TextView tvCount;
        private TextView itemTxtTitle;
        private RelativeLayout rlRoot;

        private RecyclerView homeChildRecView;


        // @BindView(R.id.img_user)
        // ImageView imgUser;
        // @BindView(R.id.item_txt_title)
        // TextView itemTxtTitle;
        // @BindView(R.id.item_txt_message)
        // TextView itemTxtMessage;
        // @BindView(R.id.radio_list)
        // RadioButton itemTxtMessage;
        // @BindView(R.id.check_list)
        // CheckBox itemCheckList;
        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.homeChildRecView = itemView.findViewById(R.id.fragment_home_child_recycler_view);
            this.itemTxtTitle = itemView.findViewById(R.id.title_text_view);
            this.tvCount = itemView.findViewById(R.id.tv_count);
            this.rlRoot = itemView.findViewById(R.id.rl_root);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition() - 1));


                }
            });

        }
    }

}

