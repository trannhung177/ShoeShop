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
import android.widget.Spinner;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView iconBack, tvAddToCart, tvBuyNow;
    TextView AddToCart;
    private EditText edtSearch;
    private ImageView imgProduct, ivCart;
    private TextView ProductName,ProductPrice,ProductQuantity, ProductDescrip;
    private DatabaseReference dbreference, CartdbReference;
    Intent i;
    Spinner spinSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);
       anhxa();
       ProductDetail();
       AddToCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AddToCart();

           }
       });

       ivCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Kiểm tra xem người dùng đã đăng nhập hay chưa
               FirebaseAuth mAuth = FirebaseAuth.getInstance();
               FirebaseUser user = mAuth.getCurrentUser();
               Toast.makeText(ProductDetailActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
               if (user.getUid().isEmpty()){

                   Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
                   startActivity(i);
                   finish();
               }
               Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
               startActivity(intent);
           }
       });



    }

//    private void AddToCart() {
//        // Kiểm tra xem người dùng đã đăng nhập hay chưa
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        Intent intent = getIntent();
//        if (intent != null) {
//            String productId = intent.getStringExtra("id");
//            Double productPrice = Double.parseDouble(ProductPrice.getText().toString());
//            Integer productQuantity =1 ;
//            Boolean isCheckedCart = false;
//            String productImg = img;
//            String productName = ProductName.getText().toString();
//            if(productId != null && user != null){
//                CartdbReference = FirebaseDatabase.getInstance().getReference("Carts");
//
//
//                // Kiểm tra xem sản phẩm có sẵn trong giỏ hàng của người dùng hiện tại hay không
//                Query query = CartdbReference.orderByChild("productId").equalTo(productId);
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                String cartId = CartdbReference.push().getKey();
//                CartModel cartItem = new CartModel(cartId, user.getUid(), productId,productName,productImg,  productPrice, productQuantity, isCheckedCart);
//                if ( cartId != null){
//                    CartdbReference.child(cartId).setValue(cartItem).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            Toast.makeText(ProductDetailActivity.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            } else if (user == null) {
//                Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
//                startActivity(i);
//
//            } else {
//                Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
//                startActivity(i);
//            }
//        }
//    }
private void AddToCart() {
    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    Intent intent = getIntent();

    if (intent != null && user != null) {
        String productId = intent.getStringExtra("id");
        Double productPrice = Double.parseDouble(ProductPrice.getText().toString());
        Integer productQuantity = 1;
        Boolean isCheckedCart = false;
        String productImg = img;
        String productName = ProductName.getText().toString();

        CartdbReference = FirebaseDatabase.getInstance().getReference("Carts");

        // Kiểm tra xem sản phẩm có sẵn trong giỏ hàng của người dùng hiện tại hay không
        Query query = CartdbReference.orderByChild("userId").equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CartModel cartItem = snapshot.getValue(CartModel.class);
                        if (cartItem != null && cartItem.getProductId().equals(productId)) {
                            // Sản phẩm đã tồn tại trong giỏ hàng của người dùng hiện tại, cập nhật số lượng
                            int updatedQuantity = cartItem.getQuantity() + productQuantity;
                            snapshot.getRef().child("quantity").setValue(updatedQuantity)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProductDetailActivity.this, "Cập nhật giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            // Sản phẩm chưa tồn tại  trong giỏ hàng của người dùng hiện tại, tạo mục mới
                            String cartId = CartdbReference.push().getKey();
                            CartModel cartItem1 = new CartModel(cartId, user.getUid(), productId, productName, productImg, productPrice, productQuantity, false);
                            if (cartId != null) {
                                CartdbReference.child(cartId).setValue(cartItem1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ProductDetailActivity.this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    } else if (user == null) {
        Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
        startActivity(i);
    } else {
        Intent i = new Intent(ProductDetailActivity.this, SignInWithEmailActivity.class);
        startActivity(i);
    }
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
        ProductDescrip=findViewById(R.id.ProductDetailDescrip);
        AddToCart = findViewById(R.id.tvAddtoCart);
        ivCart = findViewById(R.id.ivCart);
        spinSize = findViewById(R.id.spinSize);
    }
}