package com.laoxu.a1707bcartdemo.api;


import com.laoxu.a1707bcartdemo.entity.CartEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CartApiService {

    @GET("small/order/verify/v1/findShoppingCart")
    Observable<CartEntity> getCarts(@Header("userId") String uid, @Header("sessionId") String sid);
}
