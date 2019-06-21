package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.model.homepage.Content;
import com.pratilipi.hackathon.unbranded.model.homepage.Datum;
import com.pratilipi.hackathon.unbranded.network.model.Data;
import com.pratilipi.hackathon.unbranded.network.model.Product;
import com.pratilipi.hackathon.unbranded.network.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.pratilipi.hackathon.unbranded.utils.AppConstants.PRODUCT_LIST;
import static com.pratilipi.hackathon.unbranded.utils.AppConstants.PRODUCT_STORE;
import static com.pratilipi.hackathon.unbranded.utils.AppConstants.USER_STORE;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_USER_STORE = 4;
    private static final int TYPE_ITEM_STORE = 3;
    Handler handler;
    Timer swipeTimer;
    int currentPage = 0;

    private String mHeaderTitle;
    private String mFooterTitle;
    private OnHeaderClickListener mHeaderClickListener;
    private OnFooterClickListener mFooterClickListener;
    private Context mContext;
    private ArrayList<Data> dataList;
    private OnItemClickListener mItemClickListener;
    private Runnable Update;


    public TrendingRecyclerViewAdapter(Context context, ArrayList<Data> data, String headerTitle, String footerTitle, OnItemClickListener mItemClickListener) {
        this.mContext = context;
        this.dataList = data;
        this.mHeaderTitle = headerTitle;
        this.mFooterTitle = footerTitle;
        this.mItemClickListener = mItemClickListener;
    }

//    public void updateList(ArrayList<Datum> modelList) {
//        this.modelList = modelList;
//        notifyDataSetChanged();
//
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_footer, parent, false);
            return new FooterViewHolder(v);
        } else if (viewType == TYPE_ITEM_STORE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_store, parent, false);
            return new StoreViewHolder(v);
        } else if (viewType == TYPE_USER_STORE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_user_store, parent, false);
            return new UserStoreViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_product, parent, false);
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
/*            FooterViewHolder footerHolder = (FooterViewHolder) holder;

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


*//*
            ArrayList<String> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add("Fragment " + -1 + " / Item " + i);
            }
*//*

            DemoFollowAdapter adapter = new DemoFollowAdapter(homePageFollows);
            footerHolder.homeChildRecView.setAdapter(adapter);*/


        } else if (holder instanceof StoreViewHolder) {
            final Data data = dataList.get(position - 1);


            StoreViewHolder genericViewHolder = (StoreViewHolder) holder;

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

            genericViewHolder.itemTxtTitle.setText("Stores ");

//            Random r = new Random();
//            int min = 40;
//            int max = 250;
//            int random = r.nextInt((max - min) + 1) + min;
//            genericViewHolder.tvCount.setText(String.valueOf(random));

            genericViewHolder.homeChildRecView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            genericViewHolder.homeChildRecView.setLayoutManager(layoutManager);

/*
            ArrayList<String> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add("Fragment " + -1 + " / Item " + i);
            }
*/

            TrendingStoreAdapter adapter = new TrendingStoreAdapter((ArrayList<User>) data.getUsers(), mItemClickListener);
            genericViewHolder.homeChildRecView.setAdapter(adapter);

/*
            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getMessage());
*/


        } else if (holder instanceof UserStoreViewHolder) {
            final Data data = dataList.get(position - 1);

            User user = data.getUserProducts().get(0).getUserList().get(0);

            UserStoreViewHolder genericViewHolder = (UserStoreViewHolder) holder;


            genericViewHolder.userName.setText(user.getName());

            genericViewHolder.userFollowers.setText(String.format(Locale.getDefault(),
                    "%d followers", user.getFollowers()));


            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.iv_viewpagerdefault)
                    .error(R.drawable.iv_viewpagerdefault)
                    .priority(Priority.HIGH)
//                .fitCenter()
                    .error(R.drawable.iv_viewpagerdefault)
                    .placeholder(R.drawable.iv_viewpagerdefault)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(8, 0,
                            RoundedCornersTransformation.CornerType.ALL))
                    .diskCacheStrategy(DiskCacheStrategy.ALL);


            if (user.getImageUrl() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(user.getImageUrl())
                        .apply(options)
                        .into(genericViewHolder.userImage);
            }

//            Random r = new Random();
//            int min = 40;
//            int max = 250;
//            int random = r.nextInt((max - min) + 1) + min;
//            genericViewHolder.tvCount.setText(String.valueOf(random));

            genericViewHolder.homeChildRecView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            genericViewHolder.homeChildRecView.setLayoutManager(layoutManager);

