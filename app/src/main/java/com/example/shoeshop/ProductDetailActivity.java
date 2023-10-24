package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.demo.SignInWithEmailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView iconBack, tvAddToCart, tvBuyNow;
    private EditText edtSearch;
    private ImageView imgProduct;
    private TextView ProductName,ProductPrice,ProductQuantity, ProductDescrip;
    private DatabaseReference dbreference;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);
        dbreference= FirebaseDatabase.getInstance().getReference("Products");

       anhxa();
       ProductDetail();
       tvAddToCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AddToCart();
           }
       });

    }

    private void AddToCart() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!= null){

        }
        else {
            Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
            startActivity(i);
        }

    }

    private void ProductDetail(){
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
                String id = intent.getStringExtra("id");
                if(id != null){
                    dbreference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ProductModel product = snapshot.getValue(ProductModel.class);
                            assert product != null;
                            String imageUrl = product.getProductImage(); // Đây là URL hình ảnh
                            Picasso.get().load(imageUrl).into(imgProduct);
                            ProductName.setText(product.getProductName());
                            ProductPrice.setText(product.getProductPrice().toString());
                            ProductDescrip.setText(product.getProductDescription());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
        }
        else {
            Log.d("log", "ID");
        }
    }
    private void anhxa(){

        imgProduct= findViewById(R.id.imgProductDetail);// nghi nhất cái này
        ProductName=findViewById(R.id.ProductDetailName);
        ProductPrice= findViewById(R.id.ProductDetailPrice);
        ProductQuantity= findViewById(R.id.ProductDetailQuantity);
        ProductDescrip=findViewById(R.id.ProductDetailDescrip);
        tvAddToCart = findViewById(R.id.tvAddtoCart);

;
    }
}