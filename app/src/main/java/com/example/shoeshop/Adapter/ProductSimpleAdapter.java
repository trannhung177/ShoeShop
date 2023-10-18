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

import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;

import java.util.List;

public class ProductSimpleAdapter extends ArrayAdapter {
    private Activity mContext;
    List<ProductModel> productsList;

    public ProductSimpleAdapter(Activity mContext, List<ProductModel> productsList){
        super(mContext, R.layout.item_product,productsList);
        this.mContext = mContext;
        this.productsList = productsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.item_product,null,true);

        TextView tvName = listItemView.findViewById(R.id.tvProductName);
        TextView tvBrand = listItemView.findViewById(R.id.tvProductBrand);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView ivImg =  listItemView.findViewById(R.id.ivProductImg_MP);


        ProductModel product = productsList.get(position);

        tvName.setText(product.getProductName());
        tvBrand.setText(product.getProductBrand());
        ivImg.setImageResource(Integer.parseInt(product.getProductImage().toString()));


        return listItemView;
    }
}