/*
            ArrayList<String> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add("Fragment " + -1 + " / Item " + i);
            }
*/

            TrendingUserStoreAdapter adapter = new TrendingUserStoreAdapter((ArrayList<Product>) data.getProducts(), mItemClickListener);
            genericViewHolder.homeChildRecView.setAdapter(adapter);

/*
            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getMessage());
*/


        } else if (holder instanceof ViewHolder) {
            try {
                final Data data = dataList.get(position - 1);


                ViewHolder viewHolder = (ViewHolder) holder;


//            Random r = new Random();
//            int min = 40;
//            int max = 250;
//            int random = r.nextInt((max - min) + 1) + min;
//            viewHolder.tvCount.setText(String.valueOf(random));

                viewHolder.homeChildRecView.setHasFixedSize(true);
                GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                viewHolder.homeChildRecView.setLayoutManager(layoutManager);
                viewHolder.homeChildRecView.setNestedScrollingEnabled(true);


                TrendingProductAdapter adapter = new TrendingProductAdapter((ArrayList<Product>) data.getProducts(), mItemClickListener);
                viewHolder.homeChildRecView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }


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
        return getItemType(position - 1);
    }

    private int getItemType(int position) {
        switch (dataList.get(position).getType()) {
            case PRODUCT_LIST:
                return TYPE_ITEM;
            case PRODUCT_STORE:
                return TYPE_ITEM_STORE;
            case USER_STORE:
                return TYPE_USER_STORE;
            default:
                return TYPE_ITEM;
        }

    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == dataList.size() + 1;
    }


    @Override
    public int getItemCount() {

        return dataList.size() + 2;
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

//    private Data getItem(int position) {
//        return modelList.get(position);
//    }

    private void setHdrPager(HeaderViewHolder headerHolder) {
//        final List <String> videoList = new ArrayList<>();
        currentPage = 0;

        List<HomeHdrPagerItem> homeHdrPagerItems = new ArrayList<>();

        HomeHdrPagerItem item = new HomeHdrPagerItem();
        //       item.setHeader(c.getString("header_id"));
        item.setThumbnailUrl("https://i.pinimg.com/originals/b8/ce/12/b8ce12af4e594bcb26b8f55b0377dad4.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        item = new HomeHdrPagerItem();
        item.setThumbnailUrl("https://s3.envato.com/files/180378926/BEE-1237-Fashion%20Sale%20Banners_01_preview3.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        item = new HomeHdrPagerItem();
        item.setThumbnailUrl("https://dcassetcdn.com/design_img/2507911/86989/86989_13329833_2507911_307772c9_image.jpg");
        item.setTitle("");
        item.setSubTitle("");
        homeHdrPagerItems.add(item);

        item = new HomeHdrPagerItem();
        item.setThumbnailUrl("https://i.pinimg.com/originals/9a/71/6f/9a716f90fc2e24079b8960168d5ea089.jpg");
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

        void onItemClick(View view, int position, User model);

        void onItemClick(View view, int position, Product model);

        void onItemClick(View view, int position, Datum model);
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


        private RecyclerView homeChildRecView;


        public ViewHolder(final View itemView) {
            super(itemView);


            this.homeChildRecView = itemView.findViewById(R.id.fragment_home_child_recycler_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }
    }

    public class StoreViewHolder extends RecyclerView.ViewHolder {

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
        public StoreViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.homeChildRecView = itemView.findViewById(R.id.fragment_home_child_recycler_view);
            this.itemTxtTitle = itemView.findViewById(R.id.title_text_view);
//            this.tvCount = itemView.findViewById(R.id.tv_count);
            this.rlRoot = itemView.findViewById(R.id.rl_root);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), dataList.get(getAdapterPosition() - 1));


                }
            });

        }
    }

    public class UserStoreViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image)
        AppCompatImageView userImage;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.user_followers)
        TextView userFollowers;
        @BindView(R.id.follower)
        TextView follower;
        @BindView(R.id.user_layout)
        RelativeLayout userLayout;
        @BindView(R.id.tv_all)
        TextView tvAll;
        @BindView(R.id.rl_root)
        RelativeLayout rlRoot;
        /*
                private ImageView imgUser;
                private TextView itemTxtTitle;
        */
        private TextView tvCount;
        private TextView itemTxtTitle;
        private RecyclerView homeChildRecView;

        public UserStoreViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            this.homeChildRecView = itemView.findViewById(R.id.fragment_home_child_recycler_view);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), dataList.get(getAdapterPosition() - 1));


                }
            });

        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
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

        public ItemViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

