package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.Adapter.CartAdapter;
import com.example.shoeshop.Adapter.ProductAdapter;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.demo.SignInWithEmailActivity;
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
    private TextView tv_payment, tvTotal;
    private DatabaseReference dbreference;
    private CartAdapter adapter;
    private ArrayList<CartModel> list;
    private GridView gridView;
    CheckBox cbProduct;
    Double totalPrice = 0.0;
    CheckBox cbSelectAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        anhxa();
        ShowCart();
        //SelectAll();
        img_homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
//        rbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//            }
//        });
    }



    private void anhxa() {
        //edt_search=findViewById(R.id.edt_searchC);
        //img_cart= findViewById(R.id.iv_cart);
        gridView = findViewById(R.id.gvListCart);
        //tv_allproduct= findViewById(R.id.tv_allproduct);
        img_homeicon = findViewById(R.id.img_home);
        //cbProduct = findViewById(R.id.cbProductCart);
        tvTotal = findViewById(R.id.tvSumCartPrice);
        cbSelectAll = findViewById(R.id.cbSelectAll);
//        img_seachicon = findViewById(R.id.img_search);
//        img_likeicon= findViewById(R.id.img_like);
//        img_profileicon= findViewById(R.id.img_profile);

    }

    private void ShowCart() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Toast.makeText(CartActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
        if (user.getUid().isEmpty()) {

            Intent i = new Intent(CartActivity.this, SignInWithEmailActivity.class);
            startActivity(i);
            finish();
        } else {
            String userId = user.getUid();
            dbreference = FirebaseDatabase.getInstance().getReference("Carts");
            list = new ArrayList<>();
            adapter = new CartAdapter(this, list);
            gridView.setAdapter(adapter);

            dbreference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CartModel cart = dataSnapshot.getValue(CartModel.class);
                        if (cart != null) {
                            if (cart.getUserId().equals(userId)) {
                                list.add(cart);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("FAILED", error.getMessage());
                }
            });

            //select all
            cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        for(int i =0; i <list.size(); i++){
                            totalPrice += list.get(i).getPrice()*list.get(i).getQuantity();
                        }
                    }
                    else {
                        totalPrice = 0.0;
                    }
                    tvTotal.setText(String.format("%.0f",totalPrice));
                }
            });
        }
    }

}