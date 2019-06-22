package com.pratilipi.hackathon.unbranded.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pratilipi.hackathon.unbranded.R;
import com.pratilipi.hackathon.unbranded.network.model.Product;
import com.pratilipi.hackathon.unbranded.utils.AppConstants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.product_image)
    AppCompatImageView productImage;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.product_qty_minus)
    RelativeLayout productQtyMinus;
    @BindView(R.id.product_qty)
    TextView productQty;
    @BindView(R.id.product_qty_add)
    RelativeLayout productQtyAdd;
    @BindView(R.id.product_buy_price)
    TextView productBuyPrice;
    @BindView(R.id.product_buy_price_total)
    TextView productBuyPriceTotal;
    @BindView(R.id.product_buy_final)
    TextView productBuyFinal;

    @BindView(R.id.place_order)
    TextView placeOrder;

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        mProduct = (Product) getIntent().getExtras().getSerializable(AppConstants.EXTRA_PRODUCT);
        if (mProduct == null)
            onBackPressed();

        String price = String.format(Locale.getDefault(),
                getResources().getString(R.string.Rs) + " %d", mProduct.getPrice());

        productName.setText(mProduct.getName());
        productBuyFinal.setText(price);
        productBuyPrice.setText(price);
        productPrice.setText(price);
        productBuyPriceTotal.setText(price);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.iv_viewpagerdefault)
                .error(R.drawable.iv_viewpagerdefault)
                .priority(Priority.HIGH)
//                .fitCenter()
                .error(R.drawable.iv_viewpagerdefault)
                .placeholder(R.drawable.iv_viewpagerdefault)
                .centerCrop()
                .transform(new RoundedCornersTransformation(16, 0,
                        RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        Glide.with(this)
                .load(mProduct.getImageUrl())
                .apply(options)
                .into(productImage);

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra(AppConstants.ORDER_PLACED, true);
                setResult(RESULT_OK, intent);

                Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_LONG).show();

                onBackPressed();

            }
        });

    }
}
