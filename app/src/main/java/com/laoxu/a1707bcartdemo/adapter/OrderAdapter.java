package com.laoxu.a1707bcartdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.entity.OrderEntity;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.VH> {

    private Context context;
    private List<OrderEntity.Order> list;

    public OrderAdapter(Context context, List<OrderEntity.Order> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.order_item_layout, null);

        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {


        holder.orderIdTv.setText("订单号  "+list.get(position).orderId);
        holder.timeTv.setText(list.get(position).orderTime);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        OrderProductAdapter orderProductAdapter = new OrderProductAdapter(context,list.get(position).detailList);
        holder.recyclerView.setAdapter(orderProductAdapter);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_product)
        RecyclerView recyclerView;
        @BindView(R.id.tv_orderid)
        TextView orderIdTv;
        @BindView(R.id.tv_time)
        TextView timeTv;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
