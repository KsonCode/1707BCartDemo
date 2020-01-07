package com.laoxu.a1707bcartdemo.entity;

import java.util.List;

public class CartEntity {
    public String message;
    public String status;

    public List<Cart> result;

     public static class Cart{
         public boolean isCartChecked;//标示，标志位
         public String categoryName;
         public List<Product> shoppingCartList;

         public static class Product{
             public boolean isProductChecked;
             public String commodityId;
             public String commodityName;
             public String count;
             public String pic;
             public double price;
         }


     }

}
