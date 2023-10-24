package com.example.shoeshop.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    private Activity mContext;
    List<ProductModel> productsList;

    public ProductAdapter(Activity mContext, List<ProductModel> productsList){
        super(mContext, R.layout.item_product,productsList);
        this.mContext = mContext;
        this.productsList = productsList;
    }
    @SuppressLint("CheckResult")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.shoe_item,null,true);

        TextView tvName = listItemView.findViewById(R.id.txtTitle);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvPrice = listItemView.findViewById(R.id.txtPrice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivImg =  listItemView.findViewById(R.id.imgProduct);


        ProductModel product = productsList.get(position);

        tvName.setText(product.getProductName());
        tvPrice.setText(String.format( product.getProductPrice().toString()));
        Glide.with(mContext).load(product.getProductImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2).into(ivImg);
        ;


        return listItemView;
    }
}
