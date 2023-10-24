package com.example.shoeshop.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.R;
import com.example.shoeshop.ProductDetailActivity;

import java.util.ArrayList;

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.MyViewHolder> {

    Activity context;
    ArrayList<ProductModel>  list;

    public ProductItemAdapter(ArrayList<ProductModel> list, Activity context) {

        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.shoe_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel productModel= list.get(position);
        holder.title.setText(productModel.getProductName());
        holder.price.setText( productModel.getProductPrice().toString());
        Glide.with(context).load(productModel.getProductImage())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ProductDetailActivity.class);
                intent.putExtra("imgProduct", productModel.getProductImage());
                intent.putExtra("ProductName",productModel.getProductName() );
                intent.putExtra("ProductPrice", productModel.getProductPrice());
                intent.putExtra("ProductQuantity",productModel.getProductQuantity());
                intent.putExtra("ProductDescrip", productModel.getProductDescription());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView title, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView) itemView.findViewById(R.id.imgProduct);
            title=(TextView) itemView.findViewById(R.id.txtTitle);
            price=(TextView) itemView.findViewById(R.id.txtPrice);

        }

    }

}
