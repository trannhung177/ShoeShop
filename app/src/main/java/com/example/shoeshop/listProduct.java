package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;

public class listProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference dbreference;
    itemAdapter adepter;
    ArrayList<ProductModel> list;
    private EditText edt_search;
    private ImageView img_cart;
    private TextView tv_allproduct;
    private ImageView img_homeicon, img_seachicon, img_likeicon, img_profileicon;
    Intent i;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        anhxa();
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(listProduct.this, cart.class);
                startActivity(i);
            }
        });
        img_homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(listProduct.this, MainActivity.class);
                startActivity(i);
            }
        });
        img_likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(listProduct.this, cart.class);
                startActivity(i);
            }
        });
        img_profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(listProduct.this, cart.class);
                startActivity(i);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        dbreference= FirebaseDatabase.getInstance().getReference("Products");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        adepter=  new itemAdapter(list, this);
        recyclerView.setAdapter(adepter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductModel productModel= dataSnapshot.getValue(ProductModel.class);
                    list.add(productModel);
                }
                adepter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

    private void anhxa(){
        edt_search=findViewById(R.id.edSearchP);
        img_cart= findViewById(R.id.imgCartP);
        img_homeicon= findViewById(R.id.imgHomeP);
        img_seachicon = findViewById(R.id.imgSeachP);
        img_likeicon= findViewById(R.id.imgPreferP);
        img_profileicon= findViewById(R.id.imgProfP);
        recyclerView= findViewById(R.id.recycleviewProduct);
    }
}