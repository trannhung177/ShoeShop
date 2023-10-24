package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edt_search;
    private ImageView img_cart;
    private TextView tv_allproduct;
    private ImageView img_homeicon, img_seachicon, img_likeicon, img_profileicon;
    private GridView gridView;
    private DatabaseReference dbreference;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> list;
    Intent i;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa view
        anhxa();
        //tim kiem
//        edt_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                i=new Intent(MainActivity.this,search_product.class);
//                startActivity(i);
//            }
//        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itcart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(itcart);
            }
        });
        img_profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        tv_allproduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intallprd =new Intent(MainActivity.this, listProduct.class);
//                startActivity(intallprd);
//            }
//        });

    }

    private void anhxa(){
        edt_search=findViewById(R.id.edt_search);
        img_cart= findViewById(R.id.iv_cart);
        img_homeicon= findViewById(R.id.iv_homeicon);
        img_seachicon = findViewById(R.id.iv_searchicon);
        img_likeicon= findViewById(R.id.iv_likeicon);
        img_profileicon= findViewById(R.id.iv_profileicon);
        gridView=findViewById(R.id.ProductSuggest);
        dbreference= FirebaseDatabase.getInstance().getReference("Products");




        list = new ArrayList<>();
        adapter=  new ProductAdapter(this, list);
        gridView.setAdapter(adapter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductModel productModel= dataSnapshot.getValue(ProductModel.class);
                    list.add(productModel);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


                //click item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = list.get(i).getId();
                Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }


}