package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.Models.UserModel;
import com.example.shoeshop.demo.SignInWithEmailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_nameProfile,tv_emailProfile, tvExit, tvEditProfile, tvCountProductCart;
    ImageView ivHome, ivCart, ivListProduct;
    DatabaseReference dbCart, dbProfile;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        anhxa();
        CountProductInCart();
        UserDetail();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Intent intent = getIntent();
        if(intent != null){
            String userID = intent.getStringExtra("id");
            String userName = tv_nameProfile.getText().toString();
        }
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        ivListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ProfileActivity.this, listProduct.class);
                startActivity(i);
            }
        });
        ivListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user != null){
                    mAuth.signOut();
                    i = new Intent(ProfileActivity.this, SignInWithEmailActivity.class);
                    startActivity(i);
                }

            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra xem người dùng đã đăng nhập hay chưa
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                dbProfile= FirebaseDatabase.getInstance().getReference("Users");
                String userId = user.getUid();

                if(userId != null){
                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                    intent.putExtra("userId",userId);
                    startActivity(intent);
                }
            }
        });
    }

    private void CountProductInCart() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dbCart = FirebaseDatabase.getInstance().getReference("Carts");
        String userId = user.getUid();
        // Tạo một truy vấn Firebase để lấy danh sách sản phẩm trong giỏ của người dùng hiện tại (userId)
        Query query = dbCart.orderByChild("userId").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int productCount = (int) dataSnapshot.getChildrenCount();
                // Hiển thị số lượng sản phẩm trong giỏ lên TextView
                tvCountProductCart.setText("Bạn có "+productCount+ " trong giỏ hàng");
                if( productCount == 0){
                    tvCountProductCart.setText("Bạn chưa có sản phẩm nào");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

    }

    private void UserDetail(){
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dbProfile= FirebaseDatabase.getInstance().getReference("Users");
        String userId = user.getUid();

            if(userId != null){
                dbProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel user = snapshot.getValue(UserModel.class);
                        assert user != null;
//                        String imageUrl = product.getProductImage(); // Đây là URL hình ảnh
//                        Picasso.get().load(imageUrl).into(imgProduct);
//                        img = imageUrl;
                        tv_nameProfile.setText(user.getFullname());
                        tv_emailProfile.setText(user.getEmail());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        else {
                Intent i = new Intent(ProfileActivity.this, SignInWithEmailActivity.class);
                startActivity(i);
        }
    }
    private void anhxa(){
        ivHome = findViewById(R.id.iv_homeiconPr);
        ivCart = findViewById(R.id.ivCartInProfile);
        ivListProduct = findViewById(R.id.ivListProductProfile);

        tv_nameProfile= findViewById(R.id.tv_nameProfile);
        tv_emailProfile= findViewById(R.id.tv_emailProfile);
        tvCountProductCart = findViewById(R.id.tvCountProductOfUser);
        tvEditProfile = findViewById(R.id.tv_infoUser);
        tvExit = findViewById(R.id.tvExit);
    }
}