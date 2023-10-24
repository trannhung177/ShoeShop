package com.example.shoeshop.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Activity mContext;
    List<CartModel> cartList;
    @Override
    public int getCount() {
        return cartList.size();
    }
    public CartAdapter(Activity mContext, List<CartModel> cartList){
        super();
        this.mContext = mContext;
        this.cartList = cartList;
    }
    @Override
    public Object getItem(int i) {
        return cartList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("CheckResult")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.item_product,null,true);

        TextView tvName = listItemView.findViewById(R.id.tvProductName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvPrice = listItemView.findViewById(R.id.tvProductPrice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivImg =  listItemView.findViewById(R.id.ivProductImg_MP);


        CartModel cartModel = cartList.get(position);

        tvName.setText(cartModel.getProductName());
        tvPrice.setText(String.format( cartModel.getPrice().toString()));
        Glide.with(mContext).load(cartModel.getProductImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2).into(ivImg);
        ;


        return listItemView;
    }
}
