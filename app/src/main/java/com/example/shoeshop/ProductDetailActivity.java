package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.Models.CartModel;
import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.demo.SignInWithEmailActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvBackToHome, tvAddToCart, tvBuyNow;
    TextView AddToCart;
    private EditText edtSearch;
    private ImageView imgProduct, ivSearch;
    private TextView ProductName,ProductPrice,ProductQuantity, ProductDescrip;
    private DatabaseReference dbreference, CartdbReference;
    Intent i;
    Spinner spinSize;
    List<String> spinnerList;
    String iSelected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);
       anhxa();
       ProductDetail();


       // đổ dữ liệu lên dropdown và
        spinnerList = new ArrayList<>();
        spinnerList.add("39");
        spinnerList.add("40");
        spinnerList.add("41");
        spinnerList.add("42");
        spinnerList.add("43");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinSize.setAdapter(spinnerAdapter);
        spinSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                iSelected = spinnerList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                iSelected = "39";
            }
        });
       AddToCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AddToCart();

           }
       });
       tvBackToHome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               i = new Intent(ProductDetailActivity.this, MainActivity.class);
               startActivity(i);
               finish();
           }
       });

       ivSearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ProductDetailActivity.this, listProduct.class);
               startActivity(intent);
           }
       });

       tvBuyNow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               i = getIntent();
               if( i != null){
                   FirebaseAuth mAuth = FirebaseAuth.getInstance();
                   FirebaseUser user = mAuth.getCurrentUser();
                   String productId = i.getStringExtra("id");
                   String productName = ProductName.getText().toString();
                   Double productPrice = Double.parseDouble(ProductPrice.getText().toString());
                   Integer productQuantity = 1;
                   Boolean isCheckedCart = false;
                   String productImg = img;
                   Integer size = Integer.parseInt(iSelected);
                   CartModel cartModel = new CartModel("",user.getUid(),productId, productName, productImg,
                                        productPrice, productQuantity,isCheckedCart, size);
                   Intent intent = new Intent(ProductDetailActivity.this, PaymentActivity.class);
                   intent.putExtra("cartModel", cartModel);
                   intent.putExtra("isFromProductDetail", true);
                   startActivity(intent);
               }

           }
       });
    }

    // Thêm vào giỏ hàng
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
        Integer size = Integer.parseInt(iSelected);


        CartdbReference = FirebaseDatabase.getInstance().getReference("Carts");

        // Kiểm tra xem sản phẩm có sẵn trong giỏ hàng của người dùng hiện tại hay không
        Query query = CartdbReference.orderByChild("userId").equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean productFound = false; // Biến để kiểm tra xem sản phẩm đã tồn tại trong giỏ hay chưa
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        CartModel cartItem = snapshot.getValue(CartModel.class);
                        if (cartItem != null && cartItem.getProductId().equals(productId) && cartItem.getSize() == size) {
                            // Sản phẩm đã tồn tại trong giỏ hàng của người dùng hiện tại, cập nhật số lượng
                            int updatedQuantity = cartItem.getQuantity() + productQuantity;
                            snapshot.getRef().child("quantity").setValue(updatedQuantity)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProductDetailActivity.this, "Cập nhật giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            productFound = true;
                            break; // Dừng vòng lặp khi tìm thấy sản phẩm
                        }

                    }
                }
                if(!productFound) {
                    // Sản phẩm chưa tồn tại  trong giỏ hàng của người dùng hiện tại, tạo mục mới
                    String cartId = CartdbReference.push().getKey();
                    CartModel cartItem1 = new CartModel(cartId, user.getUid(),
                            productId, productName, productImg, productPrice, productQuantity, true, size);
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


// Chi tiết sản phẩm
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
        ivSearch = findViewById(R.id.ivSearchInDetail);
        spinSize = findViewById(R.id.spinSize);
        tvBackToHome = findViewById(R.id.tvBack);
        tvBuyNow = findViewById(R.id.tvBuyNow);
    }
}