package com.example.shoeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText edtFullname, edtPhone, edtPassword, edtRePassword;
    Button btnSignUp;
    TextView tvLinkToSignIn;

    // create obj access to Firebase Realtime Database
    DatabaseReference db =
            FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoeshop1407-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Mapping();

        // Đăng ký tài khoản
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = edtFullname.getText().toString();
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                String rePassword = edtRePassword.getText().toString();

                if (phone.isEmpty() || password.isEmpty() || rePassword.isEmpty() || fullname.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(rePassword)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // kiểm tra số điện thoại có trùng không
                            if(snapshot.hasChild(phone)){
                                Toast.makeText(SignUpActivity.this, "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // Gửi dữ liệu cho Firebase Realtime Database
                                // Sử dụng số điện thoại làm khóa cho mỗi người dùng
                                db.child("user").child(phone).child("fullname").setValue(fullname);
                                db.child("user").child(phone).child("password").setValue(password);
                                db.child("user").child(phone).child("repassword").setValue(rePassword);

                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        tvLinkToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }

    //Ánh xạ thuộc tính
    public  void Mapping(){
        edtFullname = findViewById(R.id.edtFullname);
        edtPhone = findViewById(R.id.edtSDTSignUp);
        edtPassword = findViewById(R.id.edtPasswordSignUp);
        edtRePassword = findViewById(R.id.edtRePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLinkToSignIn = findViewById(R.id.tvLinkToSignIn);
    }
}