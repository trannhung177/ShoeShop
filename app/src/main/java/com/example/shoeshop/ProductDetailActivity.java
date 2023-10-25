package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shoeshop.Admin.AddProduct;
import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.demo.SignInWithEmailActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    Button AddToCart;
    private EditText edtSearch;
    private ImageView imgProduct, ivCart;
    private TextView ProductName,ProductPrice,ProductQuantity, ProductDescrip;
    private DatabaseReference dbreference, CartdbReference;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);




       anhxa();
       ProductDetail();
       AddToCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //AddToCart();
               // Kiểm tra xem người dùng đã đăng nhập hay chưa
               FirebaseAuth mAuth = FirebaseAuth.getInstance();
               FirebaseUser user = mAuth.getCurrentUser();
               Intent intent = getIntent();
               if (intent != null) {
                   String productId = intent.getStringExtra("id");
                   Float productPrice = Float.parseFloat(ProductPrice.getText().toString());
                   Integer productQuantity =Integer.parseInt( ProductQuantity.getText().toString());

                   String productImg = img;
                   String productName = ProductName.getText().toString();
                   if(productId != null && user != null){
                       CartdbReference = FirebaseDatabase.getInstance().getReference("Carts");
                       String cartId = CartdbReference.push().getKey();
                       CartModel cartItem = new CartModel(cartId, user.getUid(), productId,productName,productImg,  productPrice, productQuantity);
                       if ( cartId != null){
                           CartdbReference.child(cartId).setValue(cartItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   Toast.makeText(ProductDetailActivity.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                               }
                           });
                       }
                   }
                   else {
                       Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
                       startActivity(i);
                   }
               }
           }
       });

       ivCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
               startActivity(intent);
           }
       });


    }

String img = "";
    private void ProductDetail(){
        dbreference= FirebaseDatabase.getInstance().getReference("Products");
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
                            img = imageUrl;
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
        ProductQuantity= findViewById(R.id.tvProductDetailQuantity);
        ProductDescrip=findViewById(R.id.ProductDetailDescrip);
        AddToCart = findViewById(R.id.tvAddtoCart);
        ivCart = findViewById(R.id.ivCart);

;
    }
}