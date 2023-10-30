package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_nameProfile,tv_emailProfile, tvExit, tvEditProfile;
    DatabaseReference dbProfile;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        anhxa();
        UserDetail();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Intent intent = getIntent();
        if(intent != null){
            String userID = intent.getStringExtra("id");
            String userName = tv_nameProfile.getText().toString();
        }
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
        tv_nameProfile= findViewById(R.id.tv_nameProfile);
        tv_emailProfile= findViewById(R.id.tv_emailProfile);

        tvEditProfile = findViewById(R.id.tv_infoUser);
        tvExit = findViewById(R.id.tvExit);
    }
}