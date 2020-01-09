package com.laoxu.a1707bcartdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.entity.OrderEntity;
import com.laoxu.a1707bcartdemo.widget.NumLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.VH> {

    private Context context;
    private List<OrderEntity.Order.Product> list;

    public OrderProductAdapter(Context context, List<OrderEntity.Order.Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.order_product_item_layout, null);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {


        holder.name.setText(list.get(position).commodityName);
        holder.price.setText("¥：" + list.get(position).commodityPrice);
        String picUrl = list.get(position).commodityPic;
        String[] pics = picUrl.split(",");
        if (pics!=null&&pics.length>0){
            Glide.with(context).load(pics[0]).into(holder.iv);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.iv)
        ImageView iv;

        @BindView(R.id.layout_num)
        NumLayout numLayout;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
