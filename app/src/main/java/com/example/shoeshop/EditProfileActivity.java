package com.example.shoeshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeshop.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class EditProfileActivity extends AppCompatActivity {
    EditText edtFullName, edtPhone, edtAddress, edtOldPassword, edtNewPassword;
    TextView btnSaveProfile, btnCancel;

    DatabaseReference dbProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Mapping();
        ShowUserProfile();
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUser();
            }
        });
    }

    private void UpdateUser() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dbProfile= FirebaseDatabase.getInstance().getReference("Users");
        if(user != null){
            //String userId = user.getUid();
            String fullname = edtFullName.getText().toString();
            String address = edtAddress.getText().toString();
            String phone = edtPhone.getText().toString();
            String password = edtNewPassword.getText().toString();
            String oldpassword = edtOldPassword.getText().toString();
            // Xác thực người dùng bằng mật khẩu hiện tại
            mAuth.signInWithEmailAndPassword(user.getEmail(), oldpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userId = user.getUid();
                                // Cập nhật mật khẩu nếu người dùng muốn thay đổi mật khẩu
                                if (!password.isEmpty()) {
                                    // Mật khẩu đúng, cho phép cập nhật thông tin người dùng
                                    UserModel userModel = new UserModel( fullname, user.getEmail(), password,"user", phone, address);
                                    dbProfile.child(userId).setValue(userModel);
                                }

                            } else {
                                Toast.makeText(EditProfileActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }

    private void ShowUserProfile() {
        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        dbProfile= FirebaseDatabase.getInstance().getReference("Users");
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String userId = intent.getStringExtra("userId");
            if(userId != null){
                dbProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        assert userModel != null;
                        edtFullName.setText(userModel.getFullname());
                        edtPhone.setText(userModel.getPhone());
                        edtAddress.setText(userModel.getAddress());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    private void Mapping(){
        edtFullName = findViewById(R.id.edtFullNameProfile);
        edtPhone = findViewById(R.id.edtPhoneProfile);
        edtAddress = findViewById(R.id.edtAdrressProfile);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);

        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnCancel = findViewById(R.id.btnCancel);
    }
}