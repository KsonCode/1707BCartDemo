package com.laoxu.a1707bcartdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.laoxu.a1707bcartdemo.adapter.CartAdapter;
import com.laoxu.a1707bcartdemo.api.Api;
import com.laoxu.a1707bcartdemo.api.CartApiService;
import com.laoxu.a1707bcartdemo.entity.CartEntity;
import com.laoxu.a1707bcartdemo.view.PayDesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {


    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    private Unbinder bind;

    @BindView(R.id.rv_cart_one)
    XRecyclerView oneRv;

    CartAdapter cartAdapter;

    private int page = 1;//首页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind = ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        oneRv.setLayoutManager(new LinearLayoutManager(this));

        oneRv.setLoadingMoreEnabled(false);//默认没有加载更多效果，设置成true
//        oneRv.setLoadingListener(new XRecyclerView.LoadingListener() {
//            //刷新
//            @Override
//            public void onRefresh() {
//
//            }
//
//            //加载更多
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
        oneRv.setLoadingListener(this);//设置监听
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartAdapter != null) {
                    if (checkboxAll.isChecked()) {
                        for (CartEntity.Cart cart : cartAdapter.getList()) {
                            cart.isCartChecked = true;
                            for (CartEntity.Cart.Product product : cart.shoppingCartList) {
                                product.isProductChecked = true;
                            }
                        }

                    } else {
                        for (CartEntity.Cart cart : cartAdapter.getList()) {
                            cart.isCartChecked = false;

                            for (CartEntity.Cart.Product product : cart.shoppingCartList) {
                                product.isProductChecked = false;
                            }

                        }

                    }

                    cartAdapter.notifyDataSetChanged();

                }

                totalPrice();


            }
        });

        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, PayDesActivity.class));
            }
        });

    }


    /**
     * 计算总价
     */
    public void totalPrice() {
        StringBuilder stringBuilder = new StringBuilder();
        double totalPrice = 0;
        for (CartEntity.Cart cart : cartAdapter.getList()) {
            stringBuilder.append(cart.isCartChecked);
            //得到所有商品对象
            for (CartEntity.Cart.Product product : cart.shoppingCartList) {
                stringBuilder.append(product.isProductChecked);
                if (product.isProductChecked) {
                    totalPrice += product.price * product.num;
                }
            }

        }

        System.out.println("sb===" + stringBuilder.toString());
        if (stringBuilder.toString().contains("false")) {
            checkboxAll.setChecked(false);
        } else {
            checkboxAll.setChecked(true);
        }
        tvPrice.setText("¥:" + totalPrice);


    }

    private void initData() {
        requestCart();

    }

    /**
     * 请求购物差
     */
    private void requestCart() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder(
        ).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        CartApiService cartApiService = retrofit.create(CartApiService.class);
        cartApiService.getCarts("27798", "157854043506827798")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartEntity cartEntity) {

                        if (page == 1) {//刷新的功能
                            oneRv.refreshComplete();//刷新成功后，消失
                            //展示数据
                            cartAdapter = new CartAdapter(MainActivity.this, cartEntity.result);
                            oneRv.setAdapter(cartAdapter);
                        } else {
                            oneRv.loadMoreComplete();
                            cartAdapter.addMoreData(cartEntity.result);//加载更多的数据
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        page = 1;
        requestCart();

    }

    /**
     *
     */
    @Override
    public void onLoadMore() {
        page++;
        requestCart();
    }
}
