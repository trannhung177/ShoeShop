package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.shoeshop.Models.ProductModel;
import com.example.shoeshop.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_nameProfile,tv_emailProfile;
    DatabaseReference dbProfile;

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
    }
    private void UserDetail(){
        dbProfile= FirebaseDatabase.getInstance().getReference("Users");
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String id = intent.getStringExtra("id");
            if(id != null){
                dbProfile.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
        }
        else {
            Log.d("log", "ID");
        }
    }
    private void anhxa(){
        tv_nameProfile= findViewById(R.id.tv_nameProfile);
        tv_emailProfile= findViewById(R.id.tv_emailProfile);
    }
}