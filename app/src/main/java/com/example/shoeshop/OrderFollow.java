package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.shoeshop.Adapter.OrderAdapter;
import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Models.OrderModel;
import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderFollow extends AppCompatActivity {
    DatabaseReference dbreference;
    ArrayList list;
    OrderAdapter adapter;
    ListView lvListOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_follow);
         lvListOrder = findViewById(R.id.lvListOrder);
        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderFollow.this, MainActivity.class);
                startActivity(i);
            }
        });
        SHowList();

    }

    private void SHowList() {
        dbreference= FirebaseDatabase.getInstance().getReference("Orders");
        list = new ArrayList<>();
        adapter=  new OrderAdapter(this, list);

        lvListOrder.setAdapter(adapter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    OrderModel orderModel= dataSnapshot.getValue(OrderModel.class);
                    list.add(orderModel);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}