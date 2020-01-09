package com.laoxu.a1707bcartdemo.entity;

import java.util.List;

public class OrderEntity {
    public String message;
    public String status;
    public List<Order> orderList;

    public static class Order{
        public String expressCompName;
        public String expressSn;
        public String orderId;
        public String orderStatus;
        public String orderTime;
        public String payAmount;
        public String payMethod;
        public String userId;

        public List<Product> detailList;

         public static class Product{
             public String commentStatus;
             public String commodityCount;
             public String commodityId;
             public String commodityName;
             public String commodityPic;
             public String commodityPrice;
             public String orderDetailId;

         }
    }
}
