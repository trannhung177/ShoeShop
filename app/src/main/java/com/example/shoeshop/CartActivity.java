package com.example.shoeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {
    Intent i;
    private EditText edt_search;
    private ImageView img_homeicon, img_seachicon, img_likeicon, img_profileicon, img_cart;
    private TextView tv_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        anhxa();
        img_homeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(CartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        img_seachicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        img_likeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent();
                startActivity(i);
            }
        });
    }
    private void anhxa(){
        edt_search=findViewById(R.id.edt_searchC);
        img_cart= findViewById(R.id.iv_cart);
        //tv_allproduct= findViewById(R.id.tv_allproduct);
        img_homeicon= findViewById(R.id.img_home);
        img_seachicon = findViewById(R.id.img_search);
        img_likeicon= findViewById(R.id.img_like);
        img_profileicon= findViewById(R.id.img_profile);
    }
}