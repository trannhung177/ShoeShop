package com.example.shoeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn, btnLinkToSignUp;
    TextView tvLinkToSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Mapping();

        // Chuyển qua giao diện Đăng ký
        tvLinkToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();

            }
        });

        //Đăng nhập
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtPhone.getText().toString();
                String password = edtPassword.getText().toString();
                if (phone.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignInActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    // Ánh xạ thuộc tính
    public void Mapping(){
        edtPhone = findViewById(R.id.edtSDTLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnSignIn = findViewById(R.id.btnSignin);
        tvLinkToSignUp = findViewById(R.id.tvLinkToSignUp);
    }
}


