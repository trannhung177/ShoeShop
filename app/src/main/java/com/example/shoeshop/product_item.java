package com.example.shoeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class product_item extends AppCompatActivity {
    private TextView iconBack, btAddtocart, btBuynow;
    private EditText edtSearch;
    private ImageView imgProduct;
    private TextView ProductName,ProductPrice,ProductQuantity, ProductDescrip;
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
        imgProduct= findViewById(R.id.imgProduct);
        ProductName=findViewById(R.id.ProductName);
        ProductPrice= findViewById(R.id.ProductPrice);
        ProductQuantity= findViewById(R.id.ProductQuantity);
        ProductDescrip=findViewById(R.id.ProductDescrip);

        Glide.with(this).load(getIntent().getStringExtra("imgProduct"))
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_image_24_2)
                .into(imgProduct);

        ProductName.setText(getIntent().getStringExtra("ProductName"));
        ProductPrice.setText(getIntent().getStringExtra("ProductPrice"));
        ProductQuantity.setText(getIntent().getStringExtra("ProductQuantity"));
        ProductDescrip.setText(getIntent().getStringExtra("ProductDescrip"));
    }
}