package com.laoxu.a1707bcartdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.laoxu.a1707bcartdemo.MainActivity;
import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.entity.CartEntity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends XRecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context context;
    private List<CartEntity.Cart> list;

    public CartAdapter(Context context, List<CartEntity.Cart> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =View.inflate(context, R.layout.cart_item_layout,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    /**
     * 暴露list集合
     * @return
     */
    public List<CartEntity.Cart> getList() {
        return list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (list.get(position).isCartChecked){

            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (CartEntity.Cart.Product product : list.get(position).shoppingCartList) {
            stringBuilder.append(product.isProductChecked);
        }

        if (stringBuilder.toString().contains("false")){
            holder.checkBox.setChecked(false);
            list.get(position).isCartChecked = false;
        }else{
            holder.checkBox.setChecked(true);
            list.get(position).isCartChecked = true;
        }

        holder.textView.setText(list.get(position).categoryName);

        holder.rv.setLayoutManager(new LinearLayoutManager(context));

        ProductAdapter productAdapter = new ProductAdapter(context,list.get(position).shoppingCartList);
        holder.rv.setAdapter(productAdapter);

        productAdapter.setNotifyCartListener(new ProductAdapter.NotifyCartListener() {
            @Override
            public void notifyCart() {
                        notifyDataSetChanged();

        MainActivity mainActivity = (MainActivity) context;
        mainActivity.totalPrice();
            }
        });


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()){
                    list.get(position).isCartChecked = true;
                    for (CartEntity.Cart.Product product : list.get(position).shoppingCartList) {
                        product.isProductChecked = true;
                    }
                }else{
                    list.get(position).isCartChecked = false;
                    for (CartEntity.Cart.Product product : list.get(position).shoppingCartList) {
                        product.isProductChecked = false;
                    }
                }
                notifyDataSetChanged();
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.totalPrice();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 加载分页数据
     * @param result
     */
    public void addMoreData(List<CartEntity.Cart> result) {
        if (result!=null){
            list.addAll(result);
            notifyDataSetChanged();
        }

    }

    static class MyViewHolder extends XRecyclerView.ViewHolder{

        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.name)
        TextView textView;
        @BindView(R.id.rv_product)
        RecyclerView rv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


//    /**
//     * 接收二级的选中状态的消息通知
//     * @param from
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getProductCheckedstatus(String from){
//
//        notifyDataSetChanged();
//
//        MainActivity mainActivity = (MainActivity) context;
//        mainActivity.totalPrice();
//    }
}
