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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView iconBack, btAddtocart, btBuynow;
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
//        // Nhận dữ liệu từ Intent
//        Intent intent = getIntent();
//        if (intent != null) {
//            String id = intent.getStringExtra("id");
//            if(id != null){
//                dbreference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        ProductModel product = snapshot.getValue(ProductModel.class);
//
//                        //imgProduct.setImageResource(Integer.parseInt(product.getProductImage())); // Thay vì sử dụng android:src
//                        String imageUrl = product.getProductImage(); // Đây là URL hình ảnh
//                        Picasso.get().load(imageUrl).into(imgProduct);
//                        ProductName.setText(product.getProductName());
//                        ProductPrice.setText(product.getProductPrice().toString());
//                        ProductDescrip.setText(product.getProductDescription());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        }
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
//                ProductModel itemData = (ProductModel) intent.getSerializableExtra("itemData", ProductModel.class);
//                if( itemData != null){
//                    // Hiển thị dữ liệu từ itemData trong các phần tử giao diện người dùng
//                    Log.d(itemData.getId(), "ID");
//                    imgProduct.setImageResource(Integer.parseInt(itemData.getProductImage())); // Thay vì sử dụng android:src
//                    ProductName.setText(itemData.getProductName());
//                    ProductPrice.setText(itemData.getProductPrice().toString());
//                    ProductDescrip.setText(itemData.getProductDescription());
//                }

        }
        else {
            Log.d("log", "ID");
        }
    }
    private void anhxa(){
//
//        iconBack.findViewById(R.id.txtBack);
//        btAddtocart.findViewById(R.id.txtAddtoCart);
//        btBuynow.findViewById(R.id.txtBuyNow);
//        edtSearch.findViewById(R.id.edtSearch);
        imgProduct= findViewById(R.id.imgProductDetail);// nghi nhất cái này
        ProductName=findViewById(R.id.ProductDetailName);
        ProductPrice= findViewById(R.id.ProductDetailPrice);
        ProductQuantity= findViewById(R.id.ProductDetailQuantity);
        ProductDescrip=findViewById(R.id.ProductDetailDescrip);

//        Glide.with(this).load(getIntent().getStringExtra("imgProduct"))
//                .placeholder(R.drawable.baseline_image_24)
//                .error(R.drawable.baseline_image_24_2)
//                .into(imgProduct);

//        ProductName.setText(getIntent().getStringExtra("ProductName"));
//        ProductPrice.setText(getIntent().getStringExtra("ProductPrice"));
//        ProductQuantity.setText(getIntent().getStringExtra("ProductQuantity"));
//        ProductDescrip.setText(getIntent().getStringExtra("ProductDescrip"));
    }
}