package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Adapter.ProductItemAdapter;
import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listProduct extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference dbreference;
    //ProductItemAdapter adepter;
    ProductAdapter adapter, adtNike, adtBalen;
    ArrayList<ProductModel> list;
    ArrayList<ProductModel> lstmd, lstNike, lstBalenciaga;
    ProductAdapter adtAdidas;
    GridView gridView;
    TextView tv_brandAdidas, tv_brandNike, tv_brandBalen;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        anhxa();

        gridView= findViewById(R.id.recycleviewProduct);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        dbreference= FirebaseDatabase.getInstance().getReference("Products");

        //recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter=  new ProductAdapter(this, list);
        gridView.setAdapter(adapter);
        //recyclerView.setAdapter(adepter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
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
                Intent intent = new Intent(listProduct.this, ProductDetailActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        tv_brandAdidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data();
                Query query=dbreference.orderByChild("productBrand").equalTo("Adidas");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        ProductModel productModel= snapshot.getValue(ProductModel.class);
                        lstmd.add(productModel);
                        adtAdidas.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        tv_brandNike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data1();
                Query query=dbreference.orderByChild("productBrand").equalTo("Nike");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        ProductModel productModel= snapshot.getValue(ProductModel.class);
                        lstNike.add(productModel);
                        adtNike.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        tv_brandBalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data2();
                Query query=dbreference.orderByChild("productBrand").equalTo("Balenciaga");
                query.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        ProductModel productModel= snapshot.getValue(ProductModel.class);
                        lstBalenciaga.add(productModel);
                        adtBalen.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
    protected void anhxa(){
        tv_brandAdidas=findViewById(R.id.tv_brandAdidas);
        tv_brandNike=findViewById(R.id.tv_brandNike);
        tv_brandBalen= findViewById(R.id.tv_brandBalen);
    }
    public void data(){
        lstmd= new ArrayList<>();
        adtAdidas= new ProductAdapter(this,lstmd);
        gridView.setAdapter(adtAdidas);
    }
    public void data1(){
        lstNike= new ArrayList<>();
        adtNike= new ProductAdapter(this,lstNike);
        gridView.setAdapter(adtNike);
    }
    public void data2(){
        lstBalenciaga= new ArrayList<>();
        adtBalen= new ProductAdapter(this,lstBalenciaga);
        gridView.setAdapter(adtBalen);
    }

}