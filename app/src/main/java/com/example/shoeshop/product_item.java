package com.example.shoeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class product_item extends AppCompatActivity {
    private TextView iconBack, btAddtocart, btBuynow;
    private EditText edtSearch;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);

        anhxa();
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i= new Intent(product_item.this, listProduct.class);
                startActivity(i);
            }
        });
        btAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(product_item.this, cart.class);
                startActivity(i);
            }
        });
    }

    private void anhxa(){
        iconBack.findViewById(R.id.txtBack);
        btAddtocart.findViewById(R.id.txtAddtoCart);
        btBuynow.findViewById(R.id.txtBuyNow);
        edtSearch.findViewById(R.id.edtSearch);
    }
}