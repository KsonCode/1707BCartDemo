package com.laoxu.a1707bcartdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.entity.CartEntity;

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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
