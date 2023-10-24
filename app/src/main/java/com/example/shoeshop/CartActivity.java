package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoeshop.Adapter.CartAdapter;
import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Intent i;
    private EditText edt_search;
    private ImageView img_homeicon, img_seachicon, img_likeicon, img_profileicon, img_cart;
    private TextView tv_payment;
    private DatabaseReference dbreference;
    private CartAdapter adapter;
    private ArrayList<CartModel> list;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        anhxa();
;
        img_homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void anhxa(){
        //edt_search=findViewById(R.id.edt_searchC);
        //img_cart= findViewById(R.id.iv_cart);
        gridView = findViewById(R.id.gvListCart);
        //tv_allproduct= findViewById(R.id.tv_allproduct);
        img_homeicon= findViewById(R.id.img_home);
//        img_seachicon = findViewById(R.id.img_search);
//        img_likeicon= findViewById(R.id.img_like);
//        img_profileicon= findViewById(R.id.img_profile);
        dbreference= FirebaseDatabase.getInstance().getReference("Carts");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            list = new ArrayList<>();
            adapter = new CartAdapter(this, list);
            gridView.setAdapter(adapter);
            dbreference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        CartModel cart= dataSnapshot.getValue(CartModel.class);
                        list.add(cart);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
    private  void ShowCart(){
        dbreference= FirebaseDatabase.getInstance().getReference("Carts");
        list = new ArrayList<>();
        adapter=  new CartAdapter(this, list);
        gridView.setAdapter(adapter);
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    CartModel cart= dataSnapshot.getValue(CartModel.class);
                    list.add(cart);

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}