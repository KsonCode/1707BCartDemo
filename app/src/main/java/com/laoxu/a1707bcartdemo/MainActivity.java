package com.laoxu.a1707bcartdemo;

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

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private Unbinder bind;

    @BindView(R.id.rv_cart_one)
    XRecyclerView oneRv;

    CartAdapter cartAdapter;

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
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (cartAdapter!=null){

                    if (checkboxAll.isChecked()){
                        for (CartEntity.Cart cart : cartAdapter.getList()) {
                            cart.isCartChecked = true;

                            for (CartEntity.Cart.Product product : cart.shoppingCartList) {
                                product.isProductChecked = true;
                            }

                        }

                    }else{
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

    }

    /**
     * 计算总价
     */
    public void totalPrice() {
        double totalPrice = 0;
        for (CartEntity.Cart cart : cartAdapter.getList()) {
            //得到所有商品对象
            for (CartEntity.Cart.Product product : cart.shoppingCartList) {
                totalPrice +=product.price;
            }

        }

        if (checkboxAll.isChecked()){
            tvPrice.setText("¥:"+totalPrice);
        }else{
            tvPrice.setText("¥:0");
        }



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

//        cartApiService.getCarts("159","1578297156922159")
//                .subscribe(new Consumer<CartEntity>() {
//                    @Override
//                    public void accept(CartEntity cartEntity) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });

        cartApiService.getCarts("159", "1578297156922159")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CartEntity cartEntity) {

                        //展示数据
                       cartAdapter = new CartAdapter(MainActivity.this, cartEntity.result);
                        oneRv.setAdapter(cartAdapter);

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
}
