package com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.base.BaseFragment;
import com.pratilipi.hackathon.unbranded.detail.DetailActivity;
import com.pratilipi.hackathon.unbranded.model.homepage.Content;
import com.pratilipi.hackathon.unbranded.model.homepage.Datum;
import com.pratilipi.hackathon.unbranded.network.ApiEndPoint;
import com.pratilipi.hackathon.unbranded.network.model.Data;
import com.pratilipi.hackathon.unbranded.network.model.Product;
import com.pratilipi.hackathon.unbranded.network.model.Trending;
import com.pratilipi.hackathon.unbranded.network.model.User;
import com.pratilipi.hackathon.unbranded.rxjava.AppSchedulerProvider;
import com.pratilipi.hackathon.unbranded.utils.AppConstants;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.io.Serializable;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RecyclerViewHomeFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = RecyclerViewHomeFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerView;

    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;


    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

//    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private TrendingRecyclerViewAdapter mAdapter;
    private RecyclerViewScrollListener scrollListener;

    private ArrayList<AbstractModel> modelList = new ArrayList<>();


    @SuppressLint("ValidFragment")

    public RecyclerViewHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerViewHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerViewHomeFragment newInstance(String param1, String param2) {
        RecyclerViewHomeFragment fragment = new RecyclerViewHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static RecyclerViewHomeFragment newInstance() {
        RecyclerViewHomeFragment fragment = new RecyclerViewHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycler_view_home, container, false);

        // ButterKnife.bind(this);
        setUp(view);

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        setAdapter();

//        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                // Do your stuff on refresh
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (swipeRefreshRecyclerList.isRefreshing())
//                            swipeRefreshRecyclerList.setRefreshing(false);
//                    }
//                }, 5000);
//
//            }
//        });


    }

    @Override
    protected void setUp(View view) {
        findViews(view);
        getDataApi();

    }


    private void findViews(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        swipeRefreshRecyclerList = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_recycler_list);
    }


    private void getDataApi() {

//        getActivity().showLoading();
        getCompositeDisposable().add(getHomePageContent()
                .subscribeOn(new AppSchedulerProvider().io())
                .observeOn(new AppSchedulerProvider().ui())
                .subscribe(new Consumer<Trending>() {
                    @Override
                    public void accept(Trending homePageContent) throws Exception {
                        if (homePageContent != null && homePageContent.getData() != null) {
                            Log.d("homePageContent ", homePageContent.toString());

                            setAdapter(homePageContent);
                        }
//                        RecyclerViewHomeFragment.this.hideLoading();


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
//                    hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            Log.d("anError ", anError.toString());
//                            handleApiError(anError);
                        }
                    }
                }));

    }

    private Observable<Trending> getHomePageContent() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_TRENDING_HOME)
                .build()
                .getObjectObservable(Trending.class);
    }


    private void setAdapter(Trending homePageContent) {

        mAdapter = new TrendingRecyclerViewAdapter(getActivity(), (ArrayList<Data>) homePageContent.getData(), "Header", "Footer",
                new TrendingRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, Content model) {

                    }

                    @Override
                    public void onItemClick(View view, int position, User model) {

                    }

                    @Override
                    public void onItemClick(View view, int position, Product model) {

                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(AppConstants.EXTRA_PRODUCT, (Serializable) model);
                        startActivity(intent);

                    }

                    @Override
                    public void onItemClick(View view, int position, Datum model) {

                    }
                });
//        mAdapter = new RecyclerViewAdapter(getActivity(), modelList, "Header", "Footer");


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        scrollListener = new RecyclerViewScrollListener() {

            public void onEndOfScrollReached(RecyclerView rv) {

//                Toast.makeText(getActivity(), "End of the RecyclerView reached. Do your pagination stuff here", Toast.LENGTH_SHORT).show();

                scrollListener.disableScrollListener();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
          /*
             Note: The below two methods should be used wisely to handle the pagination enable and disable states based on the use case.
                     1. scrollListener.disableScrollListener(); - Should be called to disable the scroll state.
                     2. scrollListener.enableScrollListener(); - Should be called to enable the scroll state.
          */


//        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, Datum model) {
//                Log.d(TAG, "onItemClick: " + position) ;
//                //handle item click events here
//                Toast.makeText(getActivity(), "Hey " + model.getHeader(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });


        mAdapter.SetOnHeaderClickListener(new TrendingRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, String headerTitle) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey I am a header", Toast.LENGTH_SHORT).show();

            }
        });

        mAdapter.SetOnFooterClickListener(new TrendingRecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(View view, String footerTitle) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey I am a footer", Toast.LENGTH_SHORT).show();

            }
        });


    }

/*

    private void setAdapter(Trending homePageContent) {

        mAdapter = new RecyclerViewAdapter(getActivity(), (ArrayList<Data>) homePageContent.getData(), "Header", "Footer", new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Content model) {
                Log.d(TAG, "Content onItemClick: " + position);
                startActivity(new Intent(getActivity(), DetailActivity.class));
            }

            @Override
            public void onItemClick(View view, int position, Datum model) {
                Log.d(TAG, "Datum onItemClick: " + position);
            }
        });
//        mAdapter = new RecyclerViewAdapter(getActivity(), modelList, "Header", "Footer");


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        scrollListener = new RecyclerViewScrollListener() {

            public void onEndOfScrollReached(RecyclerView rv) {

//                Toast.makeText(getActivity(), "End of the RecyclerView reached. Do your pagination stuff here", Toast.LENGTH_SHORT).show();

                scrollListener.disableScrollListener();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
          */
/*
             Note: The below two methods should be used wisely to handle the pagination enable and disable states based on the use case.
                     1. scrollListener.disableScrollListener(); - Should be called to disable the scroll state.
                     2. scrollListener.enableScrollListener(); - Should be called to enable the scroll state.
          *//*



//        mAdapter.SetOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, Datum model) {
//                Log.d(TAG, "onItemClick: " + position) ;
//                //handle item click events here
//                Toast.makeText(getActivity(), "Hey " + model.getHeader(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });


        mAdapter.SetOnHeaderClickListener(new RecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, String headerTitle) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey I am a header", Toast.LENGTH_SHORT).show();

            }
        });

        mAdapter.SetOnFooterClickListener(new RecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(View view, String footerTitle) {

                //handle item click events here
                Toast.makeText(getActivity(), "Hey I am a footer", Toast.LENGTH_SHORT).show();

            }
        });


    }

*/

}
