package com.pratilipi.hackathon.unbranded.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.androidnetworking.error.ANError;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home.TrendingProductAdapter;
import com.pratilipi.hackathon.unbranded.home.fragment.ChildFragment.home.TrendingRecyclerViewAdapter;
import com.pratilipi.hackathon.unbranded.model.homepage.Content;
import com.pratilipi.hackathon.unbranded.model.homepage.Datum;
import com.pratilipi.hackathon.unbranded.network.ApiEndPoint;
import com.pratilipi.hackathon.unbranded.network.model.Product;
import com.pratilipi.hackathon.unbranded.network.model.User;
import com.pratilipi.hackathon.unbranded.network.model.UserProduct;
import com.pratilipi.hackathon.unbranded.rxjava.AppSchedulerProvider;
import com.pratilipi.hackathon.unbranded.utils.AppConstants;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


/**
 *
 */
public class ProfileProductsFragment extends Fragment {

    @BindView(R.id.product_recycler_view)
    RecyclerView productRecyclerView;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    Unbinder unbinder;
    private FrameLayout fragmentContainer;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CompositeDisposable mCompositeDisposable;

    /**
     * Create a new instance of the fragment
     *
     * @param mUserId
     */
    private String mUserId;

    public static ProfileProductsFragment newInstance(String mUserId) {
        ProfileProductsFragment fragment = new ProfileProductsFragment();
        Bundle b = new Bundle();
        b.putString(AppConstants.EXTRA_USER, mUserId);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_products, container, false);
//        initDemoList(view);
        unbinder = ButterKnife.bind(this, view);

        if (getArguments() != null)
            mUserId = getArguments().getString(AppConstants.EXTRA_USER);

        mCompositeDisposable = new CompositeDisposable();

        getDataProductListFromServer(mUserId);
        return view;

    }

    private void getDataProductListFromServer(String userId) {
        mCompositeDisposable.add(getUserProudcts(userId)
                .subscribeOn(new AppSchedulerProvider().io())
                .observeOn(new AppSchedulerProvider().ui())
                .subscribe(new Consumer<UserProduct>() {
                    @Override
                    public void accept(UserProduct userProduct) throws Exception {
                        if (userProduct != null) {
                            setUpAdapter(userProduct);
                        }


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


    private Observable<UserProduct> getUserProudcts(String userId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_USER_PRODUCTS)
                .addHeaders("user-Id", userId)
                .build()
                .getObjectObservable(UserProduct.class);
    }


    private void setUpAdapter(UserProduct products) {
        productRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productRecyclerView.setLayoutManager(layoutManager);

/*
            ArrayList<String> itemsData = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                itemsData.add("Fragment " + -1 + " / Item " + i);
            }
*/

        TrendingProductAdapter adapter = new TrendingProductAdapter(products.getProductList(),
                new TrendingRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, Content model) {

                    }

                    @Override
                    public void onItemClick(View view, int position, User model) {

                    }

                    @Override
                    public void onItemClick(View view, int position, Product model) {

                    }

                    @Override
                    public void onItemClick(View view, int position, Datum model) {

                    }
                });
        productRecyclerView.setAdapter(adapter);


    }
    /**
     * Init the fragment
     */
/*
    private void initDemoList(View view) {

        fragmentContainer = view.findViewById(R.id.fragment_container);
        recyclerView = view.findViewById(R.id.fragment_demo_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> itemsData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
        }

        DemoAdapter adapter = new DemoAdapter(itemsData);
        recyclerView.setAdapter(adapter);
    }
*/

    /**
     * Refresh
     */
    public void refresh() {
        if (getArguments().getInt("index", 0) > 0 && recyclerView != null) {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (fragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            fragmentContainer.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden() {
        if (fragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            fragmentContainer.startAnimation(fadeOut);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.dispose();
        unbinder.unbind();
    }

    public void onPageRefresh() {
        try {
            getDataProductListFromServer(mUserId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
