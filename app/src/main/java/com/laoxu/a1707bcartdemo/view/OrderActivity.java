package com.laoxu.a1707bcartdemo.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.adapter.OrderAdapter;
import com.laoxu.a1707bcartdemo.api.Api;
import com.laoxu.a1707bcartdemo.api.OrderApiservice;
import com.laoxu.a1707bcartdemo.entity.OrderEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.rv_order)
    XRecyclerView rvOrder;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_shouhuo)
    TextView tvShouhuo;

    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 0;
                requestData();
            }
        });
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 1;
                requestData();
            }
        });
        tvShouhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 2;
                requestData();
            }
        });
    }

    private void initData() {
        requestData();
    }

    /**
     * 请求订单数据
     */
    private void requestData() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder(
        ).addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        OrderApiservice orderApiservice = retrofit.create(OrderApiservice.class);

        orderApiservice.getOrderData("27798", "157854043506827798", status, 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderEntity>() {
                    /**
                     * 成功的回调
                     * @param orderEntity
                     * @throws Exception
                     */
                    @Override
                    public void accept(OrderEntity orderEntity) throws Exception {

                        //写适配器
                        OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this, orderEntity.orderList);
                        rvOrder.setAdapter(orderAdapter);


                    }
                }, new Consumer<Throwable>() {
                    /**
                     * 失败的回调
                     * @param throwable
                     * @throws Exception
                     */
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }


}
