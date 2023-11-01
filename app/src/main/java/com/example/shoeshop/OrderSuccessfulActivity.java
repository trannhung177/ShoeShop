package com.example.shoeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderSuccessfulActivity extends AppCompatActivity {
    TextView tv_backHome,tv_YourOrder;
    private Intent i;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);
        tv_YourOrder= findViewById(R.id.tv_YourOrder);
        tv_backHome= findViewById(R.id.tv_backHome);

        tv_YourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(OrderSuccessfulActivity.this, OrderFollow.class);
                startActivity(i);
            }
        });
        tv_backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(OrderSuccessfulActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}