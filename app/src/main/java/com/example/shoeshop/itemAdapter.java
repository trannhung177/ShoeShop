package com.example.shoeshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.MyViewHolder> {

    Context context;
    ArrayList<productModel>  list;

    public itemAdapter(ArrayList<productModel> list,Context context) {

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
        productModel productModel= list.get(position);
        holder.title.setText(productModel.getTitle());
        holder.price.setText((int) productModel.cost);
        Glide.with(holder.img.getContext()).load(productModel.getImg())
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2)
                .into(holder.img);

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

            //String prc=price.getText().toString();
            //double pric=Double.parseDouble(prc);
        }

    }

}
