package com.laoxu.a1707bcartdemo.api;

import com.laoxu.a1707bcartdemo.entity.OrderEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface OrderApiservice {

    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<OrderEntity> getOrderData(@Header("userId") String uid, @Header("sessionId") String sid, @Query("status") int status, @Query("page") int page, @Query("count") int count);
}
