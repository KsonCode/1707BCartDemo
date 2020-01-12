package com.laoxu.a1707bcartdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.laoxu.a1707bcartdemo.CartActivity;
import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.entity.CartEntity;
import com.laoxu.a1707bcartdemo.widget.NumLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends XRecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context context;
    private List<CartEntity.Cart.Product> list;

    public ProductAdapter(Context context, List<CartEntity.Cart.Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =View.inflate(context, R.layout.product_item_layout,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (list.get(position).isProductChecked){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        holder.price.setText(list.get(position).price+"");

        holder.textView.setText(list.get(position).commodityName);

        Glide.with(context).load(list.get(position).pic).into(holder.iv);

       TextView numTv =  holder.numLayout.findViewById(R.id.num);
       numTv.setText(list.get(position).num+"");
       holder.numLayout.setNum(list.get(position).num);

        holder.numLayout.setNumCallback(new NumLayout.NumCallback() {
            @Override
            public void numClick(int num) {
                list.get(position).num = num;
                notifyDataSetChanged();//通知刷新
                CartActivity mainActivity = (CartActivity) context;
                mainActivity.totalPrice();
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (holder.checkBox.isChecked()){
                    list.get(position).isProductChecked = true;
                }else{
                    list.get(position).isProductChecked = false;
                }

                //通知一级的适配器刷新数据

                notifyCartListener.notifyCart();








            }
        });


    }

    private NotifyCartListener notifyCartListener;

    public void setNotifyCartListener(NotifyCartListener notifyCartListener) {
        this.notifyCartListener = notifyCartListener;
    }

    public interface NotifyCartListener{
        void notifyCart();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends XRecyclerView.ViewHolder{

        @BindView(R.id.checkbox)
        CheckBox checkBox;
        @BindView(R.id.name)
        TextView textView;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.iv)
        ImageView iv;

        @BindView(R.id.layout_num)
        NumLayout numLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
