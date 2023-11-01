package com.example.shoeshop.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Models.OrderModel;
import com.example.shoeshop.R;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderModel> orderList;

    public OrderAdapter(Activity context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int i) {
        return orderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, @Nullable View view,@NonNull ViewGroup viewGroup) {
         TextView tvOrderId, tvFullname, tvPhoneOrder, tvAddressOrder, tvDateOrder, tvDateReceived, tvTotalPrice;
        View listItemView = view;
        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listItemView = inflater.inflate(R.layout.item_order, null, false);
        }
            tvOrderId = listItemView.findViewById(R.id.tvOrderId);
            tvFullname = listItemView.findViewById(R.id.tvFullName);
            tvAddressOrder = listItemView.findViewById(R.id.tvAddressOrder);
            tvPhoneOrder = listItemView.findViewById(R.id.tvPhoneOrder);
            tvDateOrder = listItemView.findViewById(R.id.tvDateOrder);
            tvDateReceived = listItemView.findViewById(R.id.tvDateReceived);
            tvTotalPrice = listItemView.findViewById(R.id.tvTotalPrice);
        // Lấy đối tượng OrderModel tương ứng với vị trí i
        OrderModel order = (OrderModel) getItem(i);
        // Gán dữ liệu từ đối tượng OrderModel vào các TextView tương ứng
        tvOrderId.setText(order.getOrderId());
        tvFullname.setText(order.getUserName());
        tvAddressOrder.setText(order.getAddress());
        tvPhoneOrder.setText(order.getPhone());
        tvDateOrder.setText(order.getDtOrder());
        tvDateReceived.setText(order.getDtReceived());
        tvTotalPrice.setText(String.format("%.0f",order.getTotalPrice()));


        return listItemView;
    }


//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        OrderModel order = orderList.get(position);
//        holder.bind(order);
//    }
//
//    @Override
//    public int getItemCount() {
//        return orderList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView tvOrderId, tvFullname, tvPhoneOrder, tvAddressOrder, tvDateOrder, tvDateReceived, tvTotalPrice;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tvOrderId = itemView.findViewById(R.id.tvOrderId);
//            tvFullname = itemView.findViewById(R.id.tvFullName);
//            tvAddressOrder = itemView.findViewById(R.id.tvAddressOrder);
//            tvPhoneOrder = itemView.findViewById(R.id.tvPhoneOrder);
//            tvDateOrder = itemView.findViewById(R.id.tvDateOrder);
//            tvDateReceived = itemView.findViewById(R.id.tvDateReceived);
//            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
//
//        }
//
//        @SuppressLint("SetTextI18n")
//        public void bind(OrderModel order) {
//
//        }
//    }

}
